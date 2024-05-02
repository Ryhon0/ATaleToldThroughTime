package xyx.ryhn.rworld.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import xyx.ryhn.rworld.items.gear.ScopedCrossbow;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class FOVMixin {
	@Inject(at = @At(value = "HEAD"), method = "getFovMultiplier", cancellable = true)
	private void getFovMultiplier(CallbackInfoReturnable<Float> info) {
		Object t = (Object) this;

		if (t instanceof PlayerEntity pe) {
			if(MinecraftClient.getInstance().options.getPerspective().isFirstPerson() && pe.getMainHandStack().isOf(ScopedCrossbow.ITEM) && pe.isSneaking())
			{
				info.setReturnValue(0.1f);
				return;
			}
		}
	}
}