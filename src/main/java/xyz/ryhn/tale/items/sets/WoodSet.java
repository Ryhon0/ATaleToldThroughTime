package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import static net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider.*;
import static net.minecraft.data.server.recipe.RecipeProvider.offerPlanksRecipe;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateModelGenerator.TintType;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import xyz.ryhn.tale.Main;
import xyz.ryhn.tale.DataGenerator;
import xyz.ryhn.tale.items.TaleItems;

public class WoodSet extends SetGenerator {
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
		super();

		this.name = name;
		TREE = tree;

		ITEM_LOG_TAG = TagKey.of(RegistryKeys.ITEM, Main.Key(name + "_logs"));
		BLOCK_LOG_TAG = TagKey.of(RegistryKeys.BLOCK, Main.Key(name + "_logs"));

		LOG = new PillarBlock(Settings.copy(Blocks.OAK_LOG).mapColor(logColor));
		CUT_LOG = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_LOG).mapColor(logColor));
		STRIPPED_LOG = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_LOG).mapColor(plankColor));

		WOOD = new PillarBlock(Settings.copy(Blocks.OAK_WOOD).mapColor(logColor));
		CUT_WOOD = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(logColor));
		STRIPPED_WOOD = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(plankColor));

		PLANK = new Block(Settings.copy(Blocks.OAK_PLANKS).mapColor(plankColor));
		LEAVES = new LeavesBlock(Settings.copy(Blocks.OAK_LEAVES).mapColor(leafColor));

		TaleItems.registerBlock(LOG, name + "_log", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(CUT_LOG, "cut_" + name + "_log", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(STRIPPED_LOG, "stripped_" + name + "_log", ItemGroups.BUILDING_BLOCKS);

		TaleItems.registerBlock(WOOD, name + "_wood", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(CUT_WOOD, "cut_" + name + "_wood", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(STRIPPED_WOOD, "stripped_" + name + "_wood", ItemGroups.BUILDING_BLOCKS);

		TaleItems.registerBlock(PLANK, name + "_planks", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(LEAVES, name + "_leaves", ItemGroups.BUILDING_BLOCKS);

		SAPLING = new SaplingBlock(new SaplingGenerator() {
			@Override
			protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random arg0, boolean arg1) {
				return TREE;
			}
		}, Settings.copy(Blocks.OAK_SAPLING).mapColor(leafColor));
		POTTED_SAPLING = new FlowerPotBlock(SAPLING, Settings.copy(Blocks.POTTED_OAK_SAPLING).mapColor(leafColor));

		TaleItems.registerBlock(SAPLING, name + "_sapling", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlockWithNoItem(POTTED_SAPLING, "potted_" + name + "_sapling");

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

		BlockSetAPI.addBlockTypeFinder(WoodType.class, WoodType.Finder
				.simple("ataletoldthroughtime", name, name + "_planks", name + "_log"));
	}

	@Override
	public void generateBlockModels(DataGenerator.ModelGenerator generator, BlockStateModelGenerator provider) {
		provider.registerLog(LOG).log(LOG).wood(WOOD);
		generator.addItem(provider, LOG);
		generator.addItem(provider, WOOD);
		provider.registerLog(CUT_LOG).log(CUT_LOG).wood(CUT_WOOD);
		generator.addItem(provider, CUT_LOG);
		generator.addItem(provider, CUT_WOOD);
		provider.registerLog(STRIPPED_LOG).log(STRIPPED_LOG).wood(STRIPPED_WOOD);
		generator.addItem(provider, STRIPPED_LOG);
		generator.addItem(provider, STRIPPED_WOOD);
		generator.cubeWithItem(provider, LEAVES);
		provider.registerFlowerPotPlant(SAPLING, POTTED_SAPLING, TintType.NOT_TINTED);
	}

	@Override
	public void generateBlockTags(ITagBuilderProvider<Block> provider, WrapperLookup arg) {
		provider.apply(BlockTags.SAPLINGS)
				.add(SAPLING);

		provider.apply(BlockTags.FLOWER_POTS)
				.add(POTTED_SAPLING);

		provider.apply(BlockTags.LEAVES)
				.add(LEAVES);

		provider.apply(BlockTags.PLANKS)
				.add(PLANK);

		provider.apply(BLOCK_LOG_TAG)
				.add(LOG)
				.add(CUT_LOG)
				.add(STRIPPED_LOG)
				.add(WOOD)
				.add(CUT_WOOD)
				.add(STRIPPED_WOOD);

		provider.apply(BlockTags.LOGS_THAT_BURN)
				.addTag(BLOCK_LOG_TAG);

		provider.apply(BlockTags.WOODEN_BUTTONS)
				.add(Set.BUTTON);

		provider.apply(BlockTags.WOODEN_DOORS)
				.add(Set.DOOR);

		provider.apply(BlockTags.WOODEN_FENCES)
				.add(Set.FENCE);

		provider.apply(BlockTags.WOODEN_PRESSURE_PLATES)
				.add(Set.PRESSURE_PLATE);

		provider.apply(BlockTags.WOODEN_SLABS)
				.add(Set.SLAB);

		provider.apply(BlockTags.WOODEN_STAIRS)
				.add(Set.STAIRS);

		provider.apply(BlockTags.WOODEN_TRAPDOORS)
				.add(Set.TRAPDOOR);

		// getOrCreateTagBuilder(BlockTags.SIGNS)
		// .add(HANGING_SIGN)
		// .add(SIGN);
	}

	@Override
	public void generateBlockLootTables(FabricBlockLootTableProvider provider) {
		provider.addDrop(LOG);
		provider.addDrop(CUT_LOG);
		provider.addDrop(STRIPPED_LOG);
		provider.addDrop(WOOD);
		provider.addDrop(CUT_WOOD);
		provider.addDrop(STRIPPED_WOOD);
		provider.addDrop(SAPLING);
		provider.addPottedPlantDrops(POTTED_SAPLING);
		provider.addDrop(LEAVES,
				provider.leavesDrops(LEAVES, SAPLING, new float[] { 0.05f, 0.0625f, 0.083333336f, 0.1f }));
	}

	@Override
	public void generateItemTags(ITagBuilderProvider<Item> provider, WrapperLookup arg) {
		provider.apply(ItemTags.SAPLINGS)
				.add(SAPLING.asItem());

		provider.apply(ItemTags.LEAVES)
				.add(LEAVES.asItem());

		provider.apply(ItemTags.PLANKS)
				.add(PLANK.asItem());

		provider.apply(ITEM_LOG_TAG)
				.add(LOG.asItem())
				.add(CUT_LOG.asItem())
				.add(STRIPPED_LOG.asItem())
				.add(WOOD.asItem())
				.add(CUT_WOOD.asItem())
				.add(STRIPPED_WOOD.asItem());

		provider.apply(ItemTags.LOGS_THAT_BURN)
				.addTag(ITEM_LOG_TAG);

		provider.apply(ItemTags.WOODEN_BUTTONS)
				.add(Set.BUTTON.asItem());

		provider.apply(ItemTags.WOODEN_DOORS)
				.add(Set.DOOR.asItem());

		provider.apply(ItemTags.WOODEN_FENCES)
				.add(Set.FENCE.asItem());

		provider.apply(ItemTags.FENCE_GATES)
				.add(Set.FENCE_GATE.asItem());

		provider.apply(ItemTags.WOODEN_PRESSURE_PLATES)
				.add(Set.PRESSURE_PLATE.asItem());

		provider.apply(ItemTags.WOODEN_SLABS)
				.add(Set.SLAB.asItem());

		provider.apply(ItemTags.WOODEN_STAIRS)
				.add(Set.STAIRS.asItem());

		provider.apply(ItemTags.WOODEN_TRAPDOORS)
				.add(Set.TRAPDOOR.asItem());

		// provider.apply(ItemTags.SIGNS)
		// .add(HANGING_SIGN.asItem())
		// .add(SIGN.asItem());
	}

	@Override
	public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		offerPlanksRecipe(exporter, PLANK, ITEM_LOG_TAG, 4);
		ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, WOOD, 3)
				.pattern("xx")
				.pattern("xx")
				.input('x', LOG)
				.criterion(FabricRecipeProvider.hasItem(LOG),
						FabricRecipeProvider.conditionsFromItem(LOG))
				.offerTo(exporter, Main.Key(name + "_wood"));

		ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, CUT_WOOD, 3)
				.pattern("xx")
				.pattern("xx")
				.input('x', CUT_LOG)
				.criterion(FabricRecipeProvider.hasItem(CUT_LOG),
						FabricRecipeProvider.conditionsFromItem(CUT_LOG))
				.offerTo(exporter, Main.Key(name + "_cut_wood"));

		ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, STRIPPED_WOOD, 3)
				.pattern("xx")
				.pattern("xx")
				.input('x', STRIPPED_LOG)
				.criterion(FabricRecipeProvider.hasItem(STRIPPED_LOG),
						FabricRecipeProvider.conditionsFromItem(STRIPPED_LOG))
				.offerTo(exporter, Main.Key(name + "_stripped_wood"));

		/*
		 * ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, SIGN, 3)
		 * .pattern("xxx")
		 * .pattern("xxx")
		 * .pattern(" I ")
		 * .input('x', PLANK)
		 * .criterion(FabricRecipeProvider.hasItem(PLANK),
		 * FabricRecipeProvider.conditionsFromItem(PLANK))
		 * .input('I', Items.STICK)
		 * .criterion(FabricRecipeProvider.hasItem(Items.STICK),
		 * FabricRecipeProvider.conditionsFromItem(Items.STICK))
		 * .offerTo(exporter, Main.Key(name + "_signs"));
		 * 
		 * ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS,
		 * HANGING_SIGN, 6)
		 * .pattern("I I")
		 * .pattern("xxx")
		 * .pattern("xxx")
		 * .input('x', STRIPPED_LOG)
		 * .criterion(FabricRecipeProvider.hasItem(STRIPPED_LOG),
		 * FabricRecipeProvider.conditionsFromItem(STRIPPED_LOG))
		 * .input('I', Items.CHAIN)
		 * .criterion(FabricRecipeProvider.hasItem(Items.CHAIN),
		 * FabricRecipeProvider.conditionsFromItem(Items.CHAIN))
		 * .offerTo(exporter, Main.Key(name + "_hanging_signs"));
		 */
	}
}
