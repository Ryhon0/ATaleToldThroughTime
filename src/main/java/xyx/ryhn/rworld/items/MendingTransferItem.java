package xyx.ryhn.rworld.items;

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

public class MendingTransferItem extends Item {
	Item[] transferableTo;
	MendingClothEnchantment enchantment;
	SoundEvent transferSound;
	public MendingTransferItem(Settings settings, MendingClothEnchantment enchantment, Item[] transferableTo, SoundEvent transferSound) {
		super(settings);
		this.transferableTo = transferableTo;
		this.enchantment = enchantment;
		this.transferSound = transferSound;
	}

	@Override
	public ItemStack getDefaultStack() {
		ItemStack stack = super.getDefaultStack();
		stack.addEnchantment(enchantment, 1);
		return stack;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		Hand hand2 = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
		ItemStack stack = user.getStackInHand(hand);
		ItemStack stack2 = user.getStackInHand(hand2);

		for (Item item : transferableTo) {
			if(stack2.getItem() == item && EnchantmentHelper.getLevel(enchantment, stack2) == 0)
			{
				stack.setCount(0);
				stack.addEnchantment(enchantment, 1);
				world.playSound(user.getX(), user.getY(), user.getZ(), transferSound, SoundCategory.BLOCKS, 1f, 1f, true);
				return TypedActionResult.success(stack);
			}
		}

		user.sendMessage(Text.literal("No valid item found"));

		return TypedActionResult.fail(stack);
	}
}
