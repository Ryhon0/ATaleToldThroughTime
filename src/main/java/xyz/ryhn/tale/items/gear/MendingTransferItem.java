package xyz.ryhn.tale.items.gear;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.ryhn.tale.Main;
import xyz.ryhn.tale.items.enchants.MendingClothEnchantment;

public class MendingTransferItem extends Item {
	Item repairItem;
	MendingClothEnchantment enchantment;
	SoundEvent transferSound;
	boolean armor;

	public MendingTransferItem(Settings settings, MendingClothEnchantment enchantment, Item repairItem,
			SoundEvent transferSound, boolean armor) {
		super(settings);
		this.repairItem = repairItem;
		this.enchantment = enchantment;
		this.transferSound = transferSound;
		this.armor = armor;
	}

	@Override
	public ItemStack getDefaultStack() {
		ItemStack stack = super.getDefaultStack();
		stack.addEnchantment(enchantment, 1);
		return stack;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(Text.translatable(armor ? "item.tale.mending_plate.lore" : "item.tale.mending_cloth.lore"));
		tooltip.add(Text.translatable("item.tale.mending_effect_lore"));
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player,
			StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT)
			return transferTo(player, stack, otherStack);

		return false;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		Hand hand2 = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
		ItemStack stack = user.getStackInHand(hand);
		ItemStack stack2 = user.getStackInHand(hand2);
		if (transferTo(user, stack, stack2))
			return TypedActionResult.success(stack);
		else
			return TypedActionResult.fail(stack);
	}

	boolean transferTo(PlayerEntity p, ItemStack item, ItemStack tool) {
		if(tool.isEmpty()) return false;

		Main.LOGGER.info(item.toString());
		Main.LOGGER.info(tool.toString());
		if (tool.getItem() instanceof ToolItem t) {
			if (armor)
				return false;

			if (!t.getMaterial().getRepairIngredient().test(repairItem.getDefaultStack()))
				return false;
		} else if (tool.getItem() instanceof ArmorItem a) {
			if (!armor)
				return false;

			if (!a.getMaterial().getRepairIngredient().test(repairItem.getDefaultStack()))
				return false;
		}
		else return false;

		if (EnchantmentHelper.getLevel(enchantment, tool) != 0)
			return true;

		tool.addEnchantment(enchantment, 1);
		item.setCount(item.getCount() - 1);
		p.getWorld().playSound(p.getX(), p.getY(), p.getZ(), transferSound, SoundCategory.PLAYERS, 1f, 1f,
				true);

		return true;
	}
}
