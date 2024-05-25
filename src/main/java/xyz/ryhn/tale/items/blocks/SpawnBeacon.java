package xyz.ryhn.tale.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;
import xyz.ryhn.tale.Main;

public class SpawnBeacon extends Block {
	public static final RegistryKey<PointOfInterestType> POI_KEY = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Main.Key("spawn_beacon"));
	public static int SEARCH_DISTANCE = 256;

	public SpawnBeacon(Settings settings) {
		super(settings);
	}
}
