package org.desp.toolSkin.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.desp.toolSkin.database.UserDataRepository;
import org.desp.toolSkin.dto.UserDataDto;
import org.jetbrains.annotations.NotNull;

public class SkinInventoryGUI implements InventoryHolder {

    private final Inventory inventory = Bukkit.createInventory(this, 54, "장비 스킨 인벤토리");

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public SkinInventoryGUI(Player player) {
        setUpInventory(this.inventory, player);
    }

    public void setUpInventory(Inventory inventory, Player player) {
        UserDataDto playerData = UserDataRepository.getInstance().getPlayerData(player);

        List<String> pickaxeSkinInventory = playerData.getPickaxeSkinInventory();
        List<String> fishingRodSkinInventory = playerData.getFishingRodSkinInventory();

        if(pickaxeSkinInventory == null) {
            pickaxeSkinInventory = new ArrayList<>();
        }
        if (fishingRodSkinInventory == null) {
            fishingRodSkinInventory = new ArrayList<>();
        }

        int slot = 0;
        // 사망이펙트 아이템 값
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7 - 클릭 시 해당 처치 효과를 장착합니다.");
        lore.add("");
        itemMeta.setLore(lore);


        ItemStack uiBlank = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta uiBlankItemMeta = uiBlank.getItemMeta();
        uiBlankItemMeta.setDisplayName("§f");
        uiBlank.setItemMeta(uiBlankItemMeta);

        ItemStack unSetKillEffect = new ItemStack(Material.BARRIER);
        ItemMeta unSetKillEffectItemMeta = unSetKillEffect.getItemMeta();
        unSetKillEffectItemMeta.setDisplayName("§c 사망효과 해제하기");
        unSetKillEffect.setItemMeta(unSetKillEffectItemMeta);

        for(int i = 45; i<54;i++){
            if(i==49){
                inventory.setItem(i, unSetKillEffect);
                continue;
            }
            inventory.setItem(i, uiBlank);
        }
        for (String skin : pickaxeSkinInventory) {
            if(slot > 45){
                break;
            }
            itemMeta.setDisplayName("§f"+skin);
            itemStack.setItemMeta(itemMeta);
            this.inventory.setItem(slot, itemStack);
            slot++;
        }
    }
}
