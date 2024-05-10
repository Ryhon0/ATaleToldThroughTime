package xyx.ryhn.tale.items.gear;

import org.joml.Math;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyx.ryhn.tale.items.enchants.MendingClothEnchantment;

public class MendingBottle extends Item {
	public MendingClothEnchantment enchantment;

	public MendingBottle(Settings settings, MendingClothEnchantment enchantment) {
		super(settings);
		this.enchantment = enchantment;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack bottleStack = user.getStackInHand(hand);
		boolean success = false;
		for (int i = 0; i < user.getInventory().size(); ++i) {
			ItemStack itemStack = user.getInventory().getStack(i);
			if (EnchantmentHelper.getLevel(enchantment, itemStack) != 0) {
				int dmg = itemStack.getDamage();
				int capacity = bottleStack.getMaxDamage() - bottleStack.getDamage();
				dmg = Math.min(dmg, capacity);

				if (dmg > 0) {
					itemStack.damage(-dmg, user.getRandom(), null);
					success = true;
					if (bottleStack.damage(dmg, user.getRandom(), null)) {
						bottleStack.setCount(0);
						world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK,
								SoundCategory.BLOCKS, 1f, 1f, true);
						break;
					}
				}
			}
		}

		if (success) {
			world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
					SoundCategory.BLOCKS, 1f, 1f, true);
			user.getItemCooldownManager().set(this, 20);
			return TypedActionResult.success(bottleStack, false);
		} else
			return TypedActionResult.pass(bottleStack);
	}

	@Override
	public int getItemBarColor(ItemStack stack) {
		return 0xb9e45a;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}
