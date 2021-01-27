package io.traffic.offences.fees.bot.command;

import io.traffic.offences.fees.bot.command.impl.DefaultCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BotCommandExecutor {

    private final Map<String, BotCommand> keywordToBotCommandMap;
    private final BotCommand defaultCommand;

    public BotCommandExecutor(List<BotCommand> botCommands, DefaultCommand defaultCommand) {
        this.keywordToBotCommandMap = botCommands.stream()
                .collect(Collectors.toMap(BotCommand::keyword, Function.identity()));
        this.defaultCommand = defaultCommand;
    }

    public String execute(BotCommandCall call) {
        BotCommand command = keywordToBotCommandMap.getOrDefault(call.getKeyword(), defaultCommand);
        return command.execute(call);
    }
}
