package org.desp.toolSkin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.desp.toolSkin.gui.SkinInventoryGUI;
import org.jetbrains.annotations.NotNull;

public class ToolSkinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,
                             @NotNull String[] strings) {
        Player player = (Player) commandSender;

        SkinInventoryGUI gui = new SkinInventoryGUI(player);
        player.openInventory(gui.getInventory());
        return false;
    }
}
