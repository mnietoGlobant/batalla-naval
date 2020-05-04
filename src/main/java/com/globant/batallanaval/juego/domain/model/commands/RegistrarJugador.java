package com.globant.batallanaval.juego.domain.model.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record RegistrarJugador(
        @TargetAggregateIdentifier
        UUID juegoId,
        String nombre) {
}
