public class RegisterItems {
    public static final ArmorMaterial GlitchArmorMaterial = new CustomArmorMaterial();
    public static final Item GLITCH_MATERIAL = new CustomMaterialItem(new Item.Settings().group(GriegTech.GRIEGTECH_GROUP));
     // If you made a new material, this is where you would note it.
     public static final Item CUSTOM_MATERIAL_HELMET = new ArmorItem(CustomArmorMaterial,
        EquipmentSlot.HEAD, new Item.Settings().group(ExampleMod.EXAMPLE_MOD_GROUP));
     public static final Item CUSTOM_MATERIAL_CHESTPLATE = new ArmorItem(CustomArmorMaterial,
        EquipmentSlot.CHEST, new Item.Settings().group(ExampleMod.EXAMPLE_MOD_GROUP));
     public static final Item CUSTOM_MATERIAL_LEGGINGS = new ArmorItem(CustomArmorMaterial,
        EquipmentSlot.LEGS, new Item.Settings().group(ExampleMod.EXAMPLE_MOD_GROUP));
     public static final Item CUSTOM_MATERIAL_BOOTS = new ArmorItem(CustomArmorMaterial,
        EquipmentSlot.FEET, new Item.Settings().group(ExampleMod.EXAMPLE_MOD_GROUP));
}