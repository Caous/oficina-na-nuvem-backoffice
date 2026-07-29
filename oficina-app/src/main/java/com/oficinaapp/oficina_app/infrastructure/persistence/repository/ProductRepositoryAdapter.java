package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import com.oficinaapp.oficina_app.domain.model.Product;
import com.oficinaapp.oficina_app.domain.repository.ProductRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaRepository;

    public ProductRepositoryAdapter(JpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        return ProductMapper.toDomain(jpaRepository.save(ProductMapper.toEntity(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Optional<Product> findByIdAndWorkshopId(Long id, Long workshopId) {
        return jpaRepository.findByIdAndWorkshopId(id, workshopId).map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findByWorkshopId(Long workshopId) {
        return jpaRepository.findByWorkshopIdOrderByNameAsc(workshopId).stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findVisibleOnMarketplace(String search, ProductCategory category) {
        String pattern = "%" + (search == null ? "" : search) + "%";

        return jpaRepository.searchMarketplace(pattern, category == null, category).stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findVisibleOnMarketplaceById(Long id) {
        return jpaRepository.findByIdAndPublishedTrueAndStockQuantityGreaterThan(id, 0)
                .map(ProductMapper::toDomain);
    }

    @Override
    public void delete(Product product) {
        jpaRepository.deleteById(product.getId());
    }
}
