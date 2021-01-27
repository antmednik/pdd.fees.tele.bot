package io.traffic.offences.fees.bot.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BotCommandCall {
    private final String keyword;
    private final String arguments;
}
