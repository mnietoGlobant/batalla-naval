package com.globant.batallanaval.harness;

import com.globant.batallanaval.barco.Arena;
import com.globant.batallanaval.barco.Barco;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class JuegoHarness {

    private WebClient client = WebClient.create("http://localhost:8080");

    private Mono<ClientResponse> result = client.post()
            .uri("/arenas")
            .bodyValue("")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    public String getResult() {
        Arena arena = result.flatMap(res -> res.bodyToMono(Arena.class)).block();
        Mono<ClientResponse> crearBarco = client.post()
                .uri("arenas/{id}/barcos", arena.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        Mono<ClientResponse> resultDisparo = client.post()
                .uri("arenas/{id}/barcos/{barcoId}/disparar", arena.getId(),arena.getId())

                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        
        return ">> result = " +  +crearBarco.block().rawStatusCode()+ resultDisparo.block().rawStatusCode();
    }
}
