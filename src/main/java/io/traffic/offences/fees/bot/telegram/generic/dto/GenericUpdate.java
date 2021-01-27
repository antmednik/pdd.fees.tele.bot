package io.traffic.offences.fees.bot.telegram.generic.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@RequiredArgsConstructor
@Getter
public class GenericUpdate {
    private final Integer updateId;
    private final GenericMessage message;
}
