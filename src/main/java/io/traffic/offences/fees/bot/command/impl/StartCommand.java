package io.traffic.offences.fees.bot.command.impl;

import io.traffic.offences.fees.bot.command.BotCommand;
import io.traffic.offences.fees.bot.command.BotCommandCall;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StartCommand implements BotCommand {

    private final String randomArticleCommandKeyword;
    private final String findArticleCommandKeyword;
    private final String articleByNumberCommandKeyword;

    public StartCommand(RandomArticleCommand randomArticleCommand,
                        FindArticleCommand findArticleCommand,
                        ArticleByNumberCommand articleByNumberCommand) {
        randomArticleCommandKeyword = randomArticleCommand.keyword();
        findArticleCommandKeyword = findArticleCommand.keyword();
        articleByNumberCommandKeyword = articleByNumberCommand.keyword();
    }

    @Override
    public String keyword() {
        return "start";
    }

    @Override
    public String execute(BotCommandCall context) {
        return Stream.of("Доступные команды:",
                "/" + randomArticleCommandKeyword + " - случайная статья",
                "/" + findArticleCommandKeyword + " - поиск статьи по ключевому слову",
                "/" + articleByNumberCommandKeyword + " - поиск статьи по номеру")
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
