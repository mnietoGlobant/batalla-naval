package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

record BarcoCreado(
        @TargetAggregateIdentifier
        UUID barcoId,
        int x, int y
) {
}
