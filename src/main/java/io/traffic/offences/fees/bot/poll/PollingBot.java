package io.traffic.offences.fees.bot.poll;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import io.traffic.offences.fees.bot.command.BotCommand;
import io.traffic.offences.fees.bot.command.BotCommandCall;
import io.traffic.offences.fees.bot.command.BotCommandExecutor;
import io.traffic.offences.fees.bot.persistence.repository.HandledUpdateRepository;
import io.traffic.offences.fees.bot.telegram.generic.TelegramApiClient;
import io.traffic.offences.fees.bot.telegram.generic.dto.GenericSendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
@Slf4j
public class PollingBot {

    private final TelegramApiClient telegramApiClient;
    private final HandledUpdateRepository handledUpdateRepository;
    private final BotCommandExecutor botCommandExecutor;
    private final Converter<Update, BotCommandCall> updateBotCommandCallConverter;

    @Scheduled(fixedDelay = 2_000)
    public void a() {

        GetUpdatesResponse response = telegramApiClient.getUpdates(new GetUpdates().offset(3).limit(100));
        for (var update : response.updates()) {
            Optional<Long> chatId = Optional.of(update).map(Update::message).map(Message::chat).map(Chat::id);
            if (chatId.isEmpty()) {
                log.info("Chat id is empty for update with id='{}'.", update.updateId());
                continue;
            }

            BotCommandCall call = updateBotCommandCallConverter.convert(update);
            String commandResponse = botCommandExecutor.execute(call);

            telegramApiClient.sendMessage(new GenericSendMessageRequest(chatId.get(), commandResponse));
        }
    }
}
