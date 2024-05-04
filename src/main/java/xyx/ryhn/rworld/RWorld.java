package xyx.ryhn.rworld;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import xyx.ryhn.rworld.entities.mobs.humans.Citizen;
import xyx.ryhn.rworld.items.RWorldItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RWorld implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("rworld");

	@Override
	public void onInitialize() {
		RWorldItems.registerItems();

		CustomPortalBuilder.beginPortal()
				.destDimID(Key("rworld"))
				.frameBlock(Blocks.LAPIS_BLOCK)
				.lightWithItem(Items.CLOCK)
				.onlyLightInOverworld()
				.tintColor(0, 0, 255)
				.registerPortal();

		FabricDefaultAttributeRegistry.register(Citizen.ENTITY_TYPE, Citizen.createMobAttributes());
	}

	public static Identifier Key(String id) {
		return new Identifier("rworld", id);
	}
}