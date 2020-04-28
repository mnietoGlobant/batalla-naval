package com.globant.batallanaval.api;


import com.globant.batallanaval.barco.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class JuegoApi {

    public static List<UUID> id = new ArrayList<>();

    private final CommandGateway commandGateway;

    public JuegoApi(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public Mono<ServerResponse> crearBarco(ServerRequest request) {
        String id = request.pathVariable("id");
        UUID res = commandGateway.sendAndWait(new CrearBarco(UUID.fromString(id), new Point(1, 3)));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(res));
    }


    public Mono<ServerResponse> crearArena(ServerRequest request) {
        UUID arenaId = commandGateway.sendAndWait(new CrearArena(UUID.randomUUID()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(new Arena(arenaId)));
    }

    public Mono<ServerResponse> disparar(ServerRequest request) {
        String id = request.pathVariable("id");
        UUID res = commandGateway.sendAndWait(new Disparar(UUID.fromString(id), UUID.randomUUID(), new Point(2, 30)));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(res));
    }

    @EventSourcingHandler
    public void on(Impactado evento) {
        System.out.println("Barco Impactado" + evento.barcoId());
    }

    @EventSourcingHandler
    public void on(Hundido evento) {
        System.out.println("Barco hundido" + evento.barcoId());
    }
}
