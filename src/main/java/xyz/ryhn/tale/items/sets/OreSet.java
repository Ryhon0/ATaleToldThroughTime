package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.VanillaRecipeProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import xyz.ryhn.tale.DataGenerator;
import xyz.ryhn.tale.Main;
import xyz.ryhn.tale.items.TaleItems;
import static net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider.*;
import static net.minecraft.data.server.recipe.RecipeProvider.offerBlasting;
import static net.minecraft.data.server.recipe.RecipeProvider.offerCompactingRecipe;
import static net.minecraft.data.server.recipe.RecipeProvider.offerShapelessRecipe;
import static net.minecraft.data.server.recipe.RecipeProvider.offerSmelting;

public class OreSet extends SetGenerator {
	public static ArrayList<OreSet> Sets = new ArrayList<>();

	public String name;
	public int level;
	public boolean affectedByFortune = true;
	public boolean hasRawOre = false;
	public boolean hasTools = false;
	public boolean hasArmor = false;

	public int minDrops = 1;
	public int maxDrops = 1;

	public TagKey<Block> BLOCK_ORE_TAG;
	public TagKey<Item> ITEM_ORE_TAG;

	public Block BLOCK;
	public ExperienceDroppingBlock ORE_BLOCK;
	public ExperienceDroppingBlock DEEPSLATE_ORE_BLOCK;
	public Block RAW_BLOCK;
	public Item RAW;
	public Item ITEM;
	public Item NUGGET;

	public List<ToolItem> tools = new ArrayList<>();
	public SwordItem SWORD;
	public ShovelItem SHOVEL;
	public PickaxeItem PICKAXE;
	public AxeItem AXE;
	public HoeItem HOE;

	public List<ArmorItem> armorItems = new ArrayList<>();
	public ArmorItem HELMET;
	public ArmorItem CHESTPLATE;
	public ArmorItem LEGGINGS;
	public ArmorItem BOOTS;

	public OreSet(String name, String itemSuffix, Settings oreSettings, Settings deepSettings, int level) {
		super();

		this.name = name;

		BLOCK_ORE_TAG = TagKey.of(RegistryKeys.BLOCK, Main.Key(name + "_ores"));
		ITEM_ORE_TAG = TagKey.of(RegistryKeys.ITEM, Main.Key(name + "_ores"));

		ITEM = new Item(new Item.Settings());
		NUGGET = new Item(new Item.Settings());

		TaleItems.registerItem(ITEM, name + itemSuffix, ItemGroups.INGREDIENTS);
		TaleItems.registerItem(NUGGET, name + "_nugget", ItemGroups.INGREDIENTS);

		BLOCK = new Block(oreSettings);
		ORE_BLOCK = new ExperienceDroppingBlock(oreSettings, UniformIntProvider.create(0, 0));
		DEEPSLATE_ORE_BLOCK = new ExperienceDroppingBlock(deepSettings, UniformIntProvider.create(0, 0));

		TaleItems.registerBlock(BLOCK, name + "_block", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(ORE_BLOCK, name + "_ore", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(DEEPSLATE_ORE_BLOCK, "deepslate_" + name + "_ore", ItemGroups.BUILDING_BLOCKS);

		Sets.add(this);
	}

	public OreSet withRawOre(Settings rawOreSettings) {
		hasRawOre = true;

		RAW = new Item(new Item.Settings());
		TaleItems.registerItem(RAW, "raw_" + name, ItemGroups.INGREDIENTS);

		RAW_BLOCK = new Block(rawOreSettings);
		TaleItems.registerBlock(RAW_BLOCK, "raw_" + name + "_block", ItemGroups.BUILDING_BLOCKS);

		return this;
	}

	public OreSet withTools(int miningLevel, int itemDurability, float miningSpeed, float attackDamage,
			int enchantability) {
		hasTools = true;

		ToolMaterial mat = new SimpleToolMaterial(this, miningLevel, itemDurability, miningSpeed, attackDamage,
				enchantability);
		SWORD = new SwordItem(mat, 3, -2.4F, new Item.Settings());
		SHOVEL = new ShovelItem(mat, 1.5F, -3.0F, new Item.Settings());
		PICKAXE = new PickaxeItem(mat, 1, -2.8F, new Item.Settings());
		AXE = new AxeItem(mat, 6.0F, -3.0F, new Item.Settings());
		HOE = new HoeItem(mat, -2, 0.0F, new Item.Settings());

		TaleItems.registerItem(SWORD, name + "_sword", ItemGroups.COMBAT);
		TaleItems.registerItem(SHOVEL, name + "_shovel", ItemGroups.TOOLS);
		TaleItems.registerItem(PICKAXE, name + "_pickaxe", ItemGroups.TOOLS);
		TaleItems.registerItem(AXE, name + "_axe", ItemGroups.TOOLS);
		TaleItems.registerItem(HOE, name + "_hoe", ItemGroups.TOOLS);

		tools = ImmutableList.of(SWORD, SHOVEL, PICKAXE, AXE, HOE);

		return this;
	}

	public OreSet withArmor(int durabilityMultiplier, int enchantability, SoundEvent equipSound,
			int helmetProtection, int chestProtection, int leggingsProtection, int bootsProtection, float toughness,
			float knockbackResistance) {
		hasArmor = true;
		ArmorMaterial mat = new SimpleArmorMaterial(this, durabilityMultiplier, enchantability, equipSound,
				helmetProtection, chestProtection, leggingsProtection, bootsProtection, toughness, knockbackResistance);

		HELMET = new ArmorItem(mat, ArmorItem.Type.HELMET, new Item.Settings());
		CHESTPLATE = new ArmorItem(mat, ArmorItem.Type.CHESTPLATE, new Item.Settings());
		LEGGINGS = new ArmorItem(mat, ArmorItem.Type.LEGGINGS, new Item.Settings());
		BOOTS = new ArmorItem(mat, ArmorItem.Type.BOOTS, new Item.Settings());

		TaleItems.registerItem(HELMET, name + "_helmet", ItemGroups.COMBAT);
		TaleItems.registerItem(CHESTPLATE, name + "_chestplate", ItemGroups.COMBAT);
		TaleItems.registerItem(LEGGINGS, name + "_leggings", ItemGroups.COMBAT);
		TaleItems.registerItem(BOOTS, name + "_boots", ItemGroups.COMBAT);

		armorItems = ImmutableList.of(HELMET, CHESTPLATE, LEGGINGS, BOOTS);

		return this;
	}

	public OreSet noFortune() {
		affectedByFortune = false;
		return this;
	}

	public OreSet dropsXP(int min, int max) {
		ORE_BLOCK.experienceDropped = UniformIntProvider.create(min, max);
		DEEPSLATE_ORE_BLOCK.experienceDropped = UniformIntProvider.create(min, max);
		return this;
	}

	public OreSet drops(int min, int max) {
		minDrops = min;
		maxDrops = max;
		return this;
	}

	public Item getOreDrop() {
		if (hasRawOre)
			return RAW;
		return ITEM;
	}

	static class SimpleToolMaterial implements ToolMaterial {
		OreSet parent;
		int miningLevel;
		int itemDurability;
		float miningSpeed;
		float attackDamage;
		int enchantability;

		private SimpleToolMaterial(OreSet parent, int miningLevel, int itemDurability, float miningSpeed,
				float attackDamage, int enchantability) {
			this.parent = parent;
			this.miningLevel = miningLevel;
			this.itemDurability = itemDurability;
			this.miningSpeed = miningSpeed;
			this.attackDamage = attackDamage;
			this.enchantability = enchantability;
		}

		public int getDurability() {
			return this.itemDurability;
		}

		public float getMiningSpeedMultiplier() {
			return this.miningSpeed;
		}

		public float getAttackDamage() {
			return this.attackDamage;
		}

		public int getMiningLevel() {
			return this.miningLevel;
		}

		public int getEnchantability() {
			return this.enchantability;
		}

		public Ingredient getRepairIngredient() {
			return Ingredient.ofItems(parent.ITEM);
		}
	}

	static class SimpleArmorMaterial implements ArmorMaterial {
		OreSet parent;
		int durabilityMultiplier;
		EnumMap<ArmorItem.Type, Integer> protectionAmounts;
		int enchantability;
		SoundEvent equipSound;
		float toughness;
		float knockbackResistance;

		private SimpleArmorMaterial(OreSet parent, int durabilityMultiplier, int enchantability, SoundEvent equipSound,
				int helmetProtection, int chestProtection, int leggingsProtection, int bootsProtection, float toughness,
				float knockbackResistance) {
			this.parent = parent;

			this.durabilityMultiplier = durabilityMultiplier;
			this.protectionAmounts = Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), (map) -> {
				map.put(Type.BOOTS, bootsProtection);
				map.put(Type.LEGGINGS, leggingsProtection);
				map.put(Type.CHESTPLATE, chestProtection);
				map.put(Type.HELMET, helmetProtection);
			});
			this.enchantability = enchantability;
			this.equipSound = equipSound;
			this.toughness = toughness;
			this.knockbackResistance = knockbackResistance;
		}

		@Override
		public int getDurability(ArmorItem.Type type) {
			return ArmorMaterials.BASE_DURABILITY.get(type) * durabilityMultiplier;
		}

		@Override
		public int getProtection(ArmorItem.Type type) {
			return this.protectionAmounts.get((Object) type);
		}

		@Override
		public int getEnchantability() {
			return this.enchantability;
		}

		@Override
		public SoundEvent getEquipSound() {
			return this.equipSound;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.ofItems(parent.ITEM);
		}

		@Override
		public String getName() {
			return parent.name;
		}

		@Override
		public float getToughness() {
			return this.toughness;
		}

		@Override
		public float getKnockbackResistance() {
			return this.knockbackResistance;
		}
	}

	
	@Override
	public void generateBlockModels(DataGenerator.ModelGenerator generator, BlockStateModelGenerator provider) {
		generator.cubeWithItem(provider, BLOCK);
		generator.cubeWithItem(provider, ORE_BLOCK);
		generator.cubeWithItem(provider, DEEPSLATE_ORE_BLOCK);
		if (hasRawOre)
			generator.cubeWithItem(provider, RAW_BLOCK);
	}

	@Override
	public void generateBlockTags(ITagBuilderProvider<Block> provider, WrapperLookup arg) {
		provider.apply(ConventionalBlockTags.ORES)
				.addTag(BLOCK_ORE_TAG);

		provider.apply(BLOCK_ORE_TAG)
				.add(ORE_BLOCK)
				.add(DEEPSLATE_ORE_BLOCK);

		provider.apply(BlockTags.PICKAXE_MINEABLE)
				.add(BLOCK)
				.add(ORE_BLOCK)
				.add(DEEPSLATE_ORE_BLOCK);

		if (hasRawOre)
			provider.apply(BlockTags.PICKAXE_MINEABLE)
					.add(RAW_BLOCK);

		provider.apply(BlockTags.BEACON_BASE_BLOCKS)
				.add(BLOCK);

		TagKey<Block> tag = null;
		switch (level) {
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
		if (tag != null) {
			provider.apply(tag)
					.add(BLOCK)
					.add(ORE_BLOCK)
					.add(DEEPSLATE_ORE_BLOCK);

			if (hasRawOre)
				provider.apply(tag)
						.add(RAW_BLOCK);
		}
	}

	@Override
	public void generateBlockLootTables(FabricBlockLootTableProvider provider) {
		provider.addDrop(BLOCK);

		if (hasRawOre)
			provider.addDrop(RAW_BLOCK);

		LeafEntry.Builder<?> itembuilder = ItemEntry.builder(getOreDrop()).apply(
				SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops)));
		if (affectedByFortune)
			itembuilder = itembuilder.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE));

		LootTable.Builder lt = BlockLootTableGenerator.dropsWithSilkTouch(ORE_BLOCK,
				provider.applyExplosionDecay(ORE_BLOCK, itembuilder));

		provider.addDrop(ORE_BLOCK, (Block b) -> lt);
		provider.addDrop(DEEPSLATE_ORE_BLOCK, (Block b) -> lt);
	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(RAW, Models.GENERATED);
		generator.register(ITEM, Models.GENERATED);
		generator.register(NUGGET, Models.GENERATED);

		if (hasTools) {
			generator.register(SWORD, Models.HANDHELD);
			generator.register(SHOVEL, Models.HANDHELD);
			generator.register(PICKAXE, Models.HANDHELD);
			generator.register(AXE, Models.HANDHELD);
			generator.register(HOE, Models.HANDHELD);
		}

		if (hasArmor) {
			generator.registerArmor(HELMET);
			generator.registerArmor(CHESTPLATE);
			generator.registerArmor(LEGGINGS);
			generator.registerArmor(BOOTS);
		}
	}

	@Override
	public void generateItemTags(ITagBuilderProvider<Item> provider, WrapperLookup wrapper) {
		if (hasRawOre) {
			provider.apply(ConventionalItemTags.RAW_ORES)
					.add(RAW);
		}

		provider.apply(ConventionalItemTags.ORES)
				.addTag(ITEM_ORE_TAG);
		provider.apply(ConventionalItemTags.NUGGETS)
				.add(NUGGET);
		provider.apply(ConventionalItemTags.INGOTS)
				.add(NUGGET);
		provider.apply(ItemTags.TRIM_MATERIALS)
				.add(ITEM);
		provider.apply(ItemTags.BEACON_PAYMENT_ITEMS)
				.add(ITEM);

		provider.apply(ITEM_ORE_TAG)
				.add(ORE_BLOCK.asItem())
				.add(DEEPSLATE_ORE_BLOCK.asItem());

		if (hasTools) {
			provider.apply(ItemTags.SWORDS).add(SWORD);
			provider.apply(ItemTags.SHOVELS).add(SHOVEL);
			provider.apply(ItemTags.PICKAXES).add(PICKAXE);
			provider.apply(ItemTags.CLUSTER_MAX_HARVESTABLES).add(PICKAXE);
			provider.apply(ItemTags.AXES).add(AXE);
			provider.apply(ItemTags.HOES).add(HOE);

			provider.apply(ItemTags.TOOLS)
					.add(SWORD)
					.add(SHOVEL)
					.add(PICKAXE)
					.add(AXE)
					.add(HOE);
		}

		if (hasArmor) {
			provider.apply(ItemTags.TRIMMABLE_ARMOR).add(HELMET);
			provider.apply(ItemTags.TRIMMABLE_ARMOR).add(CHESTPLATE);
			provider.apply(ItemTags.TRIMMABLE_ARMOR).add(LEGGINGS);
			provider.apply(ItemTags.TRIMMABLE_ARMOR).add(BOOTS);
		}
	}

	@Override
	public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		offerSmelting(exporter, ImmutableList.of(ORE_BLOCK, DEEPSLATE_ORE_BLOCK),
				RecipeCategory.MISC, ITEM, 0.7f, 200, name);
		offerBlasting(exporter, ImmutableList.of(ORE_BLOCK, DEEPSLATE_ORE_BLOCK),
				RecipeCategory.MISC, ITEM, 0.7f, 100, name);

		if (hasRawOre) {
			offerSmelting(exporter, ImmutableList.of(RAW),
					RecipeCategory.MISC, ITEM, 0.7f, 200, name);
			offerBlasting(exporter, ImmutableList.of(RAW),
					RecipeCategory.MISC, ITEM, 0.7f, 100, name);
		}

		offerShapelessRecipe(exporter, NUGGET, ITEM, name + "_nuggets", 9);
		offerCompactingRecipe(exporter, RecipeCategory.MISC, ITEM, NUGGET);
		offerCompactingRecipe(exporter, RecipeCategory.MISC, BLOCK, ITEM);
		if (hasRawOre)
			offerCompactingRecipe(exporter, RecipeCategory.MISC, RAW_BLOCK, RAW);

		if (hasTools) {
			ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, SWORD)
					.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), ITEM)
					.pattern("X").pattern("X").pattern("#")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, SHOVEL)
					.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), ITEM)
					.pattern("X").pattern("#").pattern("#")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, PICKAXE)
					.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), ITEM)
					.pattern("XXX").pattern(" # ").pattern(" # ")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, AXE)
					.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), ITEM)
					.pattern("XX").pattern("X#").pattern(" #")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, HOE)
					.input(Character.valueOf('#'), Items.STICK).input(Character.valueOf('X'), ITEM)
					.pattern("XX").pattern(" #").pattern(" #")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);

			List<ItemConvertible> items = tools.stream().map(t -> (ItemConvertible) t).toList();
			offerSmelting(exporter,
					items,
					RecipeCategory.MISC, NUGGET, 0.1f, 200, name + "_nugget_from_tools");
			offerBlasting(exporter,
					items,
					RecipeCategory.MISC, NUGGET, 0.1f, 100, name + "_nugget_from_tools");
		}

		if (hasArmor) {
			ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, HELMET)
					.input(Character.valueOf('X'), ITEM).pattern("XXX").pattern("X X")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, CHESTPLATE)
					.input(Character.valueOf('X'), ITEM).pattern("X X").pattern("XXX")
					.pattern("XXX")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, LEGGINGS)
					.input(Character.valueOf('X'), ITEM).pattern("XXX").pattern("X X")
					.pattern("X X")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BOOTS)
					.input(Character.valueOf('X'), ITEM).pattern("X X").pattern("X X")
					.criterion(FabricRecipeProvider.hasItem(ITEM),
							VanillaRecipeProvider.conditionsFromItem(ITEM))
					.offerTo(exporter);

			List<ItemConvertible> items = armorItems.stream().map(t -> (ItemConvertible) t).toList();

			offerSmelting(exporter,
					items,
					RecipeCategory.MISC, NUGGET, 0.1f, 200, name + "_nugget_from_armor");
			offerBlasting(exporter,
					items,
					RecipeCategory.MISC, NUGGET, 0.1f, 100, name + "_nugget_from_armor");
		}
	}
}
