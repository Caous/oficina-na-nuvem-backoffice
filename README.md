# Oficina na Nuvem — Backoffice API

Spring Boot 4.1 / Java 21 API behind the Oficina na Nuvem mobile app. The code
follows Clean Architecture: `domain` knows nobody, `application` orchestrates
the rules, `infrastructure` and `presentation` plug into it from the outside.

## Running locally

Create your local credentials file (gitignored, shared by Docker and the API):

```bash
cp .env.example .env
```

Start the database:

```bash
docker compose up -d
```

Run the API (needs JDK 21 — set `JAVA_HOME` if the default `java` is older):

```bash
cd oficina-app && ./mvnw spring-boot:run
```

The API listens on `http://localhost:8080` and every path below is prefixed
with `/api`.

### Configuration

Database credentials live only in the `.env` file; everything else has a
development default.

| Variable | Default | Meaning |
| --- | --- | --- |
| `DB_HOST` / `DB_PORT` | `localhost` / `5432` | Postgres address |
| `DB_NAME` | `oficina_db` | Database name |
| `DB_USER` / `DB_PASSWORD` | — (from `.env`) | Credentials, never committed |
| `JWT_SECRET` | development secret | HMAC key, **must** be replaced outside local runs |
| `JWT_EXPIRATION_MINUTES` | `480` | Token lifetime |
| `FIPE_BASE_URL` | `https://parallelum.com.br/fipe/api/v1` | Public FIPE table |

## How the pieces relate

- One `user_account` table serves the three roles: `CUSTOMER`,
  `WORKSHOP_OWNER` and `WORKSHOP_EMPLOYEE`. A single login endpoint answers all
  of them, and the role in the response tells the app which area to open.
- **Customer and workshop are many-to-many.** One customer may be served by
  five workshops, and each workshop has many customers; `workshop_customer`
  holds that relationship. A workshop never lists the whole customer base — it
  looks a person up by document, and the lookup creates the link.
- **The marketplace crosses every workshop.** A customer browsing products sees
  what any workshop has published, each line carrying its `sellerName`. Stock
  and publication stay private to the workshop that owns the product.
- Everything a workshop touches is scoped by the `workshopId` inside the token,
  never by a value in the request body.

## Authentication

The token is a signed JWT carrying the account id, e-mail, role and workshop.
There is no session on the server and no refresh token: when the token expires,
the person signs in again.

```
Authorization: Bearer <accessToken>
```

| Method | Path | Who |
| --- | --- | --- |
| POST | `/auth/login` | anyone |
| POST | `/auth/register/customer` | anyone |
| POST | `/auth/register/workshop` | anyone |
| GET | `/auth/me` | any signed in user |

Registering a workshop creates the workshop and its owner account in the same
transaction. Employees are hired through `POST /employees`, not here.

## Customer area

| Method | Path | Notes |
| --- | --- | --- |
| GET / PUT | `/me/profile` | e-mail and document are not editable here |
| GET / POST | `/me/vehicles` | the garage |
| PUT / DELETE | `/me/vehicles/{id}` | |
| GET | `/marketplace/products` | `?search=&category=&sort=` |
| GET | `/marketplace/products/{id}` | |
| POST | `/marketplace/orders` | items, payment method, delivery address |
| GET | `/me/orders` | |

`sort` accepts `LOWEST_PRICE` (default), `HIGHEST_PRICE` and `NAME`.
`category` accepts `OILS`, `FILTERS`, `TIRES`, `PARTS`, `ACCESSORIES`, `SOUND`.

Placing an order reads the prices from the catalogue — never from the request —
copies name, price and seller into each line and takes the units out of stock in
the same transaction. An order that cannot be fulfilled is never stored.
Payment is recorded, not charged: there is no gateway yet, so every order starts
`PLACED` with payment `PENDING`.

## Workshop area

Owner and employees reach these; only the owner may hire, edit or dismiss.

| Method | Path | Notes |
| --- | --- | --- |
| GET / POST | `/employees` | POST is owner only |
| PUT / DELETE | `/employees/{id}` | owner only; DELETE disables, keeping history |
| GET | `/customers` | customers linked to this workshop |
| GET | `/customers/search?document=` | finds a customer and links them |
| GET | `/customers/{id}/vehicles` | linked customers only |
| GET / POST | `/service-categories` | |
| PUT / DELETE | `/service-categories/{id}` | a category still in use cannot be deleted |
| GET / POST | `/services` | carries `maxDiscountPercent` and the computed `minimumPrice` |
| PUT / DELETE | `/services/{id}` | |
| GET | `/service-orders?status=` | |
| POST | `/service-orders` | customer, vehicle, services and mechanic are all checked against the workshop |
| GET | `/service-orders/{id}` | |
| PATCH | `/service-orders/{id}/status` | a closed order never reopens |
| GET / POST | `/products` | the private stock |
| PUT / DELETE | `/products/{id}` | |
| PATCH | `/products/{id}/stock` | `{"delta": -3}` takes units out |
| PATCH | `/products/{id}/publish` | the switch into the marketplace |
| GET | `/dashboard/summary` | KPIs, weekly series and recent orders |

Service order statuses: `AWAITING_APPROVAL`, `APPROVED`, `IN_PROGRESS`,
`TESTING`, `COMPLETED`, `CANCELLED`. The last two close the order and feed the
dashboard.

The dashboard answers raw numbers — `openOrders`, `completedToday`,
`monthlyRevenue`, `averageTicket` — plus one entry per day of the last week
carrying a date, so the app formats currency and day labels in the reader's
language.

## FIPE

A cached pass-through to the public FIPE table, so the app talks to one API only.

| Method | Path |
| --- | --- |
| GET | `/fipe/brands?type=CAR` |
| GET | `/fipe/models?brandCode=21&type=CAR` |
| GET | `/fipe/years?brandCode=21&modelCode=4828&type=CAR` |
| GET | `/fipe/quote?brandCode=21&modelCode=4828&yearCode=2013-5&type=CAR` |

`type` defaults to `CAR` and maps onto the three FIPE segments: `CAR` and
`UTILITY` price as cars, `MOTORCYCLE` as motorcycles, `TRUCK` as trucks.
`JET_SKI` and `AIRCRAFT` are outside the table and answer `409` — those vehicles
are typed in by hand.

Years and quotes need the brand as well as the model: that is how FIPE addresses
a vehicle. Answers are cached in memory; a restart refreshes them.

## Errors

Every failure uses the same shape:

```jsonc
{
  "timestamp": "2026-07-28T22:13:21Z",
  "status": 409,
  "error": "Conflict",
  "message": "This e-mail is already registered.",
  "fields": { "email": "must be a well-formed email address" }
}
```

`fields` appears only on validation errors. Login answers `401` with the same
message for an unknown e-mail and for a wrong password, so nobody can use it to
discover which accounts exist. `403` means the role is wrong, `404` covers both
"does not exist" and "belongs to someone else", and `409` carries conflicts —
duplicated document, stock that ran out, an order already closed.

## Tests

```bash
cd oficina-app && ./mvnw test
```

## Known gaps

- The schema is created by Hibernate (`ddl-auto=update`). It never drops
  columns, so a renamed field leaves the old one behind — move to Flyway before
  the first deploy.
- CORS accepts every origin — fine for development, not for production.
- Marketplace orders record a payment method but charge nothing; there is no
  gateway and no order status transition endpoint yet.
- Listing endpoints return whole collections; add paging before the data grows.
