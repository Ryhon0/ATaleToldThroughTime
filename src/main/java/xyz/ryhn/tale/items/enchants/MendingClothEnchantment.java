package xyz.ryhn.tale.items.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

public class MendingClothEnchantment extends Enchantment {
	public Item repairItem;

	public MendingClothEnchantment(Item repairItem) {
		super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
		this.repairItem = repairItem;
	}
	
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		if (stack.getItem() instanceof ToolItem t) {
			if (!t.getMaterial().getRepairIngredient().test(repairItem.getDefaultStack()))
				return false;
		} else if (stack.getItem() instanceof ArmorItem a) {
			if (!a.getMaterial().getRepairIngredient().test(repairItem.getDefaultStack()))
				return false;
		}
		return true;
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
