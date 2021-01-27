package io.traffic.offences.fees.bot.telegram.generic.dto;

public class GenericUser {
    private final Integer id;
    private final Boolean isBot;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String languageCode;
    private final Boolean canJoinGroups;
    private final Boolean canReadAllGroupMessages;
    private final Boolean supportsInlineQueries;

    public GenericUser(Integer id, Boolean isBot, String firstName, String lastName, String username, String languageCode, Boolean canJoinGroups, Boolean canReadAllGroupMessages, Boolean supportsInlineQueries) { // NOSONAR
        this.id = id;
        this.isBot = isBot;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.languageCode = languageCode;
        this.canJoinGroups = canJoinGroups;
        this.canReadAllGroupMessages = canReadAllGroupMessages;
        this.supportsInlineQueries = supportsInlineQueries;
    }

    public Integer id() {
        return id;
    }

    public Boolean isBot() {
        return isBot;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String username() {
        return username;
    }

    public String languageCode() {
        return languageCode;
    }

    public Boolean canJoinGroups() {
        return canJoinGroups;
    }

    public Boolean canReadAllGroupMessages() {
        return canReadAllGroupMessages;
    }

    public Boolean supportsInlineQueries() {
        return supportsInlineQueries;
    }
}
