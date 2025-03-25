package org.desp.toolSkin.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserDataDto {
    private String nickName;
    private String uuid;
    private String equippedPickaxeSkin;
    private List<String> pickaxeSkinInventory;
    private String equippedFishingRodSkin;
    private List<String> fishingRodSkinInventory;
}
