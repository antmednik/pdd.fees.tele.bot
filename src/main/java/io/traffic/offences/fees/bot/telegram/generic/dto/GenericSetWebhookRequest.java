package io.traffic.offences.fees.bot.telegram.generic.dto;

public class GenericSetWebhookRequest {

    private final String url;

    public GenericSetWebhookRequest(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
