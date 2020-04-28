package com.globant.batallanaval.barco;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;
import java.util.UUID;


public class Barco {
    private List<Point> marks;
    private int vida ;
    @EntityId
    private UUID id;

    public Barco() {
    }


    public Barco(UUID barcoId, Point point) {
        id = barcoId;
        marks =List.of(point);
        vida = marks.size();
    }

    public UUID getId() {
        return id;
    }


    @EventSourcingHandler
    public void on(ProyectilDisparado evento) {
        if (!evento.barcoId().equals(id) && marks.contains(evento.point())) {
            vida--;
            AggregateLifecycle.apply(new Impactado(id, vida, evento.barcoId()));
            if (vida == 0) {
                AggregateLifecycle.apply(new Hundido(id, evento.barcoId()));
            }
      }
    }

    public boolean hasPoint(Point point){
        return marks.contains(point);
    }

}
