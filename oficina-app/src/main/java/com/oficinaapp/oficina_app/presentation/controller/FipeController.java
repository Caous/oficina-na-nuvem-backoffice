package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.fipe.FipeBrandResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeModelResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeQuoteResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeYearResponse;
import com.oficinaapp.oficina_app.application.port.FipeCatalog;
import com.oficinaapp.oficina_app.domain.enums.VehicleType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A thin pass-through to the FIPE table, so the app talks to one API only and
 * gains the cache for free.
 *
 * <p>{@code type} defaults to {@code CAR}. Years and quotes need the brand as
 * well as the model: that is how the FIPE table addresses a vehicle.
 */
@RestController
@RequestMapping("/api/fipe")
public class FipeController {

    private final FipeCatalog fipeCatalog;

    public FipeController(FipeCatalog fipeCatalog) {
        this.fipeCatalog = fipeCatalog;
    }

    @GetMapping("/brands")
    public List<FipeBrandResponse> brands(
            @RequestParam(defaultValue = "CAR") VehicleType type
    ) {
        return fipeCatalog.brandsOf(type);
    }

    @GetMapping("/models")
    public List<FipeModelResponse> models(
            @RequestParam String brandCode,
            @RequestParam(defaultValue = "CAR") VehicleType type
    ) {
        return fipeCatalog.modelsOf(type, brandCode);
    }

    @GetMapping("/years")
    public List<FipeYearResponse> years(
            @RequestParam String brandCode,
            @RequestParam String modelCode,
            @RequestParam(defaultValue = "CAR") VehicleType type
    ) {
        return fipeCatalog.yearsOf(type, brandCode, modelCode);
    }

    @GetMapping("/quote")
    public FipeQuoteResponse quote(
            @RequestParam String brandCode,
            @RequestParam String modelCode,
            @RequestParam String yearCode,
            @RequestParam(defaultValue = "CAR") VehicleType type
    ) {
        return fipeCatalog.quote(type, brandCode, modelCode, yearCode);
    }
}
