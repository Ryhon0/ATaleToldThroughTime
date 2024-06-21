package xyz.ryhn.tale.items.sets;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.MossBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.VanillaRecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import xyz.ryhn.tale.DataGenerator;
import xyz.ryhn.tale.DataGenerator.ModelGenerator;
import xyz.ryhn.tale.items.TaleItems;
import xyz.ryhn.tale.items.blocks.MultifaceBlock;

public class MossSet extends SetGenerator {
	public Block BLOCK;
	public String name;

	public MultifaceBlock MOSS;
	public Block COBBLE;
	public BlockSet COBBLE_SET;
	public Block BRICKS;
	public BlockSet BRICK_SET;

	public MossSet(Block block, boolean noSets) {
		super();

		BLOCK = block;

		String name = Registries.BLOCK.getId(block).getPath();
		if (name.endsWith("_block"))
			name = name.substring(0, name.length() - "_block".length());
		this.name = name;

		MOSS = TaleItems.registerBlock(new MultifaceBlock(AbstractBlock.Settings.copy(block)
				.pistonBehavior(PistonBehavior.DESTROY)
				.nonOpaque()),
				name, ItemGroups.BUILDING_BLOCKS);

		if (!noSets) {
			COBBLE = TaleItems.registerBlock(
					new Block(Block.Settings.copy(Blocks.MOSSY_COBBLESTONE)), name + "y_cobblestone",
					ItemGroups.BUILDING_BLOCKS);
			COBBLE_SET = new BlockSet(COBBLE, name + "y_cobblestone",
					false);

			BRICKS = TaleItems.registerBlock(
					new Block(Block.Settings.copy(Blocks.MOSSY_STONE_BRICKS)), name + "y_stone_bricks",
					ItemGroups.BUILDING_BLOCKS);
			BRICK_SET = new BlockSet(BRICKS, name + "y_brick", false);
		}
	}

	public MossSet(MossBlock block) {
		this(block, false);
	}

	@Override
	public void generateBlockModels(DataGenerator.ModelGenerator generator, BlockStateModelGenerator provider) {
		provider.registerWallPlant(MOSS);

		if (COBBLE == null)
			return;

		generator.cubeWithItem(provider, BLOCK);
	}

	@Override
	public void generateBlockLootTables(FabricBlockLootTableProvider provider) {
		provider.addDrop(MOSS);

		if (COBBLE == null)
			return;

		provider.addDrop(BLOCK);
		provider.addDrop(COBBLE);
		provider.addDrop(BRICKS);
	}

	@Override
	public void generateBlockTags(ITagBuilderProvider<Block> provider, WrapperLookup arg) {
		provider.apply(BlockTags.HOE_MINEABLE)
				.add(MOSS);

		if (COBBLE == null)
			return;

		provider.apply(BlockTags.HOE_MINEABLE)
				.add(BLOCK);

		provider.apply(BlockTags.DIRT)
				.add(BLOCK);

		provider.apply(BlockTags.STONE_BRICKS)
				.add(BRICKS);

		provider.apply(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
				.add(BLOCK)
				.add(COBBLE)
				.add(BRICKS);

		provider.apply(BlockTags.SNIFFER_DIGGABLE_BLOCK)
				.add(BLOCK);

		provider.apply(BlockTags.SNIFFER_EGG_HATCH_BOOST)
				.add(BLOCK);
	}

	@Override
	public void generateItemTags(ITagBuilderProvider<Item> provider, WrapperLookup arg) {
		if (COBBLE == null)
			return;
		provider.apply(ItemTags.DIRT)
				.add(BLOCK.asItem());

		provider.apply(ItemTags.STONE_BRICKS)
				.add(BRICKS.asItem());
	}

	@Override
	public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MOSS, 8)
				.input(BLOCK)
				.criterion(FabricRecipeProvider.hasItem(BLOCK),
						VanillaRecipeProvider.conditionsFromItem(BLOCK))
				.offerTo(exporter);

		if (COBBLE == null)
			return;

		ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, COBBLE)
				.input(BLOCK)
				.input(Items.COBBLESTONE)
				.criterion(FabricRecipeProvider.hasItem(BLOCK),
						VanillaRecipeProvider.conditionsFromItem(BLOCK))
				.offerTo(exporter);
		ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BRICKS)
				.input(BLOCK)
				.input(Items.STONE_BRICKS)
				.criterion(FabricRecipeProvider.hasItem(BLOCK),
						VanillaRecipeProvider.conditionsFromItem(BLOCK))
				.offerTo(exporter);
	}
}
