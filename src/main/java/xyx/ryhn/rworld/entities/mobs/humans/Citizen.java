package xyx.ryhn.rworld.entities.mobs.humans;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.goal.GoToVillageGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyx.ryhn.rworld.RWorld;

public class Citizen extends VillagerEntity {
	public static EntityType<Citizen> ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
			RWorld.Key("citizen"),
			EntityType.Builder.create(Citizen::new, SpawnGroup.CREATURE)
					.setDimensions(0.75f, 1.8f)
					.build());

	public Citizen(EntityType<? extends VillagerEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 1f));
		this.goalSelector.add(0, new GoToVillageGoal(this, 50));
	}

	public static DefaultAttributeContainer.Builder createAttributes()
	{
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5);
	}

	public static final EntityModelLayer CITIZEN_LAYER = new EntityModelLayer(RWorld.Key("citizen"), "main");

	public static class Renderer extends MobEntityRenderer<Citizen, Model> {
		public Renderer(EntityRendererFactory.Context context) {
			super(context, new Model(context.getPart(CITIZEN_LAYER)), 0.5f);
		}

		@Override
		public Identifier getTexture(Citizen entity) {
			return RWorld.Key("textures/entity/citizen/citizen.png");
		}
	}

	public static class Model extends EntityModel<Citizen> {
		private final ModelPart base;

		public Model(ModelPart modelPart) {
			base = modelPart.getChild("bb_main");
		}

		@Override
		public void setAngles(Citizen entity, float limbAngle, float limbDistance, float animationProgress,
				float headYaw, float headPitch) {
		}

		@Override
		public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red,
				float green, float blue, float alpha) {
			ImmutableList.of(this.base).forEach((modelRenderer) -> {
				modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			});
		}

		public static TexturedModelData getTexturedModelData() {
			ModelData modelData = new ModelData();
			ModelPartData modelPartData = modelData.getRoot();
			ModelPartData bb_main = modelPartData.addChild("bb_main",
					ModelPartBuilder.create().uv(0, 0)
							.cuboid(-4.0F, -34.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F))
							.uv(16, 20).cuboid(-4.0F, -24.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F))
							.uv(0, 22).cuboid(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
							.uv(0, 22).cuboid(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
							.uv(44, 22).cuboid(4.0F, -24.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F))
							.uv(44, 22).cuboid(-8.0F, -24.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F))
							.uv(40, 38).cuboid(4.0F, -16.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
							.uv(40, 38).cuboid(-8.0F, -16.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)),
					ModelTransform.pivot(0.0F, 24.0F, 0.0F));
			return TexturedModelData.of(modelData, 64, 64);
		}
	}
}
