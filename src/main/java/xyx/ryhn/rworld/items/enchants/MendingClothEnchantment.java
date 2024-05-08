package xyx.ryhn.rworld.items.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MendingClothEnchantment extends Enchantment {
	public Item[] validItems;

	public MendingClothEnchantment(Item[] validItems) {
		super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
		this.validItems = validItems;
	}
	
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		for (Item item : validItems) {
			if(stack.getItem() == item)
				return true;
		}
		return false;
	}

	@Override
	public boolean isAvailableForEnchantedBookOffer() {
		return false;
	}

	@Override
	public boolean isAvailableForRandomSelection() {
		return false;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}
}
