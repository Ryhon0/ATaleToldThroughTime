package xyz.ryhn.tale.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import xyz.ryhn.tale.items.TaleItems;

@Mixin(LivingEntity.class)
public class SilverUndeadProtectionMixin {
	@Inject(at = @At("RETURN"), method = "modifyAppliedDamage", cancellable = true)
	private void modifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> info) {
		if(source.getSource() instanceof LivingEntity le && le.getGroup() == EntityGroup.UNDEAD)
		{
			LivingEntity thise = (LivingEntity)(Object)this;

			float multiplier = 1f;
			for(ItemStack s: thise.getArmorItems())
			{
				if(s.getItem() instanceof ArmorItem ai &&
					ai.getMaterial().getRepairIngredient().test(TaleItems.SilverSet.ITEM.getDefaultStack()))
					multiplier -= 0.1f;
			}
			info.setReturnValue(info.getReturnValue() * multiplier);
			return;
		}
	}
}
