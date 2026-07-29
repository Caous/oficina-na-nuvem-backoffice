package com.oficinaapp.oficina_app.infrastructure.integration.fipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The shapes the FIPE service answers with, in its own Portuguese wording.
 * They stop here: everything above sees the English records of the API.
 */
final class FipePayloads {

    private FipePayloads() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Item(String codigo, String nome) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ModelList(List<Item> modelos) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Quote(

            @JsonProperty("Valor") String valor,
            @JsonProperty("Marca") String marca,
            @JsonProperty("Modelo") String modelo,
            @JsonProperty("AnoModelo") Integer anoModelo,
            @JsonProperty("Combustivel") String combustivel,
            @JsonProperty("CodigoFipe") String codigoFipe

    ) {
    }
}
