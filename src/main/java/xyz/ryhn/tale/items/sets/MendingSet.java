package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import xyz.ryhn.tale.TaleSounds;
import xyz.ryhn.tale.XPCraftingRecipe;
import xyz.ryhn.tale.items.TaleItems;
import xyz.ryhn.tale.items.enchants.MendingClothEnchantment;
import xyz.ryhn.tale.items.gear.MendingBottle;
import xyz.ryhn.tale.items.gear.MendingTransferItem;

public class MendingSet extends SetGenerator {
	public static ArrayList<MendingSet> Sets = new ArrayList<>();

	public MendingClothEnchantment ENCHANTMENT;
	public MendingBottle BOTTLE;
	public MendingTransferItem CLOTH;
	public MendingTransferItem PLATE;

	public MendingSet(String prefix, Item material, int bottleDurability) {
		super();

		Sets.add(this);
		
		ENCHANTMENT = new MendingClothEnchantment(material);
		BOTTLE = new MendingBottle(new Item.Settings().maxDamage(bottleDurability), ENCHANTMENT);
		CLOTH = new MendingTransferItem(new Item.Settings(), ENCHANTMENT, material, TaleSounds.CLOTH_FASTEN, false);
		PLATE = new MendingTransferItem(new Item.Settings(), ENCHANTMENT, material, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
				true);

		XPCraftingRecipe.addRecipe(new XPCraftingRecipe.Builder()
				.requireItem(Items.GLASS_BOTTLE)
				.requireItem(Items.LAPIS_LAZULI, 5)
				.requireItem(material)
				.build(0, BOTTLE));

		TaleItems.registerEnchantment((Enchantment) ENCHANTMENT, prefix + "_mending");
		TaleItems.registerItem(CLOTH, prefix + "_mending_cloth", ItemGroups.INGREDIENTS);
		TaleItems.registerItem(PLATE, prefix + "_mending_plate", ItemGroups.INGREDIENTS);
		TaleItems.registerItem(BOTTLE, prefix + "_mending_bottle", ItemGroups.INGREDIENTS);
	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(CLOTH, Models.GENERATED);	
		generator.register(PLATE, Models.GENERATED);	
		generator.register(BOTTLE, Models.GENERATED);	
	}
}

