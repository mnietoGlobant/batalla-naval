package com.globant.batallanaval.juego.domain.model.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.awt.*;
import java.util.UUID;

public record PosicionarBarco(
        @TargetAggregateIdentifier
        UUID juegoId,
        String barco,
        Point point) {
}
