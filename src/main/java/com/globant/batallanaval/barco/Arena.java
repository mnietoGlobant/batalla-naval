package com.globant.batallanaval.barco;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Aggregate
public class Arena {

    @AggregateIdentifier
    private UUID id;

    @AggregateMember
    private List<Barco> barcos = new ArrayList<>();

    private UUID arenaId;

    public Arena() {
    }

    @CommandHandler
    public Arena(CrearArena cmd) {
        AggregateLifecycle.apply(new ArenaCreada(UUID.randomUUID()));
    }

    public Arena(UUID arenaId) {

        this.arenaId = arenaId;
    }

    public UUID getId() {
        return id;
    }

    @CommandHandler
    public UUID handle(CrearBarco cmd) {
        if (barcos.stream().anyMatch(barco -> barco.hasPoint(cmd.point()))){
            throw new CrearBarcoException("");
        }
        UUID id = UUID.randomUUID();
        AggregateLifecycle.apply(new BarcoCreado(id, cmd.point()));
        return id;
    }

    @CommandHandler
    public void on(Disparar cmd) {
        AggregateLifecycle.apply(new ProyectilDisparado(cmd.barcoId(), cmd.point()));
    }

    @EventSourcingHandler
    public void on(BarcoCreado evento) {
        barcos.add(new Barco(evento.barcoId(), evento.point()));
    }

    @EventSourcingHandler
    public void on(ArenaCreada evento) {
        id = evento.arenaId();
    }

    @EventSourcingHandler
    public void on(Impactado evento) {
        System.out.println("Barco impactado " + evento.barcoId());
    }

    @EventSourcingHandler
    public void on(Hundido evento) {
        System.out.println("Barco hundido " + evento.barcoId());
    }
}
