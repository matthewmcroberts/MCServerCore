package com.matthew.template.bukkit.events.listeners;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class KittenCannonListener implements Listener {

    private final JavaPlugin plugin;

    public KittenCannonListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        if(e.getItem() == null) {
            return;
        }

        if(!(e.getItem().getType() == Material.STICK)) {
            return;
        }

        Player player = e.getPlayer();

        //Using ocelot because Cat interface doesn't work on older releases
        Ocelot cat = player.getWorld().spawn(player.getEyeLocation(), Ocelot.class);

        cat.setVelocity(player.getEyeLocation().getDirection().multiply(2.0));
        cat.getWorld().playSound(cat.getLocation(), Sound.ENTITY_CAT_PURR, 1f, 1f);

        new BukkitRunnable() {

            @Override
            public void run() {
                if(cat.isDead()) {
                    cancel();
                    return;
                }
                if(cat.isOnGround()) {
                    cat.getWorld().createExplosion(cat.getLocation(), 10f);
                    cat.remove();
                    cancel();
                    return;
                }

                cat.getWorld().spawnParticle(Particle.END_ROD, cat.getLocation(), 20);

            }
        }.runTaskTimer(plugin, 0, 5);
    }
}
