package com.matthew.template.bukkit.utils.messages.framework;

public abstract class MessageBuilder {

    private final String MESSAGE_PREFIX = "&9>>";

    protected StringBuilder message;

    public MessageBuilder() {
        message = new StringBuilder();
        message.append(MESSAGE_PREFIX);
    }

    public abstract String build();

    public void clear() {
        message.setLength(0);
    }

    public MessageBuilder append(String text) {
        message.append(text);
        return this; //used for method chaining
    }

    public void appendLine(String text) {
        message.append(text).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        return message.toString();
    }
}
