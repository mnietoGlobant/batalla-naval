package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

record Hundido(
        @TargetAggregateIdentifier
        UUID barcoId,
        UUID barcoEnemigoId) {


}
