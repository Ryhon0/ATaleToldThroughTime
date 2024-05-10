package xyx.ryhn.tale.items.sets;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import xyx.ryhn.tale.TaleSounds;
import xyx.ryhn.tale.XPCraftingRecipe;
import xyx.ryhn.tale.items.TaleItems;
import xyx.ryhn.tale.items.enchants.MendingClothEnchantment;
import xyx.ryhn.tale.items.gear.MendingBottle;
import xyx.ryhn.tale.items.gear.MendingTransferItem;

public class MendingSet {
	public MendingClothEnchantment ENCHANTMENT;
	public MendingBottle BOTTLE;
	public MendingTransferItem CLOTH;
	public MendingTransferItem PLATE;

	public MendingSet(String prefix, Item material, int bottleDurability, Item[] tools, Item[] armors) {
		Item[] toolsAndArmor = new Item[tools.length + armors.length];
		System.arraycopy(tools, 0, toolsAndArmor, 0, tools.length);
		System.arraycopy(armors, 0, toolsAndArmor, tools.length, armors.length);
		ENCHANTMENT = new MendingClothEnchantment(toolsAndArmor);
		BOTTLE = new MendingBottle(new Item.Settings().maxDamage(bottleDurability), ENCHANTMENT);
		CLOTH = new MendingTransferItem(new Item.Settings(), ENCHANTMENT, tools, TaleSounds.CLOTH_FASTEN, false);
		PLATE = new MendingTransferItem(new Item.Settings(), ENCHANTMENT, armors, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
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
}

