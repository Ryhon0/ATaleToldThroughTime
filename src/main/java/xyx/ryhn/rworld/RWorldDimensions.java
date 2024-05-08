package xyx.ryhn.rworld;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class RWorldDimensions {
	public static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
			RWorld.Key("rworld"));
	public static final RegistryKey<World> WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, DIMENSION_KEY.getValue());
	public static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
			RWorld.Key("rworld_type"));

	public static final RegistryKey<DimensionOptions> WHITESPACE_DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
			RWorld.Key("whitespace"));
	public static final RegistryKey<World> WHITESPACE_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD,
			DIMENSION_KEY.getValue());
	public static final RegistryKey<DimensionType> WHITESPACE_DIMENSION_TYPE_KEY = RegistryKey.of(
			RegistryKeys.DIMENSION_TYPE,
			RWorld.Key("whitespace_type"));
}
