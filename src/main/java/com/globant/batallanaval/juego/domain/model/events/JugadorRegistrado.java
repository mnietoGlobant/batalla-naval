package com.globant.batallanaval.juego.domain.model.events;

import com.globant.batallanaval.juego.domain.model.Jugador;

import java.util.UUID;

public record JugadorRegistrado(
        UUID juegoId,
        Jugador jugador) {
}
