package com.globant.batallanaval.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.globant.batallanaval.api.JuegoApi;

@Configuration
public class JuegoRouterConfiguration {
	
	@Bean
	  public RouterFunction<ServerResponse> route(JuegoApi handler) {
	    return RouterFunctions
	      .route(RequestPredicates.GET("/init").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::init)
	      .andRoute(RequestPredicates.GET("/disparar").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::disparar);
	  }

}
