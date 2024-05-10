package xyx.ryhn.tale;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.client.BlockStateModelGenerator.TintType;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import xyx.ryhn.tale.items.TaleItems;
import xyx.ryhn.tale.items.gear.MagicClock;
import xyx.ryhn.tale.items.gear.OxidizationWand;
import xyx.ryhn.tale.items.sets.BlockSet;
import xyx.ryhn.tale.items.sets.WoodSet;

public class DataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		Pack pack = generator.createPack();
		pack.addProvider(ModelGenerator::new);
		pack.addProvider(CraftingGenerator::new);
		pack.addProvider(ItemTagGenerator::new);
		pack.addProvider(BlockTagGenerator::new);
		pack.addProvider(BlockLootTableGenerator::new);
	}

	public static class ModelGenerator extends FabricModelProvider {
		public ModelGenerator(FabricDataOutput generator) {
			super(generator);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator generator) {
			generator.registerSimpleCubeAll(TaleItems.DEEPSLATE_QUARTZ_CRYSTAL_ORE);
			generator.registerSimpleCubeAll(TaleItems.QUARTZ_CRYSTAL_BLOCK);
			generator.registerSimpleCubeAll(TaleItems.QUARTZ_CRYSTAL_ORE);

			generator.registerSimpleCubeAll(TaleItems.PETRIFIED_EXPERIENCE);
			generator.registerSimpleCubeAll(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE);
			
			generator.registerSimpleCubeAll(TaleItems.WHITESPACE_BLOCK);

			generator.registerSimpleCubeAll(TaleItems.RED_CRYSTAL_BLOCK);
			generator.registerSimpleCubeAll(TaleItems.SALT_BLOCK);
			
			generator.registerWallPlant(TaleItems.RED_MOSS);
			generator.registerSimpleCubeAll(TaleItems.RED_MOSS_BLOCK);

			generator.registerWallPlant(TaleItems.BLUE_MOSS);
			generator.registerSimpleCubeAll(TaleItems.BLUE_MOSS_BLOCK);

			generator.registerWallPlant(TaleItems.MOSS);


			for (BlockSet set : BlockSet.Sets) 
				generator.registerCubeAllModelTexturePool(set.PARENT).family(set.FAMILY);

			for (WoodSet set : WoodSet.Sets) {
				registerWoodSet(generator, set);
			}
		}

		void registerWoodSet(BlockStateModelGenerator generator, WoodSet set) {
			generator.registerLog(set.LOG).log(set.LOG).wood(set.WOOD);
			generator.registerLog(set.CUT_LOG).log(set.CUT_LOG).wood(set.CUT_WOOD);
			generator.registerLog(set.STRIPPED_LOG).log(set.STRIPPED_LOG).wood(set.STRIPPED_WOOD);
			generator.registerSimpleCubeAll(set.LEAVES);
			generator.registerFlowerPotPlant(set.SAPLING, set.POTTED_SAPLING, TintType.NOT_TINTED);
		}

		@Override
		public void generateItemModels(ItemModelGenerator generator) {
			generator.register(TaleItems.QUARTZ_CRYSTAL, Models.GENERATED);

			generator.register(TaleItems.IRON_MENDING_SET.BOTTLE, Models.GENERATED);
			generator.register(TaleItems.IRON_MENDING_SET.CLOTH, Models.GENERATED);
			generator.register(TaleItems.IRON_MENDING_SET.PLATE, Models.GENERATED);

			generator.register(TaleItems.GOLD_MENDING_SET.BOTTLE, Models.GENERATED);
			generator.register(TaleItems.GOLD_MENDING_SET.CLOTH, Models.GENERATED);
			generator.register(TaleItems.GOLD_MENDING_SET.PLATE, Models.GENERATED);

			generator.register(TaleItems.DIAMOND_MENDING_SET.BOTTLE, Models.GENERATED);
			generator.register(TaleItems.DIAMOND_MENDING_SET.CLOTH, Models.GENERATED);
			generator.register(TaleItems.DIAMOND_MENDING_SET.PLATE, Models.GENERATED);

			generator.register(TaleItems.EXPERIENCE_TRANSFER_ROD, Models.GENERATED);
			generator.register(MagicClock.ITEM, Models.GENERATED);

			generator.register(OxidizationWand.ITEM, Models.GENERATED);
			generator.register(OxidizationWand.ITEM_INVERSE, Models.GENERATED);
		}
	}

	public static class CraftingGenerator extends FabricRecipeProvider {
		public CraftingGenerator(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generate(RecipeExporter exporter) {
			for (BlockSet set : BlockSet.Sets) 
				generateFamily(exporter, set.FAMILY, FeatureSet.of(FeatureFlags.VANILLA));

			for (WoodSet set : WoodSet.Sets)
				registerWoodSet(exporter, set);
		}

		void registerWoodSet(RecipeExporter exporter, WoodSet set) {
			offerPlanksRecipe(exporter, set.PLANK, set.ITEM_LOG_TAG, 4);

			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, set.WOOD, 3)
					.pattern("xx")
					.pattern("xx")
					.input('x', set.LOG)
					.criterion(FabricRecipeProvider.hasItem(set.LOG),
							FabricRecipeProvider.conditionsFromItem(set.LOG))
					.offerTo(exporter, Main.Key(set.name + "_wood"));

			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, set.CUT_WOOD, 3)
					.pattern("xx")
					.pattern("xx")
					.input('x', set.CUT_LOG)
					.criterion(FabricRecipeProvider.hasItem(set.CUT_LOG),
							FabricRecipeProvider.conditionsFromItem(set.CUT_LOG))
					.offerTo(exporter, Main.Key(set.name + "_cut_wood"));

			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, set.STRIPPED_WOOD, 3)
					.pattern("xx")
					.pattern("xx")
					.input('x', set.STRIPPED_LOG)
					.criterion(FabricRecipeProvider.hasItem(set.STRIPPED_LOG),
							FabricRecipeProvider.conditionsFromItem(set.STRIPPED_LOG))
					.offerTo(exporter, Main.Key(set.name + "_stripped_wood"));

			/*
			 * ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, set.SIGN, 3)
			 * .pattern("xxx")
			 * .pattern("xxx")
			 * .pattern(" I ")
			 * .input('x', set.PLANK)
			 * .criterion(FabricRecipeProvider.hasItem(set.PLANK),
			 * FabricRecipeProvider.conditionsFromItem(set.PLANK))
			 * .input('I', Items.STICK)
			 * .criterion(FabricRecipeProvider.hasItem(Items.STICK),
			 * FabricRecipeProvider.conditionsFromItem(Items.STICK))
			 * .offerTo(exporter, Main.Key(set.name + "_signs"));
			 * 
			 * ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS,
			 * set.HANGING_SIGN, 6)
			 * .pattern("I I")
			 * .pattern("xxx")
			 * .pattern("xxx")
			 * .input('x', set.STRIPPED_LOG)
			 * .criterion(FabricRecipeProvider.hasItem(set.STRIPPED_LOG),
			 * FabricRecipeProvider.conditionsFromItem(set.STRIPPED_LOG))
			 * .input('I', Items.CHAIN)
			 * .criterion(FabricRecipeProvider.hasItem(Items.CHAIN),
			 * FabricRecipeProvider.conditionsFromItem(Items.CHAIN))
			 * .offerTo(exporter, Main.Key(set.name + "_hanging_signs"));
			 */
		}
	}

	public static class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
		public ItemTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(WrapperLookup arg) {
			getOrCreateTagBuilder(TaleItems.TAG_ITEM_QUARTZ_CRYSTAL_ORES)
				.add(TaleItems.DEEPSLATE_QUARTZ_CRYSTAL_ORE.asItem())
				.add(TaleItems.QUARTZ_CRYSTAL_ORE.asItem());
			
			getOrCreateTagBuilder(TaleItems.TAG_ITEM_PETRIFIED_EXPERIENCE)
				.add(TaleItems.PETRIFIED_EXPERIENCE.asItem())
				.add(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE.asItem());

			for (WoodSet set : WoodSet.Sets) {
				getOrCreateTagBuilder(ItemTags.SAPLINGS)
						.add(set.SAPLING.asItem());

				getOrCreateTagBuilder(ItemTags.LEAVES)
						.add(set.LEAVES.asItem());

				getOrCreateTagBuilder(ItemTags.PLANKS)
						.add(set.PLANK.asItem());

				getOrCreateTagBuilder(set.ITEM_LOG_TAG)
						.add(set.LOG.asItem())
						.add(set.CUT_LOG.asItem())
						.add(set.STRIPPED_LOG.asItem())
						.add(set.WOOD.asItem())
						.add(set.CUT_WOOD.asItem())
						.add(set.STRIPPED_WOOD.asItem());

				getOrCreateTagBuilder(ItemTags.LOGS)
						.add(set.LOG.asItem())
						.add(set.CUT_LOG.asItem())
						.add(set.STRIPPED_LOG.asItem())
						.add(set.WOOD.asItem())
						.add(set.CUT_WOOD.asItem())
						.add(set.STRIPPED_WOOD.asItem());

				getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
						.add(set.LOG.asItem())
						.add(set.CUT_LOG.asItem())
						.add(set.STRIPPED_LOG.asItem())
						.add(set.WOOD.asItem())
						.add(set.CUT_WOOD.asItem())
						.add(set.STRIPPED_WOOD.asItem());

				getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS)
						.add(set.Set.BUTTON.asItem());

				getOrCreateTagBuilder(ItemTags.WOODEN_DOORS)
						.add(set.Set.DOOR.asItem());

				getOrCreateTagBuilder(ItemTags.WOODEN_FENCES)
						.add(set.Set.FENCE.asItem());

				getOrCreateTagBuilder(ItemTags.FENCE_GATES)
						.add(set.Set.FENCE_GATE.asItem());

				getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
						.add(set.Set.PRESSURE_PLATE.asItem());

				getOrCreateTagBuilder(ItemTags.WOODEN_SLABS)
						.add(set.Set.SLAB.asItem());

				getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS)
						.add(set.Set.STAIRS.asItem());

				getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS)
						.add(set.Set.TRAPDOOR.asItem());

				// getOrCreateTagBuilder(ItemTags.SIGNS)
				// .add(set.HANGING_SIGN.asItem())
				// .add(set.SIGN.asItem());
			}
		}
	}

	public static class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {

		public BlockTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(WrapperLookup arg) {
			getOrCreateTagBuilder(TaleItems.TAG_BLOCK_QUARTZ_CRYSTAL_ORES)
				.add(TaleItems.DEEPSLATE_QUARTZ_CRYSTAL_ORE)
				.add(TaleItems.QUARTZ_CRYSTAL_ORE);

			getOrCreateTagBuilder(TaleItems.TAG_BLOCK_PETRIFIED_EXPERIENCE)
				.add(TaleItems.PETRIFIED_EXPERIENCE)
				.add(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE);

			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(TaleItems.DEEPSLATE_QUARTZ_CRYSTAL_ORE)
				.add(TaleItems.QUARTZ_CRYSTAL_ORE)
				.add(TaleItems.QUARTZ_CRYSTAL_BLOCK)
				.add(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE)
				.add(TaleItems.PETRIFIED_EXPERIENCE);
			
			getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
				.add(TaleItems.DEEPSLATE_QUARTZ_CRYSTAL_ORE)
				.add(TaleItems.QUARTZ_CRYSTAL_ORE)
				.add(TaleItems.QUARTZ_CRYSTAL_BLOCK);

			for(BlockSet set : BlockSet.Sets)
			{
				if(set.WALL != null)
					getOrCreateTagBuilder(BlockTags.WALLS)
						.add(set.WALL);

				if(set.FENCE != null)
					getOrCreateTagBuilder(BlockTags.FENCES)
						.add(set.FENCE);

				if(set.FENCE != null)
					getOrCreateTagBuilder(BlockTags.FENCE_GATES)
						.add(set.FENCE_GATE);	
			}

			for(BlockSet set : new BlockSet[]{
				TaleItems.RED_MOSS_BRICKS_SET, TaleItems.RED_MOSS_COBBLE_SET,
				TaleItems.BLUE_MOSS_BRICKS_SET, TaleItems.BLUE_MOSS_COBBLE_SET
			})
			{
				for(Block b: set.BLOCKS)
					getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
						.add(b);
			}

			for (WoodSet set : WoodSet.Sets) {
				getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
						.add(set.LEAVES);

				for (Block b : set.Set.BLOCKS)
					getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
							.add(b);
				
				getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
					.add(set.LOG)
					.add(set.CUT_LOG)
					.add(set.STRIPPED_LOG)
					.add(set.WOOD)
					.add(set.CUT_WOOD)
					.add(set.STRIPPED_WOOD);

				getOrCreateTagBuilder(BlockTags.SAPLINGS)
						.add(set.SAPLING);

				getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
						.add(set.POTTED_SAPLING);

				getOrCreateTagBuilder(BlockTags.LEAVES)
						.add(set.LEAVES);

				getOrCreateTagBuilder(BlockTags.PLANKS)
						.add(set.PLANK);

				getOrCreateTagBuilder(set.BLOCK_LOG_TAG)
						.add(set.LOG)
						.add(set.CUT_LOG)
						.add(set.STRIPPED_LOG)
						.add(set.WOOD)
						.add(set.CUT_WOOD)
						.add(set.STRIPPED_WOOD);

				getOrCreateTagBuilder(BlockTags.LOGS)
						.add(set.LOG)
						.add(set.CUT_LOG)
						.add(set.STRIPPED_LOG)
						.add(set.WOOD)
						.add(set.CUT_WOOD)
						.add(set.STRIPPED_WOOD);

				getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
						.add(set.LOG)
						.add(set.CUT_LOG)
						.add(set.STRIPPED_LOG)
						.add(set.WOOD)
						.add(set.CUT_WOOD)
						.add(set.STRIPPED_WOOD);

				getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS)
						.add(set.Set.BUTTON);

				getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
						.add(set.Set.DOOR);

				getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
						.add(set.Set.FENCE);

				getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
						.add(set.Set.PRESSURE_PLATE);

				getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
						.add(set.Set.SLAB);

				getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
						.add(set.Set.STAIRS);

				getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
						.add(set.Set.TRAPDOOR);

				// getOrCreateTagBuilder(BlockTags.SIGNS)
				// .add(set.HANGING_SIGN)
				// .add(set.SIGN);
			}
		}
	}

	public static class BlockLootTableGenerator extends FabricBlockLootTableProvider {
		protected BlockLootTableGenerator(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generate() {
			addDrop(TaleItems.WHITESPACE_BLOCK);
			addDropWithSilkTouch(TaleItems.PETRIFIED_EXPERIENCE);
			addDropWithSilkTouch(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE);

			for(BlockSet set : BlockSet.Sets)
			{
				for (Block block : set.BLOCKS) {
					if (block == set.DOOR)
						addDrop(block, doorDrops(block));
					else if (block == set.SLAB)
						addDrop(block, slabDrops(block));
					else
						addDrop(block);
				}
			}

			for (WoodSet set : WoodSet.Sets) {
				addDrop(set.SAPLING);
				addPottedPlantDrops(set.POTTED_SAPLING);
				addDrop(set.LEAVES,
						leavesDrops(set.LEAVES, set.SAPLING, new float[] { 0.05f, 0.0625f, 0.083333336f, 0.1f }));
			}
		}
	}
}
