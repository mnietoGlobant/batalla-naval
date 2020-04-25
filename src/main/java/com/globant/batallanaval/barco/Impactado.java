package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

record Impactado(
        @TargetAggregateIdentifier
        UUID shipId,
        int vida,
        UUID shipKillerId
) {
}

