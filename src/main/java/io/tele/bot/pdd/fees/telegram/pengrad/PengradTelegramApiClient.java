package io.tele.bot.pdd.fees.telegram.pengrad;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.WebhookInfo;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetMeResponse;
import com.pengrad.telegrambot.response.GetWebhookInfoResponse;
import io.tele.bot.pdd.fees.telegram.generic.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PengradTelegramApiClient implements TelegramApiClient {

    private final TelegramBot bot;

    public PengradTelegramApiClient(@Value("${pdd.fees.telegram.token}") String token) {
        bot = new TelegramBot(token);
    }

    @Override
    public GenericUser getMe() {

        GetMeResponse response = send(new GetMe());

        return null;
    }

    @Override
    public boolean setWebhook(GenericSetWebhookRequest request) {
        BaseResponse response = send(new SetWebhook().url(request.url()));
        return response.isOk();
    }

    @Override
    public boolean deleteWebhook(Boolean dropPendingUpdates) {
        BaseResponse response = send(new DeleteWebhook());
        return response.isOk();
    }

    @Override
    public GenericWebhookInfo webhookInfo() {
        GetWebhookInfoResponse response = send(new GetWebhookInfo());
        return genericWebhookInfo(response.webhookInfo());
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> R send(BaseRequest<T, R> request) {
        R response = bot.execute(request);
        if (response.isOk()) {
            return response;
        }
        throw new TelegramApiOpException(String.format("Error in response: '%s'.", response));
    }

    private GenericWebhookInfo genericWebhookInfo(WebhookInfo webhookInfo) {
        return new GenericWebhookInfo(
                webhookInfo.url(), webhookInfo.hasCustomCertificate(), webhookInfo.pendingUpdateCount()
        );
    }

    private GenericUser genericUser(User user) {
        return new GenericUser(
                user.id(), user.isBot(), user.firstName(), user.lastName(), user.username(), user.languageCode(),
                user.canJoinGroups(),  user.canReadAllGroupMessages(), user.supportsInlineQueries());
    }
}
