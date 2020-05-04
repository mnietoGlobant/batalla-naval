package com.globant.batallanaval.juego.domain.model.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record BarcoPosicionado(
        UUID juegoId,
        String barco) {
}
