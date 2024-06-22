package com.matthew.template.bukkit.modules.commands.factory;

import com.matthew.template.bukkit.annotations.CommandInfo;
import com.matthew.template.bukkit.modules.commands.data.CommandData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CommandFactory {

    private final String basePackage;

    public CommandFactory(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<CommandData> createCommands() {
        List<CommandData> commands = new ArrayList<>();
        String path = basePackage.replace('.', '/');

        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
            List<File> directories = new ArrayList<>();

            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                directories.add(new File(url.getFile()));
            }

            for (File directory : directories) {
                commands.addAll(findCommands(directory, basePackage));
            }

        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            Bukkit.getLogger().severe("Error occurred while creating commands from " + path + ": " + e.getMessage());
        }
        return commands;
    }

    private List<CommandData> findCommands(File directory, String packageName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<CommandData> commands = new ArrayList<>();

        if (!directory.exists()) {
            return commands;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return commands;
        }

        for (File file : files) {
            String fileName = file.getName();
            if (file.isDirectory()) {
                commands.addAll(findCommands(file, packageName + "." + fileName));
            } else if (fileName.endsWith(".class")) {
                String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                Class<?> cls = Class.forName(className);

                if (cls.isAnnotationPresent(CommandInfo.class) && CommandExecutor.class.isAssignableFrom(cls)) {
                    CommandInfo commandInfo = cls.getAnnotation(CommandInfo.class);
                    CommandExecutor executor = (CommandExecutor) cls.newInstance();
                    commands.add(new CommandData(commandInfo.name(), executor));
                }
            }
        }

        return commands;
    }
}
