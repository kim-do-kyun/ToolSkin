package org.desp.toolSkin.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.desp.toolSkin.dto.UserDataDto;

public class UserDataRepository {

    private static UserDataRepository instance;
    private final MongoCollection<Document> playerDataDB;
    public Map<String, UserDataDto> playerDataCache = new HashMap<>();

    public UserDataRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.playerDataDB = database.getDatabase().getCollection("UserData");
    }

    public static UserDataRepository getInstance() {
        if (instance == null) {
            instance = new UserDataRepository();
        }
        return instance;
    }

    public void loadPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        String nickname = player.getName();

        Document document = new Document("uuid", uuid);
        if (playerDataDB.find(Filters.eq("uuid", uuid)).first() == null) {
            List<String> pickaxeSkinInventory = new ArrayList<>();
            List<String> fishingRodSkinInventory = new ArrayList<>();

            Document newUserDocument= new Document()
                    .append("nickname", nickname)
                    .append("uuid", uuid)
                    .append("equippedPickaxeSkin", "")
                    .append("pickaxeSkinInventory", pickaxeSkinInventory)
                    .append("equippedFishingRodSkin", "")
                    .append("fishingRodSkinInventory", fishingRodSkinInventory);
            playerDataDB.insertOne(newUserDocument);
        }

        String equippedPickaxeSkin = playerDataDB.find(document).first().getString("equippedPickaxeSkin");
        List<String> pickaxeSkinInventory = playerDataDB.find(document).first().getList("pickaxeSkinInventory", String.class);

        String equippedFishingRodSkin = playerDataDB.find(document).first().getString("equippedFishingRodSkin");
        List<String> fishingRodSkinInventory = playerDataDB.find(document).first().getList("fishingRodSkinInventory", String.class);

        UserDataDto userDataDto = UserDataDto.builder()
                .nickName(nickname)
                .uuid(uuid)
                .equippedPickaxeSkin(equippedPickaxeSkin)
                .pickaxeSkinInventory(pickaxeSkinInventory)
                .equippedFishingRodSkin(equippedFishingRodSkin)
                .fishingRodSkinInventory(fishingRodSkinInventory)
                .build();

        playerDataCache.put(uuid, userDataDto);
    }

    public UserDataDto getPlayerData(Player player) {
        if (!player.isOnline()) {
            return null;
        }
        return playerDataCache.get(player.getUniqueId().toString());
    }

    public void addPickaxeSkin(Player player, String pickaxeSkin) {
        String uuid = player.getUniqueId().toString();
        UserDataDto userDataDto = playerDataCache.get(uuid);
        userDataDto.getPickaxeSkinInventory().add(pickaxeSkin);
        playerDataCache.put(uuid, userDataDto);
    }

    public void savePlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        UserDataDto userDataDto = playerDataCache.get(uuid);

        Document document= new Document()
                .append("nickname", player.getName())
                .append("uuid", uuid)
                .append("equippedPickaxeSkin", userDataDto.getEquippedPickaxeSkin())
                .append("pickaxeSkinInventory", userDataDto.getPickaxeSkinInventory())
                .append("equippedFishingRodSkin", userDataDto.getEquippedFishingRodSkin())
                .append("fishingRodSkinInventory", userDataDto.getFishingRodSkinInventory());

        playerDataDB.replaceOne(
                Filters.eq("uuid", uuid),
                document,
                new ReplaceOptions().upsert(true)
        );
    }
}
