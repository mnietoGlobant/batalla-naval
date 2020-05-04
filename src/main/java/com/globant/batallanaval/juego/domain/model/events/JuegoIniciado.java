package com.globant.batallanaval.juego.domain.model.events;

import com.globant.batallanaval.juego.domain.model.Jugador;

import java.util.Set;
import java.util.UUID;

public record JuegoIniciado(
        UUID juegoId,
        Set<Jugador>jugadores) {
}
