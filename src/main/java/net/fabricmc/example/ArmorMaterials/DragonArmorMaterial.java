package net.fabricmc.example;

import net.fabricmc.config;

public class DragonArmorMaterial implements ArmorMaterial {

    @Override
	public int getDurability(EquipmentSlot slot) {
		return config.DragonArmor.BASE_DURABILITY[slot.getEntitySlotId()] * ;
	}
}