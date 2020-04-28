package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record ArenaCreada(
        @TargetAggregateIdentifier
        UUID arenaId
) {
}
