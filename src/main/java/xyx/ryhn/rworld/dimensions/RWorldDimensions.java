package xyx.ryhn.rworld.dimensions;

import java.util.OptionalLong;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionType.MonsterSettings;
import net.minecraft.world.dimension.DimensionTypes;
import xyx.ryhn.rworld.RWorld;

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
