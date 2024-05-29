package xyz.ryhn.tale.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import xyz.ryhn.tale.items.TaleItems;

@Mixin(DamageEnchantment.class)
public class SmiteSilverBlockerMixin {
	@Inject(at = @At("TAIL"), method = "isAcceptableItem", cancellable = true)
	private void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if ((Object)this == Enchantments.SMITE && info.getReturnValue()) {
			if (stack.getItem() instanceof ToolItem ti &&
					ti.getMaterial().getRepairIngredient().test(TaleItems.SilverSet.ITEM.getDefaultStack())) {
				info.setReturnValue(false);
				return;
			}
		}
	}
}
