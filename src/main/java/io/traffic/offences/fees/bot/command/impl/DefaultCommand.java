package io.traffic.offences.fees.bot.command.impl;

import io.traffic.offences.fees.bot.command.BotCommand;
import io.traffic.offences.fees.bot.command.BotCommandCall;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommand implements BotCommand {

    @Override
    public String keyword() {
        return "not-found";
    }

    @Override
    public String execute(BotCommandCall context) {
        return "Неизвестная команда :-(";
    }
}
