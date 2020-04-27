package com.globant.batallanaval.api;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;


import com.globant.batallanaval.barco.Barco;
import com.globant.batallanaval.barco.CrearBarco;
import com.globant.batallanaval.barco.Disparar;
import com.globant.batallanaval.barco.Impactado;
import com.globant.batallanaval.barco.Hundido;

import com.globant.batallanaval.barco.CrearArena;
import com.globant.batallanaval.barco.ArenaCreada;


import reactor.core.publisher.Mono;

@Component
public class JuegoApi {
	
	public static List<UUID> id = new ArrayList<>();
	static  UUID arenaId;
	
    private final CommandGateway commandGateway;

	
    public JuegoApi (CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
    	
    }
    
	public Mono<ServerResponse> init(ServerRequest request) {
		  arenaId = commandGateway.sendAndWait(new CrearArena(UUID.randomUUID()));
	     id.add(commandGateway.sendAndWait(new CrearBarco(arenaId, new Point(1,3))));
	     commandGateway.sendAndWait(new CrearBarco(arenaId, new Point(2,30)));
		   return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
		      .body(fromObject(new Barco(UUID.randomUUID(),new Point(1,2))));
		  }
	
	
	public Mono<ServerResponse> disparar(ServerRequest request) {
		commandGateway.sendAndWait(new Disparar(arenaId,id.get(0), 2,30));
	       
		   return ServerResponse.ok().build();
		  }

	
	 @EventSourcingHandler
	    public void on(Impactado evento) {
	    	System.out.println("Barco Impactado" +evento.barcoId());
			
	    }
	 
	 
	 @EventSourcingHandler
	    public void on(Hundido evento) {
	    	System.out.println("Barco hundido" +evento.barcoId());
			
	    }
}
