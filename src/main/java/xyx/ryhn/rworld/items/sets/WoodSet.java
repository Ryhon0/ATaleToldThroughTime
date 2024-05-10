package xyx.ryhn.rworld.items.sets;

import java.util.ArrayList;
import java.util.Optional;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import xyx.ryhn.rworld.RWorld;
import xyx.ryhn.rworld.items.RWorldItems;

public class WoodSet {
	public static ArrayList<WoodSet> Sets = new ArrayList<>();

	public String name;
	public BlockSet Set;

	public TagKey<Item> ITEM_LOG_TAG;
	public TagKey<Block> BLOCK_LOG_TAG;

	public RegistryKey<ConfiguredFeature<?, ?>> TREE;
	public SaplingBlock SAPLING;
	public FlowerPotBlock POTTED_SAPLING;

	public PillarBlock LOG;
	public PillarBlock CUT_LOG;
	public PillarBlock STRIPPED_LOG;
	public PillarBlock WOOD;
	public PillarBlock CUT_WOOD;
	public PillarBlock STRIPPED_WOOD;

	public Block PLANK;
	public LeavesBlock LEAVES;

	public WoodSet(String name, MapColor plankColor, MapColor logColor, MapColor leafColor,
			RegistryKey<ConfiguredFeature<?, ?>> tree) {
		this.name = name;
		TREE = tree;

		ITEM_LOG_TAG = TagKey.of(RegistryKeys.ITEM, RWorld.Key(name + "_logs"));
		BLOCK_LOG_TAG = TagKey.of(RegistryKeys.BLOCK, RWorld.Key(name + "_logs"));

		LOG = new PillarBlock(Settings.copy(Blocks.OAK_LOG).mapColor(logColor));
		CUT_LOG = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_LOG).mapColor(logColor));
		STRIPPED_LOG = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_LOG).mapColor(plankColor));

		WOOD = new PillarBlock(Settings.copy(Blocks.OAK_WOOD).mapColor(logColor));
		CUT_WOOD = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(logColor));
		STRIPPED_WOOD = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(plankColor));

		PLANK = new Block(Settings.copy(Blocks.OAK_PLANKS).mapColor(plankColor));
		LEAVES = new LeavesBlock(Settings.copy(Blocks.OAK_LEAVES).mapColor(leafColor));

		RWorldItems.registerBlock(LOG, name + "_log", ItemGroups.BUILDING_BLOCKS);
		RWorldItems.registerBlock(CUT_LOG, "cut_" + name + "_log", ItemGroups.BUILDING_BLOCKS);
		RWorldItems.registerBlock(STRIPPED_LOG, "stripped_" + name + "_log", ItemGroups.BUILDING_BLOCKS);

		RWorldItems.registerBlock(WOOD, name + "_wood", ItemGroups.BUILDING_BLOCKS);
		RWorldItems.registerBlock(CUT_WOOD, "cut_" + name + "_wood", ItemGroups.BUILDING_BLOCKS);
		RWorldItems.registerBlock(STRIPPED_WOOD, "stripped_" + name + "_wood", ItemGroups.BUILDING_BLOCKS);

		RWorldItems.registerBlock(PLANK, name + "_planks", ItemGroups.BUILDING_BLOCKS);
		RWorldItems.registerBlock(LEAVES, name + "_leaves", ItemGroups.BUILDING_BLOCKS);

		SAPLING = new SaplingBlock(
				new SaplingGenerator(name, 0f, Optional.empty(),
						Optional.empty(),
						Optional.of(TREE),
						Optional.empty(),
						Optional.empty(),
						Optional.empty()),
				Settings.copy(Blocks.OAK_SAPLING).mapColor(leafColor));
		POTTED_SAPLING = new FlowerPotBlock(SAPLING, Settings.copy(Blocks.POTTED_OAK_SAPLING).mapColor(leafColor));

		RWorldItems.registerBlock(SAPLING, name + "_sapling", ItemGroups.BUILDING_BLOCKS);
		RWorldItems.registerBlockWithNoItem(POTTED_SAPLING, "potted_" + name + "_sapling");

		Sets.add(this);
		Set = new BlockSet(PLANK, name, true);

		StrippableBlockRegistry.register(LOG, CUT_LOG);
		StrippableBlockRegistry.register(CUT_LOG, STRIPPED_LOG);

		StrippableBlockRegistry.register(WOOD, CUT_WOOD);
		StrippableBlockRegistry.register(CUT_WOOD, STRIPPED_WOOD);

		FlammableBlockRegistry fbr = FlammableBlockRegistry.getDefaultInstance();
		fbr.add(LEAVES, 30, 60);

		fbr.add(PLANK, 5, 20);
		fbr.add(Set.SLAB, 5, 20);
		fbr.add(Set.STAIRS, 5, 20);
		fbr.add(Set.FENCE, 5, 20);
		fbr.add(Set.FENCE_GATE, 5, 20);

		fbr.add(LOG, 5, 5);
		fbr.add(CUT_LOG, 5, 5);
		fbr.add(STRIPPED_LOG, 5, 5);
		fbr.add(WOOD, 5, 5);
		fbr.add(CUT_WOOD, 5, 5);
		fbr.add(STRIPPED_WOOD, 5, 5);
	}
}
