package com.globant.batallanaval.barco;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.awt.*;
import java.util.List;
import java.util.UUID;


@Aggregate
public class Barco {
    private List<Point> marks = List.of(new Point(1, 2));
    private int vida = marks.size();
    @AggregateIdentifier
    private UUID id;

    public Barco() {
    }

    @CommandHandler
    public Barco(CrearBarco cmd) {
        id = UUID.randomUUID();
        AggregateLifecycle.apply(new BarcoCreado(id, cmd.x(), cmd.y()));
    }

    public UUID getId() {
        return id;
    }

    @EventSourcingHandler
    public void on(BarcoCreado evento) {
        id = evento.barcoId();
        marks = List.of(new Point(evento.x(), evento.y()));

    }


    @CommandHandler
    public void on(Disparar cmd) {
        if(marks.contains(new Point(cmd.x(),cmd.y()))){
            throw new DispararException("Error, disparo a si mismo");
        }
        AggregateLifecycle.apply(new ProyectilDisparado(id, cmd.x(), cmd.y()));
    }


    @EventSourcingHandler
    public void on(ProyectilDisparado proyectilDisparado) {
        if (marks.contains(new Point(proyectilDisparado.x(), proyectilDisparado.y()))) {
            on(new Impactar(id, proyectilDisparado.barcoId()));
        }
    }

    @EventSourcingHandler
    public void on(Impactado event) {
            vida--;
            if (vida == 0) {
                on(new Hundir(id, event.shipKillerId()));

             }
    }

    @CommandHandler
    public void on(Hundir cmd) {
        AggregateLifecycle.apply(new Hundido(cmd.barcoId(),cmd.barcoEnemigoId()));
    }

    @CommandHandler
    public void on(Impactar cmd) {
        AggregateLifecycle.apply(new Impactado(id,vida,cmd.barcoEnemigoId()));
    }

}
