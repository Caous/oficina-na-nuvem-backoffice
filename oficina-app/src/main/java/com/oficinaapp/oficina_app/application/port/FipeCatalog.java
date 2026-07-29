package com.oficinaapp.oficina_app.application.port;

import com.oficinaapp.oficina_app.application.dto.fipe.FipeBrandResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeModelResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeQuoteResponse;
import com.oficinaapp.oficina_app.application.dto.fipe.FipeYearResponse;
import com.oficinaapp.oficina_app.domain.enums.VehicleType;

import java.util.List;

/**
 * The FIPE price table. A port because the table is someone else's service:
 * swapping the provider must not reach the controller.
 */
public interface FipeCatalog {

    List<FipeBrandResponse> brandsOf(VehicleType type);

    List<FipeModelResponse> modelsOf(VehicleType type, String brandCode);

    List<FipeYearResponse> yearsOf(VehicleType type, String brandCode, String modelCode);

    FipeQuoteResponse quote(VehicleType type, String brandCode, String modelCode, String yearCode);
}
