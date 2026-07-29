package com.oficinaapp.oficina_app.infrastructure.integration.fipe;

import com.oficinaapp.oficina_app.application.dto.fipe.FipeBrandResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeModelResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeQuoteResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeYearResponse;
import com.oficinaapp.oficina_app.application.port.FipeCatalog;
import com.oficinaapp.oficina_app.domain.enums.VehicleType;
import com.oficinaapp.oficina_app.domain.exception.ExternalServiceException;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Reads the public FIPE table over HTTP.
 *
 * <p>Answers are cached in memory: the table changes once a month, and every
 * vehicle form would otherwise hit the same three endpoints. The cache has no
 * eviction on purpose — its size is bounded by the table itself, and a restart
 * refreshes it.
 */
@Component
public class FipeRestCatalog implements FipeCatalog {

    private static final String SERVICE_NAME = "The FIPE table";
    private static final String NOT_FOUND_MARKER = "\"error\"";

    private final RestClient restClient;
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    public FipeRestCatalog(FipeProperties properties) {
        this.restClient = RestClient.create(properties.baseUrl());
    }

    @Override
    public List<FipeBrandResponse> brandsOf(VehicleType type) {
        String segment = FipeVehicleSegment.pathOf(type);

        return cached("brands:" + segment, () -> get("/%s/marcas".formatted(segment)).stream()
                .map(item -> new FipeBrandResponse(item.codigo(), item.nome()))
                .toList());
    }

    @Override
    public List<FipeModelResponse> modelsOf(VehicleType type, String brandCode) {
        String segment = FipeVehicleSegment.pathOf(type);

        return cached("models:%s:%s".formatted(segment, brandCode), () -> {
            FipePayloads.ModelList payload = call(() -> restClient.get()
                    .uri("/%s/marcas/%s/modelos".formatted(segment, brandCode))
                    .retrieve()
                    .body(FipePayloads.ModelList.class));

            return payload.modelos().stream()
                    .map(item -> new FipeModelResponse(item.codigo(), brandCode, item.nome()))
                    .toList();
        });
    }

    @Override
    public List<FipeYearResponse> yearsOf(VehicleType type, String brandCode, String modelCode) {
        String segment = FipeVehicleSegment.pathOf(type);
        String path = "/%s/marcas/%s/modelos/%s/anos".formatted(segment, brandCode, modelCode);

        return cached("years:%s:%s:%s".formatted(segment, brandCode, modelCode), () -> get(path).stream()
                .map(item -> new FipeYearResponse(item.codigo(), modelCode, item.nome()))
                .toList());
    }

    @Override
    public FipeQuoteResponse quote(VehicleType type, String brandCode, String modelCode, String yearCode) {
        String segment = FipeVehicleSegment.pathOf(type);
        String path = "/%s/marcas/%s/modelos/%s/anos/%s".formatted(segment, brandCode, modelCode, yearCode);

        return cached("quote:" + path, () -> {
            FipePayloads.Quote payload = call(() -> restClient.get()
                    .uri(path)
                    .retrieve()
                    .body(FipePayloads.Quote.class));

            return new FipeQuoteResponse(
                    payload.marca(),
                    payload.modelo(),
                    "%d %s".formatted(payload.anoModelo(), payload.combustivel()),
                    payload.codigoFipe(),
                    BrazilianMoney.parse(payload.valor())
            );
        });
    }

    private List<FipePayloads.Item> get(String path) {
        return call(() -> restClient.get()
                .uri(path)
                .retrieve()
                .body(new ParameterizedTypeReference<List<FipePayloads.Item>>() {
                }));
    }

    private static <T> T call(Supplier<T> request) {
        try {
            T body = request.get();

            if (body == null) {
                throw new ExternalServiceException(SERVICE_NAME);
            }

            return body;
        } catch (RestClientResponseException exception) {
            // FIPE answers 500 with an "error" body when a combination does not
            // exist, so the body — not the status — says whether it is our fault.
            if (exception.getResponseBodyAsString().contains(NOT_FOUND_MARKER)) {
                throw new ResourceNotFoundException("FIPE entry");
            }

            throw new ExternalServiceException(SERVICE_NAME);
        } catch (RestClientException exception) {
            throw new ExternalServiceException(SERVICE_NAME);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T cached(String key, Supplier<T> loader) {
        return (T) cache.computeIfAbsent(key, ignored -> loader.get());
    }
}
