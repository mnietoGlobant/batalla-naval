package com.globant.batallanaval.juego.domain.model;

import org.axonframework.modelling.command.EntityId;

import java.util.Objects;
import java.util.UUID;

public class Jugador {
    @EntityId
    private UUID id;
    private String nombre;

    public Jugador(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return nombre.equals(jugador.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
