package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleModKt;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// In theory, you can use Kotlin mixin classes, but it is untested and might be unstable.
// But you can call and use Kotlin functions, objects from Mixin without any issues.
// https://github.com/SpongePowered/Mixin/issues/245 for details
@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		ExampleModKt.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
