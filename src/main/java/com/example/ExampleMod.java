package com.example;

// Importing necessary classes
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ExampleMod implements ModInitializer {

	@Override
	public void onInitialize() {
		ServerPlayerEvents.AFTER_RESPAWN.register(this::handlePlayerDeath);

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(CommandManager.literal("upgradearmor")
					.requires(source -> source.hasPermissionLevel(2))
					.executes(context -> {
						if (context.getSource().getEntity() instanceof PlayerEntity) {
							PlayerEntity player = (PlayerEntity) context.getSource().getEntity();
							upgradeArmor(player);
							context.getSource().sendFeedback(() -> Text.of("Armor upgraded to Netherite!"), false);
						}
						return 1;
					}));
		});
	}


	private void handlePlayerDeath(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
		DamageSource source = oldPlayer.getRecentDamageSource();
		if (source != null && source.getAttacker() instanceof PlayerEntity) {
			PlayerEntity killer = (PlayerEntity) source.getAttacker();
			upgradeArmor(killer);
		}

		// Clear only the armor slots of the old player
		for (int i = 0; i < oldPlayer.getInventory().armor.size(); i++) {
			oldPlayer.getInventory().armor.get(i).setCount(0);
		}
	}

	private void upgradeArmor(PlayerEntity player) {
		// Array of Netherite armor items
		Item[] netheriteArmors = new Item[]{
				Items.NETHERITE_BOOTS,
				Items.NETHERITE_LEGGINGS,
				Items.NETHERITE_CHESTPLATE,
				Items.NETHERITE_HELMET
		};
		Item[] DiamondArmors = new Item[]{
				Items.DIAMOND_BOOTS,
				Items.DIAMOND_LEGGINGS,
				Items.DIAMOND_CHESTPLATE,
				Items.DIAMOND_HELMET
		};
		Item[] ironArmors = new Item[]{
				Items.IRON_BOOTS,
				Items.IRON_LEGGINGS,
				Items.IRON_CHESTPLATE,
				Items.IRON_HELMET
		};
		Item[] goldenArmors = new Item[]{
				Items.GOLDEN_BOOTS,
				Items.GOLDEN_LEGGINGS,
				Items.GOLDEN_CHESTPLATE,
				Items.GOLDEN_HELMET
		};
		Item[] leatherArmors = new Item[]{
				Items.LEATHER_BOOTS,
				Items.LEATHER_LEGGINGS,
				Items.LEATHER_CHESTPLATE,
				Items.LEATHER_HELMET
		};

		// Replace each armor piece with its Netherite counterpart
		for (int i = 0; i < player.getInventory().armor.size(); i++) {
			ItemStack currentArmorPiece = player.getInventory().armor.get(i);
			if (!(currentArmorPiece.getItem() instanceof ArmorItem)) continue;

			ItemStack netheriteArmorPiece = new ItemStack(DiamondArmors[i]);
			netheriteArmorPiece.setDamage(currentArmorPiece.getDamage());

			if (currentArmorPiece.hasNbt()) {
				netheriteArmorPiece.setNbt(currentArmorPiece.getNbt().copy());
			}

			player.getInventory().armor.set(i, netheriteArmorPiece);
		}
	}
}
