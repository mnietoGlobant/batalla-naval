package com.globant.batallanaval.configuration;

import com.globant.batallanaval.api.JuegoApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class JuegoRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> route(JuegoApi handler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/arenas").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::crearArena)
				.andRoute(RequestPredicates.POST("arenas/{id}/barcos").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::crearBarco)
                .andRoute(RequestPredicates.POST("arenas/{id}/barcos/{barcoId}/disparar").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::disparar);
    }

}
