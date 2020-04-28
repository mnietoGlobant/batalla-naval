package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.awt.*;
import java.util.UUID;

public record CrearBarco(
        @TargetAggregateIdentifier
        UUID arenaId,
        Point point) {
}
