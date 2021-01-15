package io.tele.bot.pdd.fees.telegram.generic;

public class GenericSetWebhookRequest {

    private final String url;

    public GenericSetWebhookRequest(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
