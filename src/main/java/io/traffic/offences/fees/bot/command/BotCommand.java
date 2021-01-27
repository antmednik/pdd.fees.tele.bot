package io.traffic.offences.fees.bot.command;

public interface BotCommand {

    String keyword();

    String execute(BotCommandCall context);
}
