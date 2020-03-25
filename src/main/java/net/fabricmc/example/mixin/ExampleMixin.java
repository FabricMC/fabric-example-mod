package net.fabricmc.example.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class ExampleMixin extends Screen {
	private ExampleMixin(Text title) {
		super(title);
	}
	
	@Inject(method = "init()V", at = @At("HEAD"))
	private void init(CallbackInfo info) {
		System.out.println("This line is printed by an example mod mixin!");
	}
	
	@Inject(method = "render(IIF)V", at = @At("RETURN"))
	private void render(int mouseX, int mouseY, float delta, CallbackInfo info) {
		// TODO: Maybe implement a mixin.
		// this.font.draw("Fabric Test Mod", 2, this.height - 30, -1);
		// https://github.com/SpongePowered/Mixin/wiki
	}
}
