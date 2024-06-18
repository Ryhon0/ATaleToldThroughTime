package xyz.ryhn.tale.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import xyz.ryhn.tale.Main;
import xyz.ryhn.tale.items.blocks.MultifaceBlock;
import xyz.ryhn.tale.items.blocks.SpawnBeacon;
import xyz.ryhn.tale.items.blocks.totem.RespawnTotem;
import xyz.ryhn.tale.items.gear.ExperienceTransferRod;
import xyz.ryhn.tale.items.gear.GoldWings;
import xyz.ryhn.tale.items.gear.MagicClock;
import xyz.ryhn.tale.items.gear.OxidizationWand;
import xyz.ryhn.tale.items.gear.ScopedCrossbow;
import xyz.ryhn.tale.items.gear.trinkets.TeamBand;
import xyz.ryhn.tale.items.sets.BlockSet;
import xyz.ryhn.tale.items.sets.MendingSet;
import xyz.ryhn.tale.items.sets.OreSet;
import xyz.ryhn.tale.items.sets.WoodSet;

public class TaleItems {
	public static final TagKey<Item> TAG_ITEM_PETRIFIED_EXPERIENCE = TagKey.of(RegistryKeys.ITEM,
			Main.Key("petrified_experience"));
	public static final TagKey<Block> TAG_BLOCK_PETRIFIED_EXPERIENCE = TagKey.of(RegistryKeys.BLOCK,
			Main.Key("petrified_experience"));

	public static final ExperienceDroppingBlock PETRIFIED_EXPERIENCE = new ExperienceDroppingBlock(
			Block.Settings.copy(Blocks.STONE)
					.sounds(BlockSoundGroup.AMETHYST_CLUSTER),
			UniformIntProvider.create(50, 70));
	public static final ExperienceDroppingBlock PETRIFIED_DEEPSLATE_EXPERIENCE = new ExperienceDroppingBlock(
			Block.Settings.copy(Blocks.DEEPSLATE)
					.sounds(BlockSoundGroup.AMETHYST_CLUSTER),
			UniformIntProvider.create(50, 70));

	public static final Block WHITESPACE_BLOCK = new Block(Block.Settings.create()
			.hardness(0.1f)
			.resistance(0f)
			.sounds(BlockSoundGroup.STONE)
			.noBlockBreakParticles());

	public static final Block SPAWN_BEACON = new SpawnBeacon(Block.Settings.create()
			.hardness(0.1f)
			.resistance(0f)
			.sounds(BlockSoundGroup.STONE)
			.noBlockBreakParticles());

	public static final Block RESPAWN_TOTEM = new RespawnTotem(Block.Settings.create()
			.hardness(0.1f)
			.resistance(0f)
			.sounds(BlockSoundGroup.STONE)
			.noBlockBreakParticles());

	public static final Block SALT_LAYER = new SnowBlock(Block.Settings.copy(Blocks.SNOW));
	public static final Block SALT_BLOCK = new Block(Block.Settings.copy(Blocks.STONE));
	public static final Block RED_CRYSTAL_BLOCK = new Block(Block.Settings.copy(Blocks.AMETHYST_BLOCK)
			.mapColor(MapColor.DARK_CRIMSON));

	public static final Block RED_MOSS_BLOCK = new Block(Block.Settings.copy(Blocks.MOSS_BLOCK)
			.mapColor(MapColor.DARK_CRIMSON));
	public static final Block RED_MOSS = new MultifaceBlock(Block.Settings.copy(Blocks.MOSS_BLOCK)
			.mapColor(MapColor.DARK_CRIMSON)
			.pistonBehavior(PistonBehavior.DESTROY)
			.nonOpaque());
	public static final Block RED_MOSSY_COBBLE = registerBlock(
			new Block(Block.Settings.copy(Blocks.MOSSY_COBBLESTONE)), "red_mossy_cobblestone",
			ItemGroups.BUILDING_BLOCKS);
	public static final BlockSet RED_MOSS_COBBLE_SET = new BlockSet(RED_MOSSY_COBBLE, "red_mossy_cobblestone",
			false);
	public static final Block RED_MOSSY_BRICKS = registerBlock(
			new Block(Block.Settings.copy(Blocks.MOSSY_STONE_BRICKS)), "red_mossy_stone_bricks",
			ItemGroups.BUILDING_BLOCKS);
	public static final BlockSet RED_MOSS_BRICKS_SET = new BlockSet(RED_MOSSY_BRICKS, "red_mossy_brick", false);

	public static final Block BLUE_MOSS_BLOCK = new Block(Block.Settings.copy(Blocks.MOSS_BLOCK)
			.mapColor(MapColor.LIGHT_BLUE));
	public static final Block BLUE_MOSS = new MultifaceBlock(Block.Settings.copy(Blocks.MOSS_BLOCK)
			.mapColor(MapColor.LIGHT_BLUE)
			.pistonBehavior(PistonBehavior.DESTROY)
			.nonOpaque());
	public static final Block BLUE_MOSSY_COBBLE = registerBlock(
			new Block(Block.Settings.copy(Blocks.MOSSY_COBBLESTONE)), "blue_mossy_cobblestone",
			ItemGroups.BUILDING_BLOCKS);
	public static final BlockSet BLUE_MOSS_COBBLE_SET = new BlockSet(BLUE_MOSSY_COBBLE, "blue_mossy_cobblestone",
			false);
	public static final Block BLUE_MOSSY_BRICKS = registerBlock(
			new Block(Block.Settings.copy(Blocks.MOSSY_STONE_BRICKS)), "blue_mossy_stone_bricks",
			ItemGroups.BUILDING_BLOCKS);
	public static final BlockSet BLUE_MOSS_BRICKS_SET = new BlockSet(BLUE_MOSSY_BRICKS, "blue_mossy_brick", false);

	public static final Block MOSS = new MultifaceBlock(Block.Settings.copy(Blocks.MOSS_BLOCK)
			.mapColor(MapColor.GREEN)
			.pistonBehavior(PistonBehavior.DESTROY)
			.nonOpaque());

	public static final BowItem GOLDEN_BOW = new BowItem(new Item.Settings());
	public static final ExperienceTransferRod EXPERIENCE_TRANSFER_ROD = new ExperienceTransferRod(new Item.Settings());

	public static final GoldWings GOLD_WINGS = registerItem(new GoldWings(new Item.Settings().maxDamage(300)),
			"gold_wings", ItemGroups.COMBAT);

	public static final MendingSet IRON_MENDING_SET = new MendingSet("iron", Items.IRON_INGOT, 500);
	public static final MendingSet GOLD_MENDING_SET = new MendingSet("gold", Items.GOLD_INGOT, 2000);
	public static final MendingSet DIAMOND_MENDING_SET = new MendingSet("diamond", Items.DIAMOND, 1500);

	public static RegistryKey<ConfiguredFeature<?, ?>> MAPLE_TREE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
			Main.Key("maple_tree"));
	public static WoodSet MapleSet = new WoodSet("maple", MapColor.YELLOW, MapColor.WHITE, MapColor.YELLOW, MAPLE_TREE);
	public static RegistryKey<ConfiguredFeature<?, ?>> MORNING_TREE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
			Main.Key("morning_tree"));
	public static WoodSet MorningSet = new WoodSet("morning", MapColor.PURPLE, MapColor.BLACK, MapColor.PURPLE,
			MORNING_TREE);

	public static OreSet QuartzCrystalSet = new OreSet("quartz_crystal", "",
			Block.Settings.copy(Blocks.STONE)
					.hardness(2.0f)
					.resistance(2.0f)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE),
			Block.Settings.copy(Blocks.DEEPSLATE)
					.hardness(3.0f)
					.resistance(3.0f)
					.requiresTool()
					.sounds(BlockSoundGroup.DEEPSLATE),
			MiningLevels.STONE)
			.drops(3, 5)
			.dropsXP(5, 8);
	public static OreSet SilverSet = new OreSet("silver", "_ingot",
			Settings.copy(Blocks.IRON_ORE),
			Settings.copy(Blocks.DEEPSLATE_IRON_ORE),
			MiningLevels.STONE)
			.drops(1, 10)
			.withRawOre(Settings.copy(Blocks.RAW_IRON_BLOCK))
			.withTools(MiningLevels.IRON, 200, 6.5F, 2.0F, 20)
			.withArmor(15, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
					2, 6, 5, 2,
					0f, 0f);
	public static OreSet MithrilSet = new OreSet("mithril", "_ingot",
			Settings.copy(Blocks.DIAMOND_ORE),
			Settings.copy(Blocks.DEEPSLATE_DIAMOND_ORE),
			MiningLevels.DIAMOND)
			.withRawOre(Settings.copy(Blocks.DIAMOND_BLOCK))
			.withTools(MiningLevels.NETHERITE, 2000, 10F, 4.0F, 20)
			.withArmor(37, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
					3, 8, 6, 3,
					0f, 0.25f)
			.dropsXP(5, 8)
			.noFortune();

	public static UnsealedEnchantedBook UNSEALED_ENCHANTED_BOOK = registerItem(
			new UnsealedEnchantedBook(0, new Item.Settings().rarity(Rarity.UNCOMMON)),
			"unsealed_enchanted_book", null);
	public static UnsealedEnchantedBook OVERFLOWING_UNSEALED_ENCHANTED_BOOK = registerItem(
			new UnsealedEnchantedBook(1, new Item.Settings().rarity(Rarity.RARE)),
			"overflowing_unsealed_enchanted_book", null);
	public static UnsealedEnchantedBook AWAKENED_UNSEALED_ENCHANTED_BOOK = registerItem(
			new UnsealedEnchantedBook(2, new Item.Settings().rarity(Rarity.EPIC)),
			"awakened_unsealed_enchanted_book", null);

	public static void registerItems() {
		registerItem(ScopedCrossbow.ITEM, "scoped_crossbow", ItemGroups.COMBAT);
		registerItem(GOLDEN_BOW, "golden_bow", ItemGroups.COMBAT);

		registerItem(EXPERIENCE_TRANSFER_ROD, "experience_transfer_rod", ItemGroups.TOOLS);
		registerItem(MagicClock.ITEM, "magic_clock", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM, "oxidization_wand", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM_INVERSE, "deoxidization_wand", ItemGroups.TOOLS);

		registerItem(TeamBand.ITEM, "team_band", ItemGroups.COMBAT);

		registerBlock(PETRIFIED_EXPERIENCE, "petrified_experience", ItemGroups.BUILDING_BLOCKS);
		registerBlock(PETRIFIED_DEEPSLATE_EXPERIENCE, "petrified_deepslate_experience", ItemGroups.BUILDING_BLOCKS);

		registerBlock(WHITESPACE_BLOCK, "whitespace_block", ItemGroups.BUILDING_BLOCKS);

		registerBlock(SPAWN_BEACON, "spawn_beacon", ItemGroups.BUILDING_BLOCKS);
		PointOfInterestHelper.register(SpawnBeacon.POI_KEY.getValue(), 0, SpawnBeacon.SEARCH_DISTANCE, SPAWN_BEACON);

		registerBlock(RESPAWN_TOTEM, "respawn_totem", ItemGroups.BUILDING_BLOCKS);
		PointOfInterestHelper.register(RespawnTotem.POI_KEY.getValue(), 0, RespawnTotem.SEARCH_DISTANCE, RESPAWN_TOTEM);

		registerBlock(SALT_LAYER, "salt_layer", ItemGroups.BUILDING_BLOCKS);
		registerBlock(SALT_BLOCK, "salt_block", ItemGroups.BUILDING_BLOCKS);
		registerBlock(RED_CRYSTAL_BLOCK, "red_crystal_block", ItemGroups.BUILDING_BLOCKS);

		registerBlock(RED_MOSS, "red_moss", ItemGroups.BUILDING_BLOCKS);
		registerBlock(RED_MOSS_BLOCK, "red_moss_block", ItemGroups.BUILDING_BLOCKS);

		registerBlock(BLUE_MOSS, "blue_moss", ItemGroups.BUILDING_BLOCKS);
		registerBlock(BLUE_MOSS_BLOCK, "blue_moss_block", ItemGroups.BUILDING_BLOCKS);

		registerBlock(MOSS, "moss", ItemGroups.BUILDING_BLOCKS);
	}

	public static <T extends Item> T registerItem(T i, String id, RegistryKey<ItemGroup> category) {
		Registry.register(Registries.ITEM,
				Main.Key(id),
				i);

		if (category != null)
			ItemGroupEvents.modifyEntriesEvent(category).register(content -> {
				content.add(i);
			});
		return i;
	}

	public static <T extends Block> T registerBlock(T b, String id, RegistryKey<ItemGroup> category) {
		registerBlockWithNoItem(b, id);
		registerItem(new BlockItem(b, new Item.Settings()), id, category);
		return b;
	}

	public static <T extends Block> T registerBlockWithNoItem(T b, String id) {
		return Registry.register(Registries.BLOCK, Main.Key(id), b);
	}

	public static <T extends Enchantment> T registerEnchantment(T enchant, String id) {
		return Registry.register(Registries.ENCHANTMENT, Main.Key(id), enchant);
	}
}
