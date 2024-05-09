package xyx.ryhn.rworld.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestStorage.OccupationStatus;
import xyx.ryhn.rworld.items.blocks.SpawnBeacon;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Pair;

@Mixin(MobEntity.class)
public class SpawnBeaconMixin {
	@Inject(at = @At("TAIL"), method = "canMobSpawn", cancellable = true)
	private static void canMobSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason,
			BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
		if (world.isClient())
			return;

		if (cir.getReturnValue()) {
			if (spawnReason == SpawnReason.NATURAL)
			{
				ServerWorld sw = (ServerWorld) world;
				Optional<Pair<RegistryEntry<PointOfInterestType>, BlockPos>> poi = sw.getPointOfInterestStorage()
						.getNearestTypeAndPosition(p -> p.matchesKey(SpawnBeacon.POI_KEY), pos,
							SpawnBeacon.SEARCH_DISTANCE, OccupationStatus.ANY);
				if (!poi.isEmpty()) {
					cir.setReturnValue(false);
					return;
				}
			}
		}
	}
}