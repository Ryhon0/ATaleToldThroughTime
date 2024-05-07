package xyx.ryhn.rworld.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.poi.PointOfInterestType;
import xyx.ryhn.rworld.RWorld;

public class SpawnBeacon extends Block {
	public static final RegistryKey<PointOfInterestType> POI_KEY = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, RWorld.Key("spawn_beacon"));
	public static int SEARCH_DISTANCE = 256;

	public SpawnBeacon(Settings settings) {
		super(settings);
	}
}
