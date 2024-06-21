package xyz.ryhn.tale.items.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class TaleMossBlock extends MossBlock {
	public RegistryKey<ConfiguredFeature<?, ?>> growFeature;
	public TaleMossBlock(RegistryKey<ConfiguredFeature<?, ?>> growFeature, Settings settings) {
		super(settings);
		this.growFeature = growFeature;
	}
	
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		world.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) -> {
         return registry.getEntry(growFeature);
      }).ifPresent((reference) -> {
         ((ConfiguredFeature)reference.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up());
      });
	}
}
