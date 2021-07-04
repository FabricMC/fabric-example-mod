package net.fabricmc.example.client;

import net.fabricmc.api.ClientModInitializer;

public class ExampleClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This code runs on the client side only, and should be used to set up
		// client-side logic, such as render or integrated-server tweaks.
		// Proceed with mild caution.

		System.out.println("Hello client world!");
	}
}
