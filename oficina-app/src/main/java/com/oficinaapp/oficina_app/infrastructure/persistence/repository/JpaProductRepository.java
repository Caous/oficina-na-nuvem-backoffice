package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    List<ProductEntity> findByWorkshopIdOrderByNameAsc(Long workshopId);

    /**
     * The marketplace query lives here instead of branching in Java.
     *
     * <p>{@code search} always arrives as a ready {@code like} pattern and
     * {@code ignoreCategory} carries the "no filter" case: a bare null
     * parameter has no type for Postgres to infer, and {@code lower(null)}
     * fails before the query even runs.
     */
    @Query("""
            select p from ProductEntity p
            where p.published = true
              and p.stockQuantity > 0
              and lower(p.name) like lower(:search)
              and (:ignoreCategory = true or p.category = :category)
            order by p.name asc
            """)
    List<ProductEntity> searchMarketplace(
            @Param("search") String search,
            @Param("ignoreCategory") boolean ignoreCategory,
            @Param("category") ProductCategory category
    );

    Optional<ProductEntity> findByIdAndPublishedTrueAndStockQuantityGreaterThan(Long id, int minimumStock);
}
