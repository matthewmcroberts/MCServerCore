package com.matthew.template.bukkit.events.customevents;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This class serves as a temp quick access template and reminder for creating custom events
 */
public class CowExplodeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Entity cow;
    private float power;
    private boolean cancelled;

    public CowExplodeEvent(Entity cow) {
        this.cow = cow;
        power = 5;
    }

    public Entity getCow() {
        return cow;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
