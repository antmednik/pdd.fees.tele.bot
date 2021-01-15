package io.tele.bot.pdd.fees.telegram.generic;

public interface TelegramApiClient {

    GenericUser getMe();

    boolean setWebhook(GenericSetWebhookRequest request);

    boolean deleteWebhook(Boolean dropPendingUpdates);

    GenericWebhookInfo webhookInfo();
}
