package xyz.ryhn.tale.items.gear;

import org.joml.Math;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.ryhn.tale.Main;
import xyz.ryhn.tale.items.enchants.MendingClothEnchantment;
import java.util.ArrayList;

public class MendingBottle extends Item {
	public MendingClothEnchantment enchantment;

	public MendingBottle(Settings settings, MendingClothEnchantment enchantment) {
		super(settings);
		this.enchantment = enchantment;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack bottle = user.getStackInHand(hand);
		boolean success = false;

		ArrayList<ItemStack> repairable = new ArrayList<>();

		for (int i = 0; i < user.getInventory().size(); ++i) {
			ItemStack stack = user.getInventory().getStack(i);
			if (!stack.isEmpty() && stack.isDamaged() && EnchantmentHelper.getLevel(enchantment, stack) > 0)
			{
				Main.LOGGER.info(stack + "");
				repairable.add(stack);
			}
		}

		if (repairable.size() == 0)
			return TypedActionResult.fail(bottle);

		int points = Math.min(400, bottle.getMaxDamage() - bottle.getDamage());
		int by = points / repairable.size();
		int remainder = points - (by * repairable.size());
		for (int i = 0; i < repairable.size(); i++) {
			if (bottle.isEmpty()) break;

			int thisBy = by;
			if (i < remainder)
				thisBy += 1;
			ItemStack stack = repairable.get(i);

			if (repair(user, bottle, stack, thisBy))
				success = true;
		}

		if (success) {
			world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_WATER,
					SoundCategory.BLOCKS, 1f, 1f, true);
			return TypedActionResult.success(bottle, false);
		} else
			return TypedActionResult.pass(bottle);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player,
			StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT)
		{
			World world = player.getWorld();
			if(repair(player, stack, otherStack, 100))
			{
				world.playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_WATER,
					SoundCategory.BLOCKS, 1f, 1f, true);
				return true;
			}
		}

		return false;
	}

	boolean repair(PlayerEntity p, ItemStack bottle, ItemStack tool, int by) {
		if (EnchantmentHelper.getLevel(enchantment, tool) == 0)
			return false;

		if (by == -1)
			by = bottle.getMaxDamage() - bottle.getDamage();

		by = Math.min(by, bottle.getMaxDamage() - bottle.getDamage());

		int dmg = tool.getDamage();
		dmg = Math.min(dmg, by);

		if (dmg <= 0)
			return false;

		tool.damage(-dmg, p.getRandom(), null);
		if (bottle.damage(dmg, p.getRandom(), null)) {
			bottle.setCount(0);
			p.getWorld().playSound(p.getX(), p.getY(), p.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK,
					SoundCategory.PLAYERS, 1f, 1f, true);
		}
		return true;
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
