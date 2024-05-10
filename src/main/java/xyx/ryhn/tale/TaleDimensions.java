package xyx.ryhn.tale;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class TaleDimensions {
	public static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
			Main.Key("overworld"));
	public static final RegistryKey<World> WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, DIMENSION_KEY.getValue());
	public static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
			Main.Key("overworld_type"));

	public static final RegistryKey<DimensionOptions> WHITESPACE_DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
			Main.Key("whitespace"));
	public static final RegistryKey<World> WHITESPACE_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD,
			DIMENSION_KEY.getValue());
	public static final RegistryKey<DimensionType> WHITESPACE_DIMENSION_TYPE_KEY = RegistryKey.of(
			RegistryKeys.DIMENSION_TYPE,
			Main.Key("whitespace_type"));
}
