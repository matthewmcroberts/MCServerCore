package com.matthew.template.util.messages;

import com.matthew.template.util.messages.framework.MessageBuilder;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CommandErrorMessage extends MessageBuilder {

    private final String COMMAND_NAME;

    private final List<String> args;

    public CommandErrorMessage(final String commandName, final String... args) {
        super();
        this.COMMAND_NAME = commandName;

        this.args = new ArrayList<>();
        this.args.addAll(Arrays.asList(args));
    }

    @Override
    public String build() {
        append(ChatColor.RED + "Invalid Usage. /");
        append(COMMAND_NAME);

        for (String arg : args) {
            append(" <").append(arg).append(">");
        }
        return message.toString();
    }
}
