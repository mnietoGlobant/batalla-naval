package com.globant.batallanaval.juego.domain.model;

import com.globant.batallanaval.juego.domain.model.commands.CrearJuego;
import com.globant.batallanaval.juego.domain.model.commands.IniciarJuego;
import com.globant.batallanaval.juego.domain.model.commands.RegistrarJugador;
import com.globant.batallanaval.juego.domain.model.events.JuegoCreado;
import com.globant.batallanaval.juego.domain.model.events.JuegoIniciado;
import com.globant.batallanaval.juego.domain.model.events.JugadorRegistrado;
import com.globant.batallanaval.juego.domain.model.excepciones.InsuficientesJugadoresException;
import com.globant.batallanaval.juego.domain.model.excepciones.JuegoYaIniciadoException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.EntityId;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Aggregate
public class Juego {
    @EntityId
    private UUID id;
    private String nombre;
    private Estado estado = Estado.CREADO;

    @AggregateMember
    private final Set<Jugador> jugadores = new HashSet<>();

    private Juego() {
    }

    @CommandHandler
    private Juego(CrearJuego command) {
        AggregateLifecycle.apply(
                new JuegoCreado(command.juegoId(), command.nombre())
        );
    }

    @CommandHandler
    private void handle(RegistrarJugador command) {
        var jugador = new Jugador(UUID.randomUUID(), command.nombre());
        AggregateLifecycle.apply(
                new JugadorRegistrado(command.juegoId(), jugador)
        );
    }

    @CommandHandler
    private void handle(IniciarJuego command) throws JuegoYaIniciadoException, InsuficientesJugadoresException {
        if (this.estado == Estado.INICIADO){
            throw new JuegoYaIniciadoException();
        }

        if (jugadores.size() < 2) {
            throw new InsuficientesJugadoresException();
        }

        AggregateLifecycle.apply(
                new JuegoIniciado(command.juegoId(), jugadores)
        );
    }


    @EventSourcingHandler
    private void on(JuegoCreado event) {
        this.id = event.id();
        this.nombre = event.nombre();
    }

    @EventSourcingHandler
    private void on(JugadorRegistrado event) {
        this.jugadores.add(event.jugador());
    }

    @EventSourcingHandler
    private void on(JuegoIniciado event) {
        this.estado = Estado.INICIADO;
    }
}
