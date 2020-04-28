package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.awt.*;
import java.util.UUID;

public record Disparar(
        @TargetAggregateIdentifier
        UUID arenaId,
        UUID barcoId,
        Point point
) {
}
