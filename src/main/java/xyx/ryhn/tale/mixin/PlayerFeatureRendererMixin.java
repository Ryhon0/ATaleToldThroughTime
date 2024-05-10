package xyx.ryhn.tale.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import xyx.ryhn.tale.items.gear.GoldWings;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
	public PlayerFeatureRendererMixin(Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model,
			float shadowRadius) {
		super(ctx, model, shadowRadius);
	} 

	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
		this.addFeature(new GoldWings.WingsFeatureRenderer(this, ctx.getModelLoader()));
	}
}
