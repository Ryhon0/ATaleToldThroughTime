package xyx.ryhn.rworld.mixin;

import net.minecraft.entity.player.PlayerEntity;
import xyx.ryhn.rworld.items.gear.ScopedCrossbow;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class SpyglassMixin {
	@Inject(at = @At("RETURN"), method = "isUsingSpyglass", cancellable = true)
	private void isUsingSpyglass(CallbackInfoReturnable<Boolean> info) {
		Object t = (Object) this;

		if (t instanceof PlayerEntity pe) {
			if(pe.getMainHandStack().isOf(ScopedCrossbow.ITEM) && pe.isSneaking())
			{
				info.setReturnValue(true);
				return;
			}
		}
	}
}