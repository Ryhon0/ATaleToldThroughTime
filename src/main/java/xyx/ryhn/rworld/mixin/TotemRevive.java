package xyx.ryhn.rworld.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage.OccupationStatus;
import net.minecraft.world.poi.PointOfInterestType;
import xyx.ryhn.rworld.items.blocks.SpawnBeacon;
import xyx.ryhn.rworld.items.blocks.totem.RespawnTotem;

@Mixin(LivingEntity.class)
public class TotemRevive {
	@Inject(at = @At("TAIL"), method = "tryUseTotem", cancellable = true)
	void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity le = (LivingEntity) (Object) this;
		if (le.getWorld().isClient())
			return;

		if (!cir.getReturnValue() && le instanceof PlayerEntity pe) {
			ServerWorld sw = (ServerWorld) le.getWorld();

			Optional<Pair<RegistryEntry<PointOfInterestType>, BlockPos>> poi = sw.getPointOfInterestStorage()
					.getNearestTypeAndPosition(p -> p.matchesKey(RespawnTotem.POI_KEY), le.getBlockPos(),
							RespawnTotem.SEARCH_DISTANCE, OccupationStatus.ANY);

			if (!poi.isEmpty()) {
				pe.setHealth(20);
				pe.clearStatusEffects();
				pe.setFireTicks(0);
				pe.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10, 1));

				pe.getWorld().sendEntityStatus(pe, (byte) 30); // Shield break sound
				pe.getWorld().sendEntityStatus(pe, (byte) 60); // Smoke particles

				Vec3d pos = poi.get().getSecond().toCenterPos().add(0, 1, 0);
				pe.teleport(pos.x, pos.y, pos.z);
				pe.setVelocity(0, 0, 0);
				pe.setVelocityClient(0, 0, 0);

				pe.getWorld().sendEntityStatus(pe, (byte) 30); // Shield break sound
				pe.getWorld().sendEntityStatus(pe, (byte) 60); // Smoke particles

				cir.setReturnValue(true);
				return;
			}
		}
	}
}
