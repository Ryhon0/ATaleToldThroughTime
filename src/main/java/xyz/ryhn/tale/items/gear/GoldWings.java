package xyz.ryhn.tale.items.gear;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import xyz.ryhn.tale.Main;
import xyz.ryhn.tale.items.TaleItems;

public class GoldWings extends ElytraItem implements FabricElytraItem {
	public GoldWings(Settings settings) {
		super(settings);
	}

	public static class WingsFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
			extends FeatureRenderer<T, M> {
		private static final Identifier SKIN = Main.Key("textures/entity/gold_wings.png");
		private final ElytraEntityModel<T> elytra;

		public WingsFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
			super(context);
			this.elytra = new ElytraEntityModel(loader.getModelPart(EntityModelLayers.ELYTRA));
		}

		public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
				T livingEntity, float f, float g, float h, float j, float k, float l) {
			ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
			
			if (itemStack.isOf(TaleItems.GOLD_WINGS)) {
				matrixStack.push();
				matrixStack.translate(0.0F, 0.0F, 0.125F);
				this.getContextModel().copyStateTo(this.elytra);
				this.elytra.setAngles(livingEntity, f, g, j, k, l);
				VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider,
						RenderLayer.getArmorCutoutNoCull(SKIN), false, itemStack.hasGlint());
				this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStack.pop();
			}
		}
	}
}
