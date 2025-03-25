package org.desp.toolSkin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class SkinDataDto {
    private String id;
    private String type;
    private String material;
    private int customModelData;
}
