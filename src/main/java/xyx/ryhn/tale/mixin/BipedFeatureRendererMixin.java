package xyx.ryhn.tale.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import xyx.ryhn.tale.items.gear.GoldWings;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedFeatureRendererMixin<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {
	public BipedFeatureRendererMixin(Context context, M entityModel, float f) {
		super(context, entityModel, f);
	}

	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(EntityRendererFactory.Context ctx, M model, float shadowRadius, CallbackInfo ci) {
		this.addFeature(new GoldWings.WingsFeatureRenderer(this, ctx.getModelLoader()));
	}
}
