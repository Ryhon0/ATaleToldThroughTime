package xyz.ryhn.tale.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(at = @At("HEAD"), method = "appendEnchantments", cancellable = true)
	private static void appendEnchantments(List<Text> tooltip, NbtList enchantments, CallbackInfo info) {
		for (int i = 0; i < enchantments.size(); ++i) {
			NbtCompound nbtCompound = enchantments.getCompound(i);
			Registries.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(nbtCompound)).ifPresent((e) -> {
				int level = EnchantmentHelper.getLevelFromNbt(nbtCompound);
				if(level > e.getMaxLevel())
					tooltip.add(e.getName(level).copy().styled(s -> s.withColor(Formatting.LIGHT_PURPLE)));
				else tooltip.add(e.getName(level));
			});
		}
		info.cancel();
	}

	@Inject(at = @At("TAIL"), method = "isOf", cancellable = true)
	private void isOf(Item i, CallbackInfoReturnable<Boolean> cir) {
		ItemStack stack = (ItemStack) (Object) this;
		if (i == Items.CROSSBOW) {
			cir.setReturnValue(stack.getItem() instanceof CrossbowItem);
		} else if (i == Items.BOW) {
			cir.setReturnValue(stack.getItem() instanceof BowItem);
		}
	}
}