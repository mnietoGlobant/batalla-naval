package com.globant.batallanaval.barco;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class BarcoTest {
    private FixtureConfiguration<Barco> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Barco.class);
    }

    @Test
    public void givenBarcoCreado_WhenDispara_ThenCoheteEsLanzadoSinImpacto() {
        UUID uuid = UUID.randomUUID();
        fixture.given(new BarcoCreado(uuid, 1, 1))
                .when(new Disparar(uuid, 2, 1))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProyectilDisparado(uuid, 2, 1));
    }

    @Test
    public void givenBarcoCreado_WhenDisparaAsiMismo_thenError() {
        UUID uuid = UUID.randomUUID();
        fixture.given(new BarcoCreado(uuid, 1, 1))
                .when(new Disparar(uuid, 1, 1))
                .expectException(DispararException.class);
    }

    @Test
    public void givenBarcoCreado_WhenEsImpactado_thenEsHundido() {
        UUID uuid = UUID.randomUUID();
        fixture.given(new BarcoCreado(uuid, 1, 1))
                .when(new Impactar(uuid,uuid))
                .expectEvents(new Impactado(uuid, 1, uuid), new Hundido(uuid,uuid));
    }
}
