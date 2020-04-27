package com.globant.batallanaval.barco;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;
import java.awt.Point;

public record CrearArena(
		@TargetAggregateIdentifier
		UUID arenaId
		) {}
