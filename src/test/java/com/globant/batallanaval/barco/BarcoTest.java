package com.globant.batallanaval.barco;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.UUID;

public class BarcoTest {
    private FixtureConfiguration<Arena> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Arena.class);
        fixture.setReportIllegalStateChange(false);
    }

    @Test
    public void givenUnBarco_WhenDispara_ThenProyectilDisparadoNoMasEventos() {
        UUID uuid = UUID.randomUUID();
        UUID barcoUuid = UUID.randomUUID();
        fixture.given(new ArenaCreada(uuid)).andGiven(new BarcoCreado(barcoUuid, new Point(2, 1)))
                .when(new Disparar(uuid, barcoUuid, new Point(2, 1)))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProyectilDisparado(barcoUuid, new Point(2, 1)));
    }

    @Test
    public void givenBarcoCreado_WhenCreaOtroBarcoMismaPosicion_ThenError() {
        UUID uuid = UUID.randomUUID();
        UUID barcoUuid = UUID.randomUUID();
        fixture.given(new ArenaCreada(uuid)).andGiven(new BarcoCreado(barcoUuid, new Point(2, 1)))
                .when(new CrearBarco(uuid, new Point(2, 1)))
                .expectException(CrearBarcoException.class);

    }

    @Test
    public void givenDosBarcos_WhenUnoDispara_ThenElOtroEsImpactoHundido() {
        UUID uuid = UUID.randomUUID();
        UUID barcoUuid = UUID.randomUUID();
        UUID enemigoUuid = UUID.randomUUID();
        fixture.given(new ArenaCreada(uuid)).andGiven(new BarcoCreado(barcoUuid,new Point(1, 1))).andGiven(new BarcoCreado(enemigoUuid,new Point(20, 10)))
                .when(new Disparar(uuid, barcoUuid, new Point(20, 10)))
                .expectEvents( new ProyectilDisparado(barcoUuid, new Point(20, 10)),new Impactado(enemigoUuid,0,barcoUuid), new Hundido(enemigoUuid,barcoUuid));
    }


}
