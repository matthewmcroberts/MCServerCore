package com.matthew.template.bukkit.events.listeners;

import com.matthew.template.bukkit.events.customevents.CowExplodeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CowListener implements Listener {

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent e) {
        if(!e.getPlayer().hasPermission("cowexploder.use")) {
            return;
        }

        try {
            if(e.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }
        } catch(Throwable t) {
            //Old mc version
        }

        Entity entity = e.getRightClicked();

        if(entity instanceof Cow) {

            //Custom event, so must invoke it manually
            CowExplodeEvent cowEvent = new CowExplodeEvent(entity);

            //Register custom event with bukkit
            Bukkit.getPluginManager().callEvent(cowEvent);

            if(!cowEvent.isCancelled()) {
                entity.getWorld().createExplosion(entity.getLocation(), cowEvent.getPower());
            }
        }
    }

    @EventHandler
    public void onCowExplode(CowExplodeEvent e) {
        e.setPower(e.getPower() + 3);
    }
}
