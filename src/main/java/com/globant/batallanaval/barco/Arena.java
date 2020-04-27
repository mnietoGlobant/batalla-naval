package com.globant.batallanaval.barco;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Arena {
	
	 @AggregateIdentifier
	 private UUID id;

	  @AggregateMember
	  private List<Barco> barcos = new ArrayList<>();

	  
	  public Arena() {
	  }
	  
	  public UUID getId() {
		  return id;
	  }
	  
	  
	  @CommandHandler
	   public Arena(CrearArena cmd) {
		  AggregateLifecycle.apply(new ArenaCreada(UUID.randomUUID()));
	   }
	  
	  
	  @CommandHandler
	   public void handle(CrearBarco cmd) {
		  AggregateLifecycle.apply(new BarcoCreado(UUID.randomUUID(), cmd.point()));
	   }
	  
	  
	  @CommandHandler
	    public void on(Disparar cmd) {
	       
	        barcos.stream().filter(barco -> !barco.getId().equals(cmd.origenId())).forEach(barco -> AggregateLifecycle.apply(new ProyectilDisparado(barco.getId(), cmd.x(), cmd.y())));
	        
	    }
	  
	  
	  @EventSourcingHandler
	  public void on(BarcoCreado evento) {
	        barcos.add(new Barco (evento.barcoId(),evento.point()));
	  }
	  
	  @EventSourcingHandler
	  public void on(ArenaCreada evento) {
		  id = evento.arenaId();
	  }
}
