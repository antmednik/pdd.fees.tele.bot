package io.traffic.offences.fees.bot.command.impl;

import io.traffic.offences.fees.bot.command.BotCommand;
import io.traffic.offences.fees.bot.command.BotCommandCall;
import io.traffic.offences.fees.bot.domain.TrafficOffenceArticle;
import io.traffic.offences.fees.bot.formatting.TrafficOffenceArticleFormatter;
import io.traffic.offences.fees.bot.index.TrafficOffenceArticlesReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindArticleCommand implements BotCommand {

    private final TrafficOffenceArticlesReference trafficOffenceArticlesReference;
    private final TrafficOffenceArticleFormatter formatter;

    @Override
    public String keyword() {
        return "find";
    }

    @Override
    public String execute(BotCommandCall context) {
        List<TrafficOffenceArticle> candidates = trafficOffenceArticlesReference.search(context.getArguments());
        return formatter.format(candidates);
    }
}
