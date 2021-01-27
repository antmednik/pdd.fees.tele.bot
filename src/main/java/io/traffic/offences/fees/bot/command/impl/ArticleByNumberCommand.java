package io.traffic.offences.fees.bot.command.impl;

import io.traffic.offences.fees.bot.command.BotCommand;
import io.traffic.offences.fees.bot.command.BotCommandCall;
import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import io.traffic.offences.fees.bot.index.TrafficOffenceArticlesReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleByNumberCommand implements BotCommand {

    private final TrafficOffenceArticlesReference trafficOffenceArticlesReference;

    @Override
    public String keyword() {
        return "article";
    }

    @Override
    public String execute(BotCommandCall context) {
        TrafficOffenceArticle article = trafficOffenceArticlesReference.articleByNumber(context.getArguments());
        return article.toString();
    }
}
