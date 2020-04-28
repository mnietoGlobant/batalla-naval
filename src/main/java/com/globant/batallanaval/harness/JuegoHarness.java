package com.globant.batallanaval.harness;

import com.globant.batallanaval.barco.Arena;
import com.globant.batallanaval.barco.Barco;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class JuegoHarness {

    private WebClient client = WebClient.create("http://localhost:8080");

    private Mono<ClientResponse> result = client.get()
            .uri("/crearArena")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    private Mono<ClientResponse> crearBarco = client.get()
            .uri("/crearBarco")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    private Mono<ClientResponse> resultDisparo = client.get()
            .uri("/disparar")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    public String getResult() {
        return ">> result = " + result.flatMap(res -> res.bodyToMono(Arena.class)).block() +crearBarco.block().rawStatusCode()+ resultDisparo.block().rawStatusCode();
    }
}
