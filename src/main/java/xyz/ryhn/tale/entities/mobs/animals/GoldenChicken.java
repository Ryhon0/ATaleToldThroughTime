package xyz.ryhn.tale.entities.mobs.animals;

import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.ryhn.tale.Main;

public class GoldenChicken extends ChickenEntity {
	public static EntityType<GoldenChicken> ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
			Main.Key("golden_chicken"),
			EntityType.Builder.create(GoldenChicken::new, SpawnGroup.CREATURE)
					.setDimensions(0.4f, 0.7f)
					.maxTrackingRange(10)
					.build("golden_chicken"));

	public GoldenChicken(EntityType<? extends ChickenEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 3.5));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));
	}

	public static DefaultAttributeContainer.Builder createChickenAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	@Override
	public int getXpToDrop() {
		return 0;
	}

	@Override
	public ItemEntity dropItem(ItemConvertible item) {
		if(item == Items.EGG)
		{
			if (this.random.nextFloat() >= 0.9)
				return super.dropItem(Items.GOLD_INGOT);
			else
				return super.dropItem(Items.GOLD_NUGGET);
		}
		else return super.dropItem(item);
	}

	public static class Renderer extends ChickenEntityRenderer {
		public Renderer(Context context) {
			super(context);
		}

		private static final Identifier TEXTURE = Main.Key("textures/entity/golden_chicken.png");

		public Identifier getTexture(ChickenEntity chickenEntity) {
			return TEXTURE;
		}
	}
}
