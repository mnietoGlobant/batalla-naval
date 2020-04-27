package com.globant.batallanaval.api;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.globant.batallanaval.barco.Barco;

import reactor.core.publisher.Mono;

public class JuegoWebClient {

	 private WebClient client = WebClient.create("http://localhost:8080");

	 private Mono<ClientResponse> result = client.get()
	      .uri("/init")
	      .accept(MediaType.APPLICATION_JSON)
	      .exchange();
	 
	 private Mono<ClientResponse> resultDisparo = client.get()
		      .uri("/disparar")
		      .accept(MediaType.APPLICATION_JSON)
		      .exchange();

	  public String getResult() {
	    return ">> result = " + result.flatMap(res -> res.bodyToMono(Barco.class)).block() + resultDisparo.block().rawStatusCode();
	  }
}
