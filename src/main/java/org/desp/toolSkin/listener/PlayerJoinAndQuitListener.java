package org.desp.toolSkin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.desp.toolSkin.ToolSkin;
import org.desp.toolSkin.database.UserDataRepository;

public class PlayerJoinAndQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(ToolSkin.getInstance(), new Runnable() {
            @Override
            public void run() {
                UserDataRepository.getInstance().loadPlayerData(event.getPlayer());
            }
        }, 20L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserDataRepository.getInstance().savePlayerData(player);
    }
}
