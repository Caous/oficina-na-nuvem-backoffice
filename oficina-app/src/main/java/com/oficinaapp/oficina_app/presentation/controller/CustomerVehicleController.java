package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleRequest;
import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleResponse;
import com.oficinaapp.oficina_app.application.usecase.vehicle.DeleteVehicleUseCase;
import com.oficinaapp.oficina_app.application.usecase.vehicle.ListMyVehiclesUseCase;
import com.oficinaapp.oficina_app.application.usecase.vehicle.SaveVehicleUseCase;
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

/** The garage of whoever is signed in. */
@RestController
@RequestMapping("/api/me/vehicles")
public class CustomerVehicleController {

    private final ListMyVehiclesUseCase listMyVehicles;
    private final SaveVehicleUseCase saveVehicle;
    private final DeleteVehicleUseCase deleteVehicle;

    public CustomerVehicleController(
            ListMyVehiclesUseCase listMyVehicles,
            SaveVehicleUseCase saveVehicle,
            DeleteVehicleUseCase deleteVehicle
    ) {
        this.listMyVehicles = listMyVehicles;
        this.saveVehicle = saveVehicle;
        this.deleteVehicle = deleteVehicle;
    }

    @GetMapping
    public List<VehicleResponse> list(@AuthenticationPrincipal AuthenticatedUserPrincipal caller) {
        return listMyVehicles.execute(caller.id());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse add(
            @Valid @RequestBody VehicleRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveVehicle.execute(null, request, caller.id());
    }

    @PutMapping("/{id}")
    public VehicleResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequest request,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        return saveVehicle.execute(id, request, caller.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal caller
    ) {
        deleteVehicle.execute(id, caller.id());
    }
}
