package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Import statements for necessary Minecraft and Fabric classes
// https://github.com/Tutorials-By-Kaupenjoe/Fabric-Tutorial-1.19/blob/main/src/main/java/net/kaupenjoe/tutorialmod/item/ModItems.java
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// Main class of the mod, implementing ModInitializer for initialization logic
public class ExampleMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// Registering an event listener for AFTER_RESPAWN event to handle player deaths
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			handlePlayerDeath(oldPlayer, newPlayer);
		});
	}

	// Method to handle logic when a player dies
	private void handlePlayerDeath(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer) {
		// Getting the last damage source, which could be another player
		DamageSource source = oldPlayer.getRecentDamageSource();
		// Checking if the damage source is a player
		if (source != null && source.getAttacker() instanceof PlayerEntity) {
			PlayerEntity killer = (PlayerEntity) source.getAttacker();
			// Upgrading the killer's armor
			upgradeArmor(killer);
		}

		// Clearing the dead player's armor inventory to prevent it from dropping
		oldPlayer.getInventory().armor.clear();
	}

	// Method to upgrade a player's armor
	private void upgradeArmor(PlayerEntity player) {
		// Iterating through the player's armor slots
		for (int i = 0; i < player.getInventory().armor.size(); i++) {
			ItemStack itemStack = player.getInventory().armor.get(i);

			// Checking if the item in the slot is an instance of ArmorItem
			if (itemStack.getItem() instanceof ArmorItem) {
				ArmorItem armorItem = (ArmorItem) itemStack.getItem();
				ArmorMaterials material = (ArmorMaterials) armorItem.getMaterial();

				// Checking if the armor material is DIAMOND
				if (material == ArmorMaterials.DIAMOND) {
					// Upgrading the armor item to Netherite
					ItemStack upgradedItem = upgradeToNetherite(itemStack);
					// Setting the upgraded item back in the armor slot
					player.getInventory().armor.set(i, upgradedItem);
				}
			}
		}
	}

	// Method to upgrade a diamond armor piece to a Netherite armor piece
	private ItemStack upgradeToNetherite(ItemStack itemStack) {
		// Creating a new ItemStack for the Netherite armor piece
		ItemStack netheriteArmor = new ItemStack(
				switch (itemStack.getItem()) {
					// Mapping each diamond armor piece to its Netherite counterpart
					case Items.DIAMOND_HELMET -> Items.NETHERITE_HELMET;
					case Items.DIAMOND_CHESTPLATE -> Items.NETHERITE_CHESTPLATE;
					case Items.DIAMOND_LEGGINGS -> Items.NETHERITE_LEGGINGS;
					case Items.DIAMOND_BOOTS -> Items.NETHERITE_BOOTS;
					default -> itemStack.getItem(); // Default case to handle unexpected items
				});

		// Copying the enchantments from the old armor to the new armor
		netheriteArmor.setEnchantments(itemStack.getEnchantments());
		// Copying the damage value from the old armor to the new armor
		netheriteArmor.setDamage(itemStack.getDamage());

		// Optionally, adding additional enchantments to the new armor
		netheriteArmor.addEnchantment(Enchantments.PROTECTION, 4);

		// Returning the new, upgraded Netherite armor item
		return netheriteArmor;
	}
}
