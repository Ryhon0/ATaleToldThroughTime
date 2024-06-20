package xyz.ryhn.tale;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import xyz.ryhn.tale.items.TaleItems;
import xyz.ryhn.tale.items.gear.MagicClock;
import xyz.ryhn.tale.items.gear.OxidizationWand;
import xyz.ryhn.tale.items.sets.SetGenerator;

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
			for (SetGenerator set : SetGenerator.Sets)
				set.generateBlockModels(this, generator);

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

			for(SpawnEggItem egg : TaleItems.SpawnEggs)
				generator.registerParentedItemModel(egg, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
		}

		// This function should not exist, but due to a bug in Fabric,
		// under unknown circumstances, item models are not generated for blocks.
		// This function generates the blockstate, block model and item model
		public void cubeWithItem(BlockStateModelGenerator generator, Block b) {
			generator.registerSimpleCubeAll(b);
			addItem(generator, b);
		}

		public void addItem(BlockStateModelGenerator generator, Block b) {
			generator.registerParentedItemModel(b, Registries.BLOCK.getId(b).withPrefixedPath("block/"));
		}

		@Override
		public void generateItemModels(ItemModelGenerator generator) {
			for (SetGenerator set : SetGenerator.Sets)
				set.generateItemModels(generator);

			generator.register(TaleItems.EXPERIENCE_TRANSFER_ROD, Models.HANDHELD);
			generator.register(MagicClock.ITEM, Models.GENERATED);

			generator.register(OxidizationWand.ITEM, Models.GENERATED);
			generator.register(OxidizationWand.ITEM_INVERSE, Models.GENERATED);

			generator.register(TaleItems.GOLD_WINGS, Models.GENERATED);

			generator.register(TaleItems.UNSEALED_ENCHANTED_BOOK, Models.GENERATED);
			generator.register(TaleItems.OVERFLOWING_UNSEALED_ENCHANTED_BOOK, Models.GENERATED);
			generator.register(TaleItems.AWAKENED_UNSEALED_ENCHANTED_BOOK, Models.GENERATED);
		}
	}

	public static class CraftingGenerator extends FabricRecipeProvider {
		public CraftingGenerator(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generate(Consumer<RecipeJsonProvider> exporter) {
			for (SetGenerator set : SetGenerator.Sets)
				set.generateRecipes(exporter);
		}
	}

	public static class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
		public ItemTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(WrapperLookup arg) {
			for (SetGenerator set : SetGenerator.Sets)
				set.generateItemTags((tag) -> getOrCreateTagBuilder(tag), arg);

			getOrCreateTagBuilder(TaleItems.TAG_ITEM_PETRIFIED_EXPERIENCE)
					.add(TaleItems.PETRIFIED_EXPERIENCE.asItem())
					.add(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE.asItem());

			getOrCreateTagBuilder(ItemTags.BOOKSHELF_BOOKS)
					.add(TaleItems.UNSEALED_ENCHANTED_BOOK)
					.add(TaleItems.OVERFLOWING_UNSEALED_ENCHANTED_BOOK)
					.add(TaleItems.AWAKENED_UNSEALED_ENCHANTED_BOOK);
		}
	}

	public static class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {

		public BlockTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(WrapperLookup arg) {
			for (SetGenerator set : SetGenerator.Sets)
				set.generateBlockTags((tag) -> getOrCreateTagBuilder(tag), arg);

			getOrCreateTagBuilder(TaleItems.TAG_BLOCK_PETRIFIED_EXPERIENCE)
					.add(TaleItems.PETRIFIED_EXPERIENCE)
					.add(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE);

			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
					.add(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE)
					.add(TaleItems.PETRIFIED_EXPERIENCE);
		}
	}

	public static class BlockLootTableGenerator extends FabricBlockLootTableProvider {
		protected BlockLootTableGenerator(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generate() {
			for (SetGenerator set : SetGenerator.Sets)
				set.generateBlockLootTables(this);

			addDrop(TaleItems.WHITESPACE_BLOCK);
			addDropWithSilkTouch(TaleItems.PETRIFIED_EXPERIENCE);
			addDropWithSilkTouch(TaleItems.PETRIFIED_DEEPSLATE_EXPERIENCE);
		}
	}
}
