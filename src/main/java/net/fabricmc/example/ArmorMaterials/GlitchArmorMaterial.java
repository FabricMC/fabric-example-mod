package net.fabricmc.example;

import net.fabricmc.config;

public class GlitchedArmorMaterial implements ArmorMaterial {

    @Override
	public int getDurability(EquipmentSlot slot) {
		return config.GlitchedArmor.BASE_DURABILITY[slot.getEntitySlotId()];
    }
    
    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return config.GlitchedArmor.PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return config.GlitchedArmor.ENCHANTABILITY;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(RegisterItems.);
    }
}