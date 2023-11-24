package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ExampleMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Registering an event listener for AFTER_RESPAWN event to handle player deaths
        // This event is triggered in real-time when a player respawns after dying
        ServerPlayerEvents.AFTER_RESPAWN.register(this::handlePlayerDeath);

        // Registering a command "/upgradearmor" that can be executed in real-time during the game
        // This command allows players with the right permissions to upgrade their armor instantly
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("upgradearmor")
                .requires(source -> source.hasPermissionLevel(2)) // Command requires the player to have OP level 2
                .executes(context -> {
                    // Executing the command to upgrade armor
                    if (context.getSource().getEntity() instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) context.getSource().getEntity();
                        upgradeArmor(player); // Upgrading the player's armor
                        context.getSource().sendFeedback(new LiteralText("Armor upgraded to Netherite!"), false);
                    }
                    return 1; // Command executed successfully
                }));
        });
    }

    private void handlePlayerDeath(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        // This method is triggered when a player dies and respawns
        // It handles the logic of what happens after a player's death in real-time
        DamageSource source = oldPlayer.getRecentDamageSource();
        if (source != null && source.getAttacker() instanceof PlayerEntity) {
            PlayerEntity killer = (PlayerEntity) source.getAttacker();
            upgradeArmor(killer); // Upgrading the killer's armor
        }
        oldPlayer.getInventory().armor.clear(); // Clearing the dead player's armor to prevent dropping
    }

    private void upgradeArmor(PlayerEntity player) {
        // This method upgrades the player's armor to Netherite in real-time
        // It's called when the command is executed or after killing another player
        player.getInventory().armor.forEach(itemStack -> {
            if (!(itemStack.getItem() instanceof ArmorItem)) return; // Skip if not an armor item
            ArmorItem armorItem = (ArmorItem) itemStack.getItem();
            if (armorItem.getMaterial() != ArmorMaterials.DIAMOND) return; // Skip if not diamond material

            ItemStack netheriteArmor = new ItemStack(getNetheriteCounterpart(itemStack.getItem()));
            netheriteArmor.setEnchantments(itemStack.getEnchantments()); // Copy enchantments
            netheriteArmor.setDamage(itemStack.getDamage()); // Copy damage value

            itemStack.setCount(0); // Remove the old diamond armor
            player.getInventory().armor.set(player.getInventory().armor.indexOf(itemStack), netheriteArmor); // Replace with Netherite armor
        });
    }

    private Item getNetheriteCounterpart(Item item) {
        // This method returns the Netherite counterpart of a diamond armor item
        // It's used to map each diamond armor piece to its corresponding Netherite piece
        return switch (item) {
            case Items.DIAMOND_HELMET -> Items.NETHERITE_HELMET;
            case Items.DIAMOND_CHESTPLATE -> Items.NETHERITE_CHESTPLATE;
            case Items.DIAMOND_LEGGINGS -> Items.NETHERITE_LEGGINGS;
            case Items.DIAMOND_BOOTS -> Items.NETHERITE_BOOTS;
            default -> item; // Return the item itself if it's not a diamond armor piece
        };
    }
}
