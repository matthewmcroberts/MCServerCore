package com.matthew.template.bukkit.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to register commands custom commands.
 * Additionally, also specifies the name of the command
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterCommand {
    String name();
}
