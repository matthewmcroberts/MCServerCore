package com.matthew.template.generalevents.listeners;

import com.matthew.template.generalevents.customevents.CowExplodeEvent;
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
        try {
            if(e.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }
        } catch(Throwable t) {
            //Old mc version
        }

        Entity entity = e.getRightClicked();

        if(entity instanceof Cow) {
            //This is a custom event that we made, so it is not going to be called automatically by bukkit, so we need
            //to fire it ourselves
            CowExplodeEvent cowEvent = new CowExplodeEvent(entity);

            //Again because this is a custom event, we need to make sure to register it with Bukkit and ensure
            //That every plugin sees it.
            Bukkit.getPluginManager().callEvent(cowEvent);

            //Bukkit.getLogger().info(ChatColor.RED + "Cow exploded");

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
