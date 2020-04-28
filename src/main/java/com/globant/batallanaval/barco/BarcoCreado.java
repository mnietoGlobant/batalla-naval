package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.awt.*;
import java.util.UUID;

record BarcoCreado(
        UUID barcoId,
        Point point
) {
}
