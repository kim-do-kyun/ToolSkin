package org.desp.toolSkin;

import java.util.Collection;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.desp.toolSkin.command.ToolSkinCommand;
import org.desp.toolSkin.database.SkinDataRepository;
import org.desp.toolSkin.database.UserDataRepository;
import org.desp.toolSkin.listener.PlayerJoinAndQuitListener;

public final class ToolSkin extends JavaPlugin {

    @Getter
    private static ToolSkin instance;

    @Override
    public void onEnable() {
        instance = this;

        SkinDataRepository.getInstance().loadPlayerData();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinAndQuitListener(), this);
        getCommand("아이템스킨").setExecutor(new ToolSkinCommand());

        // Plugin startup logic
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            UserDataRepository.getInstance().loadPlayerData(player);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            UserDataRepository.getInstance().savePlayerData(player);
        }
    }
}
