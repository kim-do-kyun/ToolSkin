package org.desp.toolSkin.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.desp.toolSkin.dto.SkinDataDto;

public class SkinDataRepository {

    private static SkinDataRepository instance;
    private final MongoCollection<Document> skinDataDB;
    public Map<String, SkinDataDto> skinDataCache = new HashMap<>();

    public SkinDataRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.skinDataDB = database.getDatabase().getCollection("SkinData");
    }

    public static SkinDataRepository getInstance() {
        if (instance == null) {
            instance = new SkinDataRepository();
        }
        return instance;
    }

    public void loadPlayerData() {
        FindIterable<Document> documents = skinDataDB.find();
        for (Document document : documents) {
            String id = document.getString("id");
            String type = document.getString("type");
            String material = document.getString("material");
            Integer customModelData = document.getInteger("customModelData");

            SkinDataDto skinDataDto = SkinDataDto.builder()
                    .id(id)
                    .type(type)
                    .material(material)
                    .customModelData(customModelData)
                    .build();

            skinDataCache.put(id, skinDataDto);
        }
    }
}
