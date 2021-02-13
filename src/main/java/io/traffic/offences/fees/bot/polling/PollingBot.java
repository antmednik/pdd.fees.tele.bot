package io.traffic.offences.fees.bot.polling;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import io.traffic.offences.fees.bot.command.BotCommandCall;
import io.traffic.offences.fees.bot.command.BotCommandExecutor;
import io.traffic.offences.fees.bot.persistence.HandledUpdatesStorage;
import io.traffic.offences.fees.bot.persistence.entity.LastHandledUpdate;
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

    private final PollingBotProperties properties;
    private final TelegramApiClient telegramApiClient;
    private final HandledUpdatesStorage handledUpdatesStorage;
    private final BotCommandExecutor botCommandExecutor;
    private final Converter<Update, BotCommandCall> updateBotCommandCallConverter;

    @Scheduled(fixedDelay = 2_000)  // todo: move to properties
    public void poll() {
        try {
            Optional<LastHandledUpdate> lastHandledUpdate = handledUpdatesStorage.lastHandledUpdate();
            log.info("Get last handled update id = '{}'.", lastHandledUpdate.toString());

            GetUpdates getUpdatesRequest = new GetUpdates()
                    .limit(properties.getUpdatesLimit())
                    .timeout((int) properties.getTimeout().toSeconds())
                    .allowedUpdates("message");
            lastHandledUpdate.ifPresent(lhu -> getUpdatesRequest.offset(lhu.getUpdateId() + 1));
            log.info("Get updates request = '{}'.", getUpdatesRequest.getParameters());

            GetUpdatesResponse response = telegramApiClient.getUpdates(getUpdatesRequest);
            log.info("Received updates count = '{}'.", response.updates().size());

            Update update = null;
            for (int index = 0; index < response.updates().size(); index++) {
                update = response.updates().get(index);

                if (lastHandledUpdate.isPresent()
                        && update.updateId() <= lastHandledUpdate.get().getUpdateId()) {
                    log.info("Update with id ='{}' was already handled.", update.updateId());
                    continue;
                }

                Optional<Long> chatId = Optional.of(update).map(Update::message).map(Message::chat).map(Chat::id);
                if (chatId.isEmpty()) {
                    log.info("Chat id is empty for update with id='{}'.", update.updateId());
                    continue;
                }

                BotCommandCall call = updateBotCommandCallConverter.convert(update);
                String commandResponse = botCommandExecutor.execute(call);

                telegramApiClient.sendMessage(new GenericSendMessageRequest(chatId.get(), commandResponse));
            }

            if (update != null &&
                    (lastHandledUpdate.isEmpty()
                            || lastHandledUpdate.get().getUpdateId() < update.updateId())) {
                handledUpdatesStorage.setLastHandledUpdate(update.updateId());
                log.info("Set last handled update to id = '{}'.", update.updateId());
            }
        } catch (Exception ex) {
            log.error("Error received while polling.", ex);
        }
    }
}
