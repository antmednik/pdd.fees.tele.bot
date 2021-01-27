package io.traffic.offences.fees.bot.telegram.generic.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@RequiredArgsConstructor
@Getter
public class GenericSendMessageRequest {
    private final Long chatId;
    private final String text;
}
