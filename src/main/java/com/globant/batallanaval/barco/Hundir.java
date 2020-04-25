package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

record Hundir(
        @TargetAggregateIdentifier
        UUID barcoId,
        UUID barcoEnemigoId
) {
}

