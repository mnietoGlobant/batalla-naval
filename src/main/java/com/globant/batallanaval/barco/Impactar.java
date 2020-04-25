package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

record Impactar (
        @TargetAggregateIdentifier
        UUID barcoId,
        UUID barcoEnemigoId
){
}
