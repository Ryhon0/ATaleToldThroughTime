package xyx.ryhn.rworld.items.blocks.totem;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;
import xyx.ryhn.rworld.RWorld;

public class RespawnTotem extends Block {
	public static final RegistryKey<PointOfInterestType> POI_KEY =
			RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, RWorld.Key("respawn_totem"));
	public static int SEARCH_DISTANCE = 48;

	public RespawnTotem(Settings settings) {
		super(settings);
	}
}
