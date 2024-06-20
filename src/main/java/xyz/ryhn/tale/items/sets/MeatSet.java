package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import xyz.ryhn.tale.DataGenerator;
import xyz.ryhn.tale.DataGenerator.ModelGenerator;
import xyz.ryhn.tale.items.TaleItems;

import static net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider.*;
import static net.minecraft.data.server.recipe.RecipeProvider.conditionsFromItem;
import static net.minecraft.data.server.recipe.RecipeProvider.hasItem;
import static net.minecraft.data.server.recipe.RecipeProvider.offerFoodCookingRecipe;
import static net.minecraft.data.server.recipe.RecipeProvider.offerPlanksRecipe;
import static net.minecraft.data.server.recipe.RecipeProvider.offerReversibleCompactingRecipes;
import static net.minecraft.data.server.recipe.RecipeProvider.offerSmelting;

public class MeatSet extends SetGenerator {
	public static ArrayList<MeatSet> Sets = new ArrayList<>();

	public String name;

	public MeatSetItem RAW;
	public MeatSetItem COOKED;

	public MeatBlock BLOCK;
	public PillarBlock COOKED_BLOCK;

	public Item REMAINDER;

	public MeatSet(String name, int hunger, float saturation) {
		super();

		this.name = name;

		RAW = TaleItems.registerItem(
				new MeatSetItem(new Item.Settings().food(
						new FoodComponent.Builder()
								.meat()
								.hunger(hunger / 2)
								.saturationModifier(saturation / 2)
								.build())),
				"raw_" + name,
				ItemGroups.FOOD_AND_DRINK);

		COOKED = TaleItems.registerItem(
				new MeatSetItem(new Item.Settings().food(
						new FoodComponent.Builder()
								.meat()
								.hunger(hunger)
								.saturationModifier(saturation)
								.build())),
				"cooked_" + name,
				ItemGroups.FOOD_AND_DRINK);

		BLOCK = TaleItems.registerBlock(new MeatBlock(AbstractBlock.Settings.create()
				.mapColor(MapColor.PINK)
				.strength(0.8F)
				.sounds(BlockSoundGroup.WOOL), COOKED_BLOCK), name + "_block", ItemGroups.FOOD_AND_DRINK);
		COOKED_BLOCK = TaleItems.registerBlock(new MeatBlock(AbstractBlock.Settings.copy(BLOCK)
				.mapColor(MapColor.BROWN), COOKED_BLOCK), "cooked_" + name + "_block", ItemGroups.FOOD_AND_DRINK);
	}

	public MeatSet remainder(Item i) {
		REMAINDER = i;
		return this;
	}

	@Override
	public void generateBlockModels(DataGenerator.ModelGenerator generator, BlockStateModelGenerator provider) {
		provider.registerLog(BLOCK).log(BLOCK);
		provider.registerLog(COOKED_BLOCK).log(COOKED_BLOCK);

		generator.addItem(provider, BLOCK);
		generator.addItem(provider, COOKED_BLOCK);
	}

	@Override
	public void generateBlockTags(ITagBuilderProvider<Block> provider, WrapperLookup arg) {
		provider.apply(BlockTags.AXE_MINEABLE)
				.add(BLOCK)
				.add(COOKED_BLOCK);

		provider.apply(BlockTags.SWORD_EFFICIENT)
				.add(BLOCK)
				.add(COOKED_BLOCK);
	}

	@Override
	public void generateBlockLootTables(FabricBlockLootTableProvider provider) {
		provider.addDrop(BLOCK);	
		provider.addDrop(COOKED_BLOCK);	
	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(RAW, Models.GENERATED);
		generator.register(COOKED, Models.GENERATED);
	}

	@Override
	public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		offerFoodCookingRecipe(exporter, "smelting", RecipeSerializer.SMELTING, 200, RAW, COOKED, 0.35f);
		offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 100, RAW, COOKED, 0.35f);
		offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 600, RAW, COOKED, 0f);
	
		offerReversibleCompactingRecipes(exporter, RecipeCategory.FOOD, RAW, RecipeCategory.FOOD, BLOCK);
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, COOKED, 12)
		.input(COOKED_BLOCK)
		.criterion(hasItem(COOKED_BLOCK), conditionsFromItem(COOKED_BLOCK))
		.offerTo(exporter, new Identifier("cooked_"+name+"_block"));
	}

	public class MeatSetItem extends Item {
		public MeatSetItem(Settings settings) {
			super(settings);
		}

		@Override
		public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
			stack = super.finishUsing(stack, world, user);

			ItemStack s = REMAINDER.getDefaultStack();
			if (stack.getCount() == 0)
				return s;

			if (user instanceof PlayerEntity pe) {
				if (pe.giveItemStack(s)) {
					return stack;
				}
			}
			user.dropStack(s);
			return stack;
		}
	}

	public class MeatBlock extends PillarBlock {
		public MeatBlock(Settings settings, Block cookedBlock) {
			super(settings);
		}

		@Override
		public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
			BlockState upState = world.getBlockState(pos.up());
			Block upBlock = upState.getBlock();
			BlockState downState = world.getBlockState(pos.down());
			Block downBlock = downState.getBlock();

			if (upBlock instanceof FireBlock || downBlock instanceof FireBlock ||
					(downBlock instanceof CampfireBlock && CampfireBlock.isLitCampfire(downState))) {
				BlockState cookedState = COOKED_BLOCK.getDefaultState()
						.with(PillarBlock.AXIS, state.get(PillarBlock.AXIS));
				world.setBlockState(pos, cookedState);
				world.playSoundAtBlockCenter(pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f, true);
			}
		}

		@Override
		public boolean hasRandomTicks(BlockState state) {
			return true;
		}
	}
}
