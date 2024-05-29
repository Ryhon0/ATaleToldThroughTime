package xyz.ryhn.tale.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import xyz.ryhn.tale.items.TaleItems;

@Mixin(EnchantmentHelper.class)
public class BuiltinSilverSmiteMixin {
	@Inject(at = @At("TAIL"), method = "getAttackDamage", cancellable = true)
	private static void getAttackDamage(ItemStack stack, EntityGroup group, CallbackInfoReturnable<Float> info) {
		if (stack.getItem() instanceof ToolItem ti &&
				ti.getMaterial().getRepairIngredient().test(TaleItems.SilverSet.ITEM.getDefaultStack())) {
			if (group == EntityGroup.UNDEAD) {
				float bonus = 0f;
				if (ti instanceof SwordItem si)
					bonus += si.getAttackDamage();
				else if (ti instanceof MiningToolItem mti)
					bonus += mti.getAttackDamage();

				bonus += info.getReturnValue() * 2;
				info.setReturnValue(bonus);
				return;
			}
		}
	}
}
