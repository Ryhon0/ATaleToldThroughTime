package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import xyz.ryhn.tale.DataGenerator;

public class SetGenerator {
	public static ArrayList<SetGenerator> Sets = new ArrayList<>();
	public SetGenerator()
	{
		Sets.add(this);
	}

	public void generateBlockModels(DataGenerator.ModelGenerator generator, BlockStateModelGenerator provider)
	{

	}

	public void generateBlockLootTables(FabricBlockLootTableProvider provider)
	{
		
	}

	public void generateItemModels(ItemModelGenerator generator)
	{

	}

	public void generateRecipes(Consumer<RecipeJsonProvider> exporter)
	{

	}

	@FunctionalInterface
	public static interface ITagBuilderProvider<T>
	{
		FabricTagProvider<T>.FabricTagBuilder apply(TagKey<T> tag);
	}

	public void generateItemTags(ITagBuilderProvider<Item> provider, WrapperLookup arg)
	{
	}

	public void generateBlockTags(ITagBuilderProvider<Block> provider, WrapperLookup arg)
	{

	}
}
