package com.matthew.template.bukkit.modules.commands.factory;

import com.matthew.template.bukkit.annotations.RegisterCommand;
import com.matthew.template.bukkit.modules.commands.data.CommandData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandFactory {

    private final String basePackage;

    public CommandFactory(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<CommandData> createCommands() {
        List<CommandData> commands = new ArrayList<>();

        try {
            Reflections reflections = new Reflections(
                    new ConfigurationBuilder()
                            .forPackages(basePackage)
                            .addScanners(Scanners.TypesAnnotated)
            );

            Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(RegisterCommand.class);

            for (Class<?> cls : commandClasses) {
                if (CommandExecutor.class.isAssignableFrom(cls)) {
                    RegisterCommand registerCommand = cls.getAnnotation(RegisterCommand.class);
                    CommandExecutor executor = (CommandExecutor) cls.getDeclaredConstructor().newInstance();
                    commands.add(new CommandData(registerCommand.name(), executor));
                }
            }

        } catch (Exception e) {
            Bukkit.getLogger().severe("Error occurred while scanning for commands in package " + basePackage + ": " + e.getMessage());
        }
        return commands;
    }
}
