package io.traffic.offences.fees.bot.telegram.generic;

import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import io.traffic.offences.fees.bot.telegram.generic.dto.*;

public interface TelegramApiClient {

    GenericUser getMe();

    boolean setWebhook(GenericSetWebhookRequest request);

    boolean deleteWebhook(Boolean dropPendingUpdates);

    GenericWebhookInfo webhookInfo();

    GenericGetUpdatesResponse getUpdates(GenericGetUpdatesRequest request);

    GetUpdatesResponse getUpdates(GetUpdates request);

    GenericMessage sendMessage(GenericSendMessageRequest request);
}
