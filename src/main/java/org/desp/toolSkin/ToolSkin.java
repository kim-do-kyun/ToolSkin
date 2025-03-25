package org.desp.toolSkin;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.desp.toolSkin.database.SkinDataRepository;

public final class ToolSkin extends JavaPlugin {

    @Getter
    private static ToolSkin instance;

    @Override
    public void onEnable() {
        instance = this;

        SkinDataRepository.getInstance().loadPlayerData();

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
