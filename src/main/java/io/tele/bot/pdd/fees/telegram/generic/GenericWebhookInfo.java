package io.tele.bot.pdd.fees.telegram.generic;

public class GenericWebhookInfo {
    private final String url;
    private final Boolean hasCustomCertificate;
    private final Integer pendingUpdateCount;

    public GenericWebhookInfo(String url, Boolean hasCustomCertificate, Integer pendingUpdateCount) {
        this.url = url;
        this.hasCustomCertificate = hasCustomCertificate;
        this.pendingUpdateCount = pendingUpdateCount;
    }

    public String url() {
        return url;
    }

    public Boolean hasCustomCertificate() {
        return hasCustomCertificate;
    }

    public Integer pendingUpdateCount() {
        return pendingUpdateCount;
    }
}
