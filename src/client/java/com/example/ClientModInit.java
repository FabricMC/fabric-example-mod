import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ClientModInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 모드 시작 시 기존 번역 파일 로드
        TranslationStorage.load();

        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (lines.isEmpty()) return;

            String originalName = lines.get(0).getString();
            
            // 한글이 포함되지 않은 경우만 처리 (이미 한글이면 건너뜀)
            if (!originalName.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                String translated = TranslationStorage.getOrAdd(originalName);

                if (translated != null) {
                    // 번역 결과가 있을 때
                    lines.add(1, Text.literal("🇰🇷 번역: " + translated).formatted(Formatting.GREEN));
                } else {
                    // 수집은 되었으나 아직 번역값이 없을 때
                    lines.add(1, Text.literal("⌛ 번역 대기 중... (파일을 확인하세요)").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
                }
            }
        });
    }
}
