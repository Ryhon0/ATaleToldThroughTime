package xyz.ryhn.tale.items.gear;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.ryhn.tale.items.enchants.MendingClothEnchantment;

public class MendingTransferItem extends Item {
	Item[] transferableTo;
	MendingClothEnchantment enchantment;
	SoundEvent transferSound;
	boolean armor;

	public MendingTransferItem(Settings settings, MendingClothEnchantment enchantment, Item[] transferableTo,
			SoundEvent transferSound, boolean armor) {
		super(settings);
		this.transferableTo = transferableTo;
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
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		Hand hand2 = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
		ItemStack stack = user.getStackInHand(hand);
		ItemStack stack2 = user.getStackInHand(hand2);

		for (Item item : transferableTo) {
			if (stack2.getItem() == item && EnchantmentHelper.getLevel(enchantment, stack2) == 0) {
				stack2.addEnchantment(enchantment, 1);

				stack.setCount(0);
				world.playSound(user.getX(), user.getY(), user.getZ(), transferSound, SoundCategory.BLOCKS, 1f, 1f,
						true);
				return TypedActionResult.success(stack);
			}
		}

		return TypedActionResult.fail(stack);
	}
}
