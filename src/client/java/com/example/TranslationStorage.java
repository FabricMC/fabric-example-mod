import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TranslationStorage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, String> translationMap = new HashMap<>();
    private static File configFile;

    public static void load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("item_translations.json");
        configFile = configPath.toFile();

        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Map<String, String> loaded = GSON.fromJson(reader, new TypeToken<Map<String, String>>(){}.getType());
                if (loaded != null) translationMap.putAll(loaded);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(translationMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getOrAdd(String original) {
        if (translationMap.containsKey(original)) {
            String translated = translationMap.get(original);
            // 번역값이 비어있으면 원문을 보여주거나 상태를 알려줌
            return (translated == null || translated.isEmpty()) ? null : translated;
        } else {
            // 처음 발견한 아이템이면 목록에 추가하고 저장
            translationMap.put(original, ""); // 나중에 채워넣을 수 있도록 빈값 저장
            save();
            return null;
        }
    }
}
