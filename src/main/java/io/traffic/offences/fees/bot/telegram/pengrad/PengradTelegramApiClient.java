package io.traffic.offences.fees.bot.telegram.pengrad;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.WebhookInfo;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.*;
import io.traffic.offences.fees.bot.telegram.generic.*;
import io.traffic.offences.fees.bot.telegram.generic.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PengradTelegramApiClient implements TelegramApiClient {

    private final TelegramBot bot;
    private final Converter<User, GenericUser> genericUserConverter;
    private final Converter<GenericGetUpdatesRequest, GetUpdates> genericGetUpdatesRequestConverter;
    private final Converter<GetUpdatesResponse, GenericGetUpdatesResponse> genericGetUpdatesResponseConverter;
    private final Converter<WebhookInfo, GenericWebhookInfo> genericWebhookInfoConverter;
    private final Converter<GenericSendMessageRequest, SendMessage> genericSendMessageRequestSendMessageConverter;
    private final Converter<Message, GenericMessage> messageToGenericMessageConverter;

    @Override
    public GenericUser getMe() {
        GetMeResponse response = send(new GetMe());
        return null;    // todo
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
        return genericWebhookInfoConverter.convert(response.webhookInfo());
    }

    @Override
    public GenericGetUpdatesResponse getUpdates(GenericGetUpdatesRequest request) {
        GetUpdates getUpdatesRequest = genericGetUpdatesRequestConverter.convert(request);
        GetUpdatesResponse originalResponse = send(getUpdatesRequest);
        return genericGetUpdatesResponseConverter.convert(originalResponse);
    }

    @Override
    public GetUpdatesResponse getUpdates(GetUpdates request) {
        return send(request);
    }

    @Override
    public GenericMessage sendMessage(GenericSendMessageRequest request) {
        SendMessage convertedRequest = genericSendMessageRequestSendMessageConverter.convert(request);
        SendResponse response = send(convertedRequest);
        return messageToGenericMessageConverter.convert(response.message());
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> R send(BaseRequest<T, R> request) {
        R response = bot.execute(request);
        if (response.isOk()) {
            return response;
        }
        throw new TelegramApiOpException(String.format("Error in response: '%s'.", response));
    }
}
