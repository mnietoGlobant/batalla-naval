package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record Impactado(
        UUID barcoId,
        int vida,
        UUID barcoEnemigoId
) {
}

