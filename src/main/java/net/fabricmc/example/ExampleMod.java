package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;

public class ExampleMod implements ModInitializer, ClientModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
	}
	
	@Override
	public void onInitializeClient() {
		// This code runs on the client side only, and should be used to set up
		// client-side logic, such as render or integrated-server tweaks.
		// Proceed with mild caution.
		
		System.out.println("Hello client world!");
	}
}
