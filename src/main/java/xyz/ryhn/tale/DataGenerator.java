package xyz.ryhn.tale;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.ImmutableList;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.VanillaRecipeProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.data.client.BlockStateModelGenerator.TintType;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import xyz.ryhn.tale.items.TaleItems;
import xyz.ryhn.tale.items.gear.MagicClock;
import xyz.ryhn.tale.items.gear.OxidizationWand;
import xyz.ryhn.tale.items.sets.BlockSet;
import xyz.ryhn.tale.items.sets.OreSet;
import xyz.ryhn.tale.items.sets.WoodSet;

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
			cubeWithItem(generator, TaleItems.DEEPSLATE_QUARTZ_CRYSTAL_ORE);
			cubeWithItem(generator, TaleItems.QUARTZ_CRYSTAL_BLOCK);
			cubeWithItem(generator, TaleItems.QUARTZ_CRYSTAL_ORE);

			cubeWithItem(generator, TaleItems.PETRIFIED_EXPERIENCE);
			cubeWithItem(generator, TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE);

			cubeWithItem(generator, TaleItems.WHITESPACE_BLOCK);

			cubeWithItem(generator, TaleItems.RED_CRYSTAL_BLOCK);
			cubeWithItem(generator, TaleItems.SALT_BLOCK);

			generator.registerWallPlant(TaleItems.RED_MOSS);
			cubeWithItem(generator, TaleItems.RED_MOSS_BLOCK);

			generator.registerWallPlant(TaleItems.BLUE_MOSS);
			cubeWithItem(generator, TaleItems.BLUE_MOSS_BLOCK);

			generator.registerWallPlant(TaleItems.MOSS);

			for (OreSet set : OreSet.Sets) {
				cubeWithItem(generator, set.BLOCK);
				cubeWithItem(generator, set.ORE_BLOCK);
				cubeWithItem(generator, set.DEEPSLATE_ORE_BLOCK);
				cubeWithItem(generator, set.RAW_BLOCK);
			}

			for (BlockSet set : BlockSet.Sets) {
				Block base = set.FAMILY.getBaseBlock();
				generator.registerCubeAllModelTexturePool(base).family(set.FAMILY);
				addItem(generator, base);
				addItem(generator, set.PRESSURE_PLATE);
				if (set.FENCE_GATE != null)
					addItem(generator, set.FENCE_GATE);
			}

			for (WoodSet set : WoodSet.Sets) {
				generator.registerLog(set.LOG).log(set.LOG).wood(set.WOOD);
				addItem(generator, set.LOG);
				addItem(generator, set.WOOD);
				generator.registerLog(set.CUT_LOG).log(set.CUT_LOG).wood(set.CUT_WOOD);
				addItem(generator, set.CUT_LOG);
				addItem(generator, set.CUT_WOOD);
				generator.registerLog(set.STRIPPED_LOG).log(set.STRIPPED_LOG).wood(set.STRIPPED_WOOD);
				addItem(generator, set.STRIPPED_LOG);
				addItem(generator, set.STRIPPED_WOOD);
				cubeWithItem(generator, set.LEAVES);
				generator.registerFlowerPotPlant(set.SAPLING, set.POTTED_SAPLING, TintType.NOT_TINTED);
			}
		}

		// This function should not exist, but due to a bug in Fabric,
		// under unknown circumstances, item models are not generated for blocks.
		// This function generates the blockstate, block model and item model
		void cubeWithItem(BlockStateModelGenerator generator, Block b) {
			generator.registerSimpleCubeAll(b);
			addItem(generator, b);
		}

		void addItem(BlockStateModelGenerator generator, Block b) {
			generator.registerParentedItemModel(b, Registries.BLOCK.getId(b).withPrefixedPath("block/"));
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

			generator.register(TaleItems.EXPERIENCE_TRANSFER_ROD, Models.HANDHELD);
			generator.register(MagicClock.ITEM, Models.GENERATED);

			generator.register(OxidizationWand.ITEM, Models.GENERATED);
			generator.register(OxidizationWand.ITEM_INVERSE, Models.GENERATED);

			generator.register(TaleItems.GOLD_WINGS, Models.GENERATED);

			for (OreSet set : OreSet.Sets) {
				generator.register(set.RAW, Models.GENERATED);
				generator.register(set.INGOT, Models.GENERATED);
				generator.register(set.NUGGET, Models.GENERATED);

				if (set.SWORD != null)
					generator.register(set.SWORD, Models.HANDHELD);
				if (set.SHOVEL != null)
					generator.register(set.SHOVEL, Models.HANDHELD);
				if (set.PICKAXE != null)
					generator.register(set.PICKAXE, Models.HANDHELD);
				if (set.AXE != null)
					generator.register(set.AXE, Models.HANDHELD);
				if (set.HOE != null)
					generator.register(set.HOE, Models.HANDHELD);

				if (set.HELMET != null)
					generator.registerArmor(set.HELMET);
				if (set.CHESTPLATE != null)
					generator.registerArmor(set.CHESTPLATE);
				if (set.LEGGINGS != null)
					generator.registerArmor(set.LEGGINGS);
				if (set.BOOTS != null)
					generator.registerArmor(set.BOOTS);
			}
		}
	}

	public static class CraftingGenerator extends FabricRecipeProvider {
		public CraftingGenerator(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generate(RecipeExporter exporter) {
			for (BlockSet set : BlockSet.Sets) {
				generateFamily(exporter, set.FAMILY, FeatureSet.of(FeatureFlags.VANILLA));
				if (set.WOOD_TYPE == null) {
					offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, set.PRESSURE_PLATE, set.PARENT);
					offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, set.WALL, set.PARENT);
					offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, set.STAIRS, set.PARENT);
					offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, set.SLAB, set.PARENT, 2);
				}
			}

			for (WoodSet set : WoodSet.Sets)
				registerWoodSet(exporter, set);

			for (OreSet set : OreSet.Sets) {
				offerSmelting(exporter, ImmutableList.of(set.ORE_BLOCK, set.DEEPSLATE_ORE_BLOCK),
						RecipeCategory.MISC, set.INGOT, 0.7f, 200, set.name);
				offerBlasting(exporter, ImmutableList.of(set.ORE_BLOCK, set.DEEPSLATE_ORE_BLOCK),
						RecipeCategory.MISC, set.INGOT, 0.7f, 100, set.name);

				offerSmelting(exporter, ImmutableList.of(set.RAW),
						RecipeCategory.MISC, set.INGOT, 0.7f, 200, set.name);
				offerBlasting(exporter, ImmutableList.of(set.RAW),
						RecipeCategory.MISC, set.INGOT, 0.7f, 100, set.name);

				offerShapelessRecipe(exporter, set.NUGGET, set.INGOT, set.name + "_nuggets", 9);
				offerCompactingRecipe(exporter, RecipeCategory.MISC, set.INGOT, set.NUGGET);
				offerCompactingRecipe(exporter, RecipeCategory.MISC, set.BLOCK, set.INGOT);
				offerCompactingRecipe(exporter, RecipeCategory.MISC, set.RAW_BLOCK, set.RAW);

				if (set.SWORD != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, set.SWORD)
							.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), set.INGOT)
							.pattern("X").pattern("X").pattern("#")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.SHOVEL != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, set.SHOVEL)
							.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), set.INGOT)
							.pattern("X").pattern("#").pattern("#")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.PICKAXE != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, set.PICKAXE)
							.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), set.INGOT)
							.pattern("XXX").pattern(" # ").pattern(" # ")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.AXE != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, set.AXE)
							.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), set.INGOT)
							.pattern("XX").pattern("X#").pattern(" #")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.HOE != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, set.HOE)
							.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), set.INGOT)
							.pattern("XX").pattern(" #").pattern(" #")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.HELMET != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, set.HELMET)
							.input(Character.valueOf('X'), set.INGOT).pattern("XXX").pattern("X X")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.CHESTPLATE != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, set.CHESTPLATE)
							.input(Character.valueOf('X'), set.INGOT).pattern("X X").pattern("XXX")
							.pattern("XXX")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.LEGGINGS != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, set.LEGGINGS)
							.input(Character.valueOf('X'), set.INGOT).pattern("XXX").pattern("X X")
							.pattern("X X")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}
				if (set.BOOTS != null) {
					ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, set.BOOTS)
							.input(Character.valueOf('X'), set.INGOT).pattern("X X").pattern("X X")
							.criterion(FabricRecipeProvider.hasItem(set.INGOT),
									VanillaRecipeProvider.conditionsFromItem(set.INGOT))
							.offerTo(exporter);
				}

				if (!set.tools.isEmpty()) {
					List<ItemConvertible> items = set.tools.stream().map(t -> (ItemConvertible) t).toList();
					offerSmelting(exporter,
							items,
							RecipeCategory.MISC, set.NUGGET, 0.1f, 200, set.name + "_nugget_from_tools");
					offerBlasting(exporter,
							items,
							RecipeCategory.MISC, set.NUGGET, 0.1f, 100, set.name + "_nugget_from_tools");
				}

				if (!set.armorItems.isEmpty()) {
					List<ItemConvertible> items = set.armorItems.stream().map(t -> (ItemConvertible) t).toList();

					offerSmelting(exporter,
							items,
							RecipeCategory.MISC, set.NUGGET, 0.1f, 200, set.name + "_nugget_from_armor");
					offerBlasting(exporter,
							items,
							RecipeCategory.MISC, set.NUGGET, 0.1f, 100, set.name + "_nugget_from_armor");
				}
			}
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

				getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
					.addTag(set.ITEM_LOG_TAG);

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

			for (OreSet set : OreSet.Sets) {
				getOrCreateTagBuilder(ConventionalItemTags.ORES)
						.addTag(set.ITEM_ORE_TAG);
				getOrCreateTagBuilder(ConventionalItemTags.NUGGETS)
						.add(set.NUGGET);
				getOrCreateTagBuilder(ConventionalItemTags.INGOTS)
						.add(set.NUGGET);
				getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
						.add(set.INGOT);
				getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS)
						.add(set.INGOT);

				getOrCreateTagBuilder(set.ITEM_ORE_TAG)
						.add(set.ORE_BLOCK.asItem())
						.add(set.DEEPSLATE_ORE_BLOCK.asItem());

				if (set.SWORD != null) {
					getOrCreateTagBuilder(ItemTags.SWORDS).add(set.SWORD);
					getOrCreateTagBuilder(ItemTags.SHOVELS).add(set.SHOVEL);
					getOrCreateTagBuilder(ItemTags.PICKAXES).add(set.PICKAXE);
					getOrCreateTagBuilder(ItemTags.CLUSTER_MAX_HARVESTABLES).add(set.PICKAXE);
					getOrCreateTagBuilder(ItemTags.AXES).add(set.AXE);
					getOrCreateTagBuilder(ItemTags.HOES).add(set.HOE);

					getOrCreateTagBuilder(ItemTags.TOOLS)
							.add(set.SWORD)
							.add(set.SHOVEL)
							.add(set.PICKAXE)
							.add(set.AXE)
							.add(set.HOE);

				}

				if (set.HELMET != null)
					getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(set.HELMET);
				if (set.CHESTPLATE != null)
					getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(set.CHESTPLATE);
				if (set.LEGGINGS != null)
					getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(set.LEGGINGS);
				if (set.BOOTS != null)
					getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(set.BOOTS);
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

			for (OreSet set : OreSet.Sets) {
				getOrCreateTagBuilder(ConventionalBlockTags.ORES)
						.addTag(set.BLOCK_ORE_TAG);

				getOrCreateTagBuilder(set.BLOCK_ORE_TAG)
						.add(set.ORE_BLOCK)
						.add(set.DEEPSLATE_ORE_BLOCK);

				getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
						.add(set.BLOCK)
						.add(set.ORE_BLOCK)
						.add(set.DEEPSLATE_ORE_BLOCK)
						.add(set.RAW_BLOCK);

				getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
						.add(set.BLOCK);

				TagKey<Block> tag = null;
				switch (set.level) {
					case MiningLevels.STONE:
						tag = BlockTags.NEEDS_STONE_TOOL;
						break;
					case MiningLevels.IRON:
						tag = BlockTags.NEEDS_IRON_TOOL;
						break;
					case MiningLevels.DIAMOND:
						tag = BlockTags.NEEDS_DIAMOND_TOOL;
						break;
				}
				if (tag != null)
					getOrCreateTagBuilder(tag)
							.add(set.BLOCK)
							.add(set.ORE_BLOCK)
							.add(set.DEEPSLATE_ORE_BLOCK)
							.add(set.RAW_BLOCK);
			}

			for (BlockSet set : BlockSet.Sets) {
				if (set.WALL != null)
					getOrCreateTagBuilder(BlockTags.WALLS)
							.add(set.WALL);

				if (set.FENCE != null)
					getOrCreateTagBuilder(BlockTags.FENCES)
							.add(set.FENCE);

				if (set.FENCE != null)
					getOrCreateTagBuilder(BlockTags.FENCE_GATES)
							.add(set.FENCE_GATE);
			}

			for (BlockSet set : new BlockSet[] {
					TaleItems.RED_MOSS_BRICKS_SET, TaleItems.RED_MOSS_COBBLE_SET,
					TaleItems.BLUE_MOSS_BRICKS_SET, TaleItems.BLUE_MOSS_COBBLE_SET
			}) {
				for (Block b : set.BLOCKS)
					getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
							.add(b);
			}

			for (WoodSet set : WoodSet.Sets) {
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

				getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
						.addTag(set.BLOCK_LOG_TAG);

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

			for (BlockSet set : BlockSet.Sets) {
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
				addDrop(set.LOG);
				addDrop(set.CUT_LOG);
				addDrop(set.STRIPPED_LOG);
				addDrop(set.WOOD);
				addDrop(set.CUT_WOOD);
				addDrop(set.STRIPPED_WOOD);
				addDrop(set.SAPLING);
				addPottedPlantDrops(set.POTTED_SAPLING);
				addDrop(set.LEAVES,
						leavesDrops(set.LEAVES, set.SAPLING, new float[] { 0.05f, 0.0625f, 0.083333336f, 0.1f }));
			}

			for (OreSet set : OreSet.Sets) {
				addDrop(set.BLOCK);
				addDrop(set.RAW_BLOCK);
				if (set.affectedByFortune) {
					addDrop(set.ORE_BLOCK, (Block b) -> oreDrops(set.ORE_BLOCK, set.RAW));
					addDrop(set.DEEPSLATE_ORE_BLOCK, (Block b) -> oreDrops(set.DEEPSLATE_ORE_BLOCK, set.RAW));
				} else {
					addDrop(set.ORE_BLOCK, (Block b) -> dropsWithSilkTouch(set.ORE_BLOCK,
							this.applyExplosionDecay(set.ORE_BLOCK, ItemEntry.builder(set.RAW))));
					addDrop(set.DEEPSLATE_ORE_BLOCK, (Block b) -> dropsWithSilkTouch(set.DEEPSLATE_ORE_BLOCK,
							this.applyExplosionDecay(set.DEEPSLATE_ORE_BLOCK, ItemEntry.builder(set.RAW))));
				}
			}
		}
	}
}
