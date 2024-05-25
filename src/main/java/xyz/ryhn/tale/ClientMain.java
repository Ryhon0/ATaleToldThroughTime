package xyz.ryhn.tale;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import xyz.ryhn.tale.entities.mobs.animals.GoldenChicken;
import xyz.ryhn.tale.entities.mobs.humans.Citizen;
import xyz.ryhn.tale.entities.mobs.humans.CitizenRenderer;
import xyz.ryhn.tale.items.TaleItems;
import xyz.ryhn.tale.items.gear.ScopedCrossbow;
import xyz.ryhn.tale.items.sets.WoodSet;

@Environment(EnvType.CLIENT)
public class ClientMain implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(Citizen.ENTITY_TYPE, CitizenRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CitizenRenderer.CITIZEN_LAYER,
				CitizenRenderer.Model::getTexturedModelData);
		
		EntityRendererRegistry.register(GoldenChicken.ENTITY_TYPE, GoldenChicken.Renderer::new);

		EntityRendererRegistry.register(Citizen.ENTITY_TYPE, CitizenRenderer::new);

		LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER
				.register(p -> !p.getInventory().getArmorStack(1).isOf(TaleItems.GOLD_WINGS));

		{
			ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("pull"),
					(stack, world, entity, seed) -> {
						if (entity == null) {
							return 0.0f;
						}
						if (CrossbowItem.isCharged(stack)) {
							return 0.0f;
						}
						return (float) (stack.getMaxUseTime() - entity.getItemUseTimeLeft())
								/ (float) CrossbowItem.getPullTime(stack);
					});
			ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("pulling"),
					(stack, world, entity, seed) -> entity != null && entity.isUsingItem()
							&& entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0f : 0.0f);
			ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("charged"),
					(stack, world, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0f : 0.0f);
			ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("firework"),
					(stack, world, entity,
							seed) -> CrossbowItem.isCharged(stack)
									&& CrossbowItem.hasProjectile(stack, Items.FIREWORK_ROCKET)
											? 1.0f
											: 0.0f);
		}

		{
			ModelPredicateProviderRegistry.register(TaleItems.GOLDEN_BOW, new Identifier("pull"),
					(stack, world, entity, seed) -> {
						if (entity == null) {
							return 0.0f;
						}
						if (entity.getActiveItem() != stack) {
							return 0.0f;
						}
						return (float) (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
					});
			ModelPredicateProviderRegistry.register(TaleItems.GOLDEN_BOW, new Identifier("pulling"),
					(stack, world, entity,
							seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f
									: 0.0f);
		}

		for (WoodSet set : WoodSet.Sets) {
			BlockRenderLayerMap.INSTANCE.putBlock(set.SAPLING, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(set.POTTED_SAPLING, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(set.Set.DOOR, RenderLayer.getCutout());
			BlockRenderLayerMap.INSTANCE.putBlock(set.Set.TRAPDOOR, RenderLayer.getCutout());
		}

		DimensionEffects.BY_IDENTIFIER.put(
				Main.Key("whitespace"),
				new WhitespaceDimensionEffects());
	}

	public static class WhitespaceDimensionEffects extends DimensionEffects {
		public WhitespaceDimensionEffects() {
			super(Float.NaN, false, SkyType.NONE, true, false);
		}

		@Override
		public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
			return color;
		}

		@Override
		public boolean useThickFog(int camX, int camY) {
			return false;
		}

		@Override
		public float[] getFogColorOverride(float skyAngle, float tickDelta) {
			return null;
		}
	}
}
