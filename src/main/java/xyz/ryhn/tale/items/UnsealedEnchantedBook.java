package xyz.ryhn.tale.items;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ClickType;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnsealedEnchantedBook extends Item {
	public int overflow = 0;

	public UnsealedEnchantedBook(int overflow, Settings settings) {
		super(settings);
		this.overflow = overflow;
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player,
			StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && stack.getItem() == this && !otherStack.isEmpty())
			return applyTo(player, stack, otherStack);

		return false;
	}

	Enchantment getEnchantment(ItemStack book) {
		String id = book.getOrCreateNbt().getString("Enchantment");
		Enchantment e = Registries.ENCHANTMENT.get(Identifier.splitOn(id, ':'));
		if (e != null)
			return e;
		else
			return Enchantments.EFFICIENCY;
	}

	public ItemStack of(Enchantment e) {
		ItemStack stack = new ItemStack(this);
		stack.getOrCreateNbt().putString("Enchantment", EnchantmentHelper.getEnchantmentId(e).toString());
		return stack;
	}

	boolean applyTo(PlayerEntity p, ItemStack book, ItemStack tool) {
		Enchantment e = getEnchantment(book);

		if (!e.isAcceptableItem(tool))
			return false;

		int level = EnchantmentHelper.getLevel(e, tool);
		if (e.getMaxLevel() == 1 && level == 1)
			return true;

		if (level >= e.getMaxLevel() + overflow)
			return true;

		for (Enchantment te : EnchantmentHelper.get(tool).keySet()) {
			if (e != te && !e.canCombine(te))
				return true;
		}

		NbtList list = tool.getEnchantments();
		for (int i = 0; i < list.size(); ++i) {
			NbtCompound en = (NbtCompound) list.get(i);
			if (en.getString("id").equals(EnchantmentHelper.getEnchantmentId(e).toString())) {
				list.remove(i);
				break;
			}
		}
		tool.addEnchantment(e, Math.min(level + overflow + 1, e.getMaxLevel() + overflow));

		book.setCount(book.getCount() - 1);
		p.getWorld().playSound(p.getX(), p.getY(), p.getZ(),
				SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1f, 1f, true);

		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack book = user.getStackInHand(hand);
		ItemStack tool = user.getStackInHand(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);

		if(applyTo(user, book, tool))
			return TypedActionResult.success(book);
		return TypedActionResult.fail(book);
	}

	@Override
	public ItemStack getDefaultStack() {
		return of(Enchantments.EFFICIENCY);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(Text.translatable("item.ataletoldthroughtime.unsealed_enchanted_book.lore")
				.styled(s -> s.withColor(Formatting.GRAY)));
		if (overflow > 0)
			tooltip.add(Text.translatable(getEnchantment(stack).getTranslationKey())
					.styled(s -> s.withColor(Formatting.BLUE))
					.append(" +")
					.append(Text.translatable("enchantment.level." + overflow)));
		else
			tooltip.add(Text.translatable(getEnchantment(stack).getTranslationKey())
					.styled(s -> s.withColor(Formatting.BLUE)));

	}
}
