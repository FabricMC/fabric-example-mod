package net.fabricmc.example.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin extends Screen {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		System.out.println("This line is printed by an example mod mixin!");
	}
	@Inject(method = "render(IIF)V", at = @At("RETURN"))
	public void render(int mouseX, int mouseY, float delta, CallbackInfo info) {
		this.font.draw("Fabric Test Mod", 2, this.height - 30, -1);
	}
}
