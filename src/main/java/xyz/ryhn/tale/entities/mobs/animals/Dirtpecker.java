// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package xyz.ryhn.tale.entities.mobs.animals;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.ryhn.tale.ClientMain;
import xyz.ryhn.tale.Main;

public class Dirtpecker extends ZombieEntity {
	public static EntityType<Dirtpecker> ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
			Main.Key("dirtpecker"),
			EntityType.Builder.create(Dirtpecker::new, SpawnGroup.CREATURE)
					.setDimensions(0.7f, 1.2f)
					.maxTrackingRange(10)
					.build("dirtpecker"));

	public Dirtpecker(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
	}

	public static class Renderer extends MobEntityRenderer<Dirtpecker, Model> {
		public Renderer(Context context) {
			super(context, new Model(context.getPart(LAYER)), 0.75f);
		}

		public static final EntityModelLayer LAYER = new EntityModelLayer(Main.Key("dirtpecker"), "main");

		private static final Identifier TEXTURE = Main.Key("textures/entity/dirtpecker.png");

		@Override
		public Identifier getTexture(Dirtpecker entity) {
			return TEXTURE;
		}
	}

	public static class Model extends EntityModel<Dirtpecker> {
		private final ModelPart body;
		private ModelPart head = null;
		private ModelPart r_leg = null;
		private ModelPart l_leg = null;

		public Model(ModelPart root) {
			this.body = root.getChild("body");
			if(root.hasChild("head"))
			{
				head = body.getChild("head");
				r_leg = body.getChild("r_leg");
				l_leg = body.getChild("l_leg");
			}
			else Main.LOGGER.info("NO HEAD!!!");
		}

		public static TexturedModelData getTexturedModelData() {
			ModelData modelData = new ModelData();
			ModelPartData modelPartData = modelData.getRoot();
			ModelPartData body = modelPartData.addChild("body",
					ModelPartBuilder.create().uv(0, 0)
							.cuboid(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 10.0F, new Dilation(0.0F))
							.uv(14, 16).cuboid(-2.0F, -4.0F, -3.0F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F))
							.uv(22, 0).cuboid(-2.0F, -1.0F, 5.0F, 4.0F, 5.0F, 2.0F, new Dilation(0.0F)),
					ModelTransform.pivot(0.0F, 14.0F, 1.0F));

			ModelPartData head = body.addChild("head",
					ModelPartBuilder.create().uv(30, 27)
							.cuboid(-1.0F, -5.0F, -2.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
							.uv(27, 16).cuboid(-1.0F, -10.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
							.uv(0, 16).cuboid(-2.0F, -9.0F, -4.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F))
							.uv(10, 27).cuboid(-1.0F, -8.0F, -8.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)),
					ModelTransform.pivot(0.0F, 0.0F, -4.0F));

			ModelPartData r_leg = body.addChild("r_leg",
					ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
							.uv(20, 22).cuboid(-2.0F, 7.0F, -2.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)),
					ModelTransform.pivot(-3.0F, 1.0F, 0.0F));

			ModelPartData l_leg = body.addChild("l_leg",
					ModelPartBuilder.create().uv(22, 27)
							.cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
							.uv(0, 26).cuboid(-2.0F, 7.0F, -2.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)),
					ModelTransform.pivot(4.0F, 1.0F, 0.0F));
			return TexturedModelData.of(modelData, 64, 64);
		}

		@Override
		public void setAngles(Dirtpecker entity, float limbSwing, float limbSwingAmount, float ageInTicks,
				float netHeadYaw, float headPitch) {
		}

		@Override
		public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
				float green, float blue, float alpha) {
			body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		}
	}
}