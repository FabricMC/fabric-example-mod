package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {

	// This is a generic Item template
	// public static final Item FabricItem = new Item(new FabricItemSettings().group(ItemGroup.MISC));

	// VT Tutorial Book
	// Implemented by Jerbyl
	public static final TutorialBook TUTORIAL_BOOK = new TutorialBook(new FabricItemSettings().group(ItemGroup.MISC));

	public static final RingOfFlight RING_OF_FLIGHT = new RingOfFlight(new FabricItemSettings().group(ItemGroup.MISC));


	// Charcoal block
	// Implemented by Jerbyl
	public static final Block BLOCK_OF_CHARCOAL = new Block(FabricBlockSettings.of(Material.STONE).hardness(5.0f));

	// Charcoal block
	// Implemented by Jerbyl
	public static final Block EXPERIENCE_CAGE = new Block(FabricBlockSettings.of(Material.STONE).hardness(5.0f));

	// Gold-plated diamond armor items
	// Implemented by Jerbyl
	public static final Item GOLD_DIAMOND_HELMET = new ArmorItem(GoldDiamond.GOLD_DIAMOND, EquipmentSlot.HEAD, (new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_DIAMOND_CHESTPLATE = new ArmorItem(GoldDiamond.GOLD_DIAMOND, EquipmentSlot.CHEST, (new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_DIAMOND_LEGGINGS = new ArmorItem(GoldDiamond.GOLD_DIAMOND, EquipmentSlot.LEGS, (new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_DIAMOND_BOOTS = new ArmorItem(GoldDiamond.GOLD_DIAMOND, EquipmentSlot.FEET, (new Item.Settings().group(ItemGroup.COMBAT)));

	// Gold-plated netherite armor items
	// Implemented by Jerbyl
	public static final Item GOLD_NETHERITE_HELMET = new ArmorItem(GoldNetherite.GOLD_NETHERITE, EquipmentSlot.HEAD, (new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_NETHERITE_CHESTPLATE = new ArmorItem(GoldNetherite.GOLD_NETHERITE, EquipmentSlot.CHEST, (new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_NETHERITE_LEGGINGS = new ArmorItem(GoldNetherite.GOLD_NETHERITE, EquipmentSlot.LEGS, (new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item GOLD_NETHERITE_BOOTS = new ArmorItem(GoldNetherite.GOLD_NETHERITE, EquipmentSlot.FEET, (new Item.Settings().group(ItemGroup.COMBAT)));


	@Override
	public void onInitialize() {

		// Adds registry entry for VT Tutorial Book
		// Implemented by Jerbyl
		Registry.register(Registry.ITEM, new Identifier("examplemod", "tutorial_book"), TUTORIAL_BOOK);

		// Adds registry entry for Ring Of Flight
		// Implemented by Jerbyl
		Registry.register(Registry.ITEM, new Identifier("examplemod", "ring_of_flight"), RING_OF_FLIGHT);

		// Adds registry entries for the Charcoal_Block item and block
		// Implemented by Jerbyl
		Registry.register(Registry.BLOCK, new Identifier("examplemod", "block_of_charcoal"), BLOCK_OF_CHARCOAL);
		Registry.register(Registry.ITEM, new Identifier("examplemod", "block_of_charcoal"), new BlockItem(BLOCK_OF_CHARCOAL, new Item.Settings().group(ItemGroup.MISC)));

		// Adds registry entries for gold-trimmed diamond and netherite armors.
		//  Implemented by Jerbyl
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_diamond_helmet"), GOLD_DIAMOND_HELMET);
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_diamond_chestplate"), GOLD_DIAMOND_CHESTPLATE);
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_diamond_leggings"), GOLD_DIAMOND_LEGGINGS);
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_diamond_boots"), GOLD_DIAMOND_BOOTS);
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_netherite_helmet"), GOLD_NETHERITE_HELMET);
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_netherite_chestplate"), GOLD_NETHERITE_CHESTPLATE);
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_netherite_leggings"), GOLD_NETHERITE_LEGGINGS);
		Registry.register(Registry.ITEM,new Identifier("examplemod","gold_netherite_boots"), GOLD_NETHERITE_BOOTS);
	}

}