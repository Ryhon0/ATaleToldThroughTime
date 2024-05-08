package xyx.ryhn.rworld.entities.mobs.humans;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.goal.GoToVillageGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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
}
