package com.globant.batallanaval.juego.domain.model.events;

import java.util.UUID;

public record JuegoCreado(
        UUID id,
        String nombre) {
}
