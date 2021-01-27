package io.traffic.offences.fees.bot.telegram.generic.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@RequiredArgsConstructor
@Getter
public class GenericGetUpdatesResponse {
    private final List<GenericUpdate> updates;
}
