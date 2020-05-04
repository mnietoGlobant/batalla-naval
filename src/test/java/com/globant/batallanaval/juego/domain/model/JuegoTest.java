package com.globant.batallanaval.juego.domain.model;

import com.globant.batallanaval.juego.domain.model.commands.CrearJuego;
import com.globant.batallanaval.juego.domain.model.commands.IniciarJuego;
import com.globant.batallanaval.juego.domain.model.commands.RegistrarJugador;
import com.globant.batallanaval.juego.domain.model.events.JuegoCreado;
import com.globant.batallanaval.juego.domain.model.events.JuegoIniciado;
import com.globant.batallanaval.juego.domain.model.events.JugadorRegistrado;
import com.globant.batallanaval.juego.domain.model.excepciones.InsuficientesJugadoresException;
import com.globant.batallanaval.juego.domain.model.excepciones.JuegoYaIniciadoException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

class JuegoTest {
    private FixtureConfiguration<Juego> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Juego.class);
        fixture.setReportIllegalStateChange(false);
    }

    @Test
    void WhenCrearJuego_ThenJuegoCreadoNoMasEventos() {
        var juegoId = UUID.randomUUID();
        var nombreJuego = "Juego prueba";

        fixture.givenNoPriorActivity()
                .when(new CrearJuego(juegoId, nombreJuego))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new JuegoCreado(juegoId, nombreJuego));
    }

    @Test
    void givenUnJuego_WhenRegistrarUnJugador_ThenJugadorRegistadoNoMasEventos() {
        var juegoId = UUID.randomUUID();
        var idJugador = UUID.randomUUID();
        var nombreJugador = "Jugador 1";

        fixture.given(new JuegoCreado(juegoId, "nuevo juego"))
                .when(new RegistrarJugador(juegoId, nombreJugador))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new JugadorRegistrado(juegoId, new Jugador(idJugador, nombreJugador)));
    }

    @Test
    void givenUnJuegoYDosJugadores_WhenIniciarJuego_ThenJuegoIniciadoNoMasEventos() {
        var juegoId = UUID.randomUUID();
        var idJugador1 = UUID.randomUUID();
        var nombreJugador1 = "Jugador 1";
        var idJugador2 = UUID.randomUUID();
        var nombreJugador2 = "Jugador 2";

        fixture.given(new JuegoCreado(juegoId, "nuevo juego"))
                .andGiven(new JugadorRegistrado(juegoId, new Jugador(idJugador1, nombreJugador1)))
                .andGiven(new JugadorRegistrado(juegoId, new Jugador(idJugador2, nombreJugador2)))
                .when(new IniciarJuego(juegoId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new JuegoIniciado(juegoId,
                        new HashSet<>(Arrays.asList(
                                new Jugador(idJugador1, nombreJugador1),
                                new Jugador(idJugador2, nombreJugador2))
                        )));
    }

    @Test
    void givenUnJuegoYUnJugador_WhenIniciarJuego_ThenInsuficientesJugadoresException() {
        var juegoId = UUID.randomUUID();
        var idJugador1 = UUID.randomUUID();
        var nombreJugador1 = "Jugador 1";

        fixture.given(new JuegoCreado(juegoId, "nuevo juego"))
                .andGiven(new JugadorRegistrado(juegoId, new Jugador(idJugador1, nombreJugador1)))
                .when(new IniciarJuego(juegoId))
                .expectException(InsuficientesJugadoresException.class);
    }

    @Test
    void givenUnJuegoIniciado_WhenIniciarJuego_ThenJuegoYaIniciadoException() {
        var juegoId = UUID.randomUUID();
        var idJugador1 = UUID.randomUUID();
        var nombreJugador1 = "Jugador 1";
        var idJugador2 = UUID.randomUUID();
        var nombreJugador2 = "Jugador 2";


        fixture.given(new JuegoCreado(juegoId, "nuevo juego"))
                .andGiven(new JugadorRegistrado(juegoId, new Jugador(idJugador1, nombreJugador1)))
                .andGiven(new JugadorRegistrado(juegoId, new Jugador(idJugador2, nombreJugador2)))
                .andGiven(new JuegoIniciado(juegoId,
                        new HashSet<>(Arrays.asList(
                                new Jugador(idJugador1, nombreJugador1),
                                new Jugador(idJugador2, nombreJugador2))
                        )))
                .when(new IniciarJuego(juegoId))
                .expectException(JuegoYaIniciadoException.class);
    }
}
