package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.catalog.ServiceCategoryRequest;
import com.oficinaapp.oficina_app.application.dto.catalog.ServiceCategoryResponse;
import com.oficinaapp.oficina_app.application.usecase.catalog.DeleteServiceCategoryUseCase;
import com.oficinaapp.oficina_app.application.usecase.catalog.ListServiceCategoriesUseCase;
import com.oficinaapp.oficina_app.application.usecase.catalog.SaveServiceCategoryUseCase;
import com.oficinaapp.oficina_app.infrastructure.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service-categories")
public class ServiceCategoryController {

    private final ListServiceCategoriesUseCase listCategories;
    private final SaveServiceCategoryUseCase saveCategory;
    private final DeleteServiceCategoryUseCase deleteCategory;

    public ServiceCategoryController(
            ListServiceCategoriesUseCase listCategories,
            SaveServiceCategoryUseCase saveCategory,
            DeleteServiceCategoryUseCase deleteCategory
    ) {
        this.listCategories = listCategories;
        this.saveCategory = saveCategory;
        this.deleteCategory = deleteCategory;
    }

    @GetMapping
    public List<ServiceCategoryResponse> list(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return listCategories.execute(caller.requireWorkshopId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceCategoryResponse create(
            @Valid @RequestBody ServiceCategoryRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveCategory.execute(null, request, caller.requireWorkshopId());
    }

    @PutMapping("/{id}")
    public ServiceCategoryResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ServiceCategoryRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveCategory.execute(id, request, caller.requireWorkshopId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        deleteCategory.execute(id, caller.requireWorkshopId());
    }
}
