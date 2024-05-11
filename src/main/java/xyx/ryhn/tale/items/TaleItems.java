package xyx.ryhn.tale.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import xyx.ryhn.tale.Main;
import xyx.ryhn.tale.items.blocks.MultifaceBlock;
import xyx.ryhn.tale.items.blocks.SpawnBeacon;
import xyx.ryhn.tale.items.blocks.totem.RespawnTotem;
import xyx.ryhn.tale.items.gear.ExperienceTransferRod;
import xyx.ryhn.tale.items.gear.GoldWings;
import xyx.ryhn.tale.items.gear.MagicClock;
import xyx.ryhn.tale.items.gear.OxidizationWand;
import xyx.ryhn.tale.items.gear.ScopedCrossbow;
import xyx.ryhn.tale.items.gear.trinkets.TeamBand;
import xyx.ryhn.tale.items.sets.BlockSet;
import xyx.ryhn.tale.items.sets.MendingSet;
import xyx.ryhn.tale.items.sets.WoodSet;

public class TaleItems {
	public static final TagKey<Item> TAG_ITEM_QUARTZ_CRYSTAL_ORES = TagKey.of(RegistryKeys.ITEM,
			Main.Key("quartz_crystal_ores"));
	public static final TagKey<Block> TAG_BLOCK_QUARTZ_CRYSTAL_ORES = TagKey.of(RegistryKeys.BLOCK,
			Main.Key("quartz_crystal_ores"));
	public static final Item QUARTZ_CRYSTAL = new Item(new Item.Settings());
	public static final Block QUARTZ_CRYSTAL_ORE = new ExperienceDroppingBlock(UniformIntProvider.create(2, 10),
			Block.Settings.copy(Blocks.STONE)
					.hardness(2.0f)
					.resistance(2.0f)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE));
	public static final Block DEEPSLATE_QUARTZ_CRYSTAL_ORE = new ExperienceDroppingBlock(
			UniformIntProvider.create(5, 10), Block.Settings.copy(Blocks.DEEPSLATE)
					.hardness(3.0f)
					.resistance(3.0f)
					.requiresTool()
					.sounds(BlockSoundGroup.DEEPSLATE));
	public static final Block QUARTZ_CRYSTAL_BLOCK = new Block(Block.Settings.copy(Blocks.QUARTZ_BLOCK)
			.hardness(1.0f)
			.resistance(1.0f)
			.requiresTool()
			.sounds(BlockSoundGroup.CALCITE));

	public static final TagKey<Item> TAG_ITEM_PETRIFIED_EXPERIENCE = TagKey.of(RegistryKeys.ITEM,
			Main.Key("petrified_experience"));
	public static final TagKey<Block> TAG_BLOCK_PETRIFIED_EXPERIENCE = TagKey.of(RegistryKeys.BLOCK,
			Main.Key("petrified_experience"));

	public static final ExperienceDroppingBlock PETRIFIED_EXPERIENCE = new ExperienceDroppingBlock(
			UniformIntProvider.create(50, 70),
			Block.Settings.copy(Blocks.STONE)
					.sounds(BlockSoundGroup.AMETHYST_CLUSTER));
	public static final ExperienceDroppingBlock PETRIFIED_DEEPSLATE_EXPERIENCE = new ExperienceDroppingBlock(
			UniformIntProvider.create(50, 70),
			Block.Settings.copy(Blocks.DEEPSLATE)
					.sounds(BlockSoundGroup.AMETHYST_CLUSTER));

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

	public static final GoldWings GOLD_WINGS = registerItem(new GoldWings(new Item.Settings().maxDamage(300)), "gold_wings", ItemGroups.COMBAT);

	public static final MendingSet IRON_MENDING_SET = new MendingSet("iron", Items.IRON_INGOT, 500,
			new Item[] {
					Items.IRON_PICKAXE,
					Items.IRON_AXE,
					Items.IRON_SWORD,
					Items.IRON_SHOVEL,
					Items.IRON_HOE,
					Items.SHEARS,
			},
			new Item[] {
					Items.IRON_HELMET,
					Items.IRON_CHESTPLATE,
					Items.IRON_LEGGINGS,
					Items.IRON_BOOTS,
					Items.CHAINMAIL_HELMET,
					Items.CHAINMAIL_CHESTPLATE,
					Items.CHAINMAIL_LEGGINGS,
					Items.CHAINMAIL_BOOTS,
			});
	public static final MendingSet GOLD_MENDING_SET = new MendingSet("gold", Items.GOLD_INGOT, 2000,
			new Item[] {
					Items.GOLDEN_PICKAXE,
					Items.GOLDEN_AXE,
					Items.GOLDEN_SWORD,
					Items.GOLDEN_SHOVEL,
					Items.GOLDEN_HOE,
			},
			new Item[] {
					Items.GOLDEN_HELMET,
					Items.GOLDEN_CHESTPLATE,
					Items.GOLDEN_LEGGINGS,
					Items.GOLDEN_BOOTS,
			});
	public static final MendingSet DIAMOND_MENDING_SET = new MendingSet("diamond", Items.DIAMOND, 1500,
			new Item[] {
					Items.DIAMOND_PICKAXE,
					Items.DIAMOND_AXE,
					Items.DIAMOND_SWORD,
					Items.DIAMOND_SHOVEL,
					Items.DIAMOND_HOE,
			},
			new Item[] {
					Items.DIAMOND_HELMET,
					Items.DIAMOND_CHESTPLATE,
					Items.DIAMOND_LEGGINGS,
					Items.DIAMOND_BOOTS,
			});

	public static RegistryKey<ConfiguredFeature<?, ?>> MAPLE_TREE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
			Main.Key("maple_tree"));
	public static WoodSet MapleSet = new WoodSet("maple", MapColor.YELLOW, MapColor.WHITE, MapColor.YELLOW, MAPLE_TREE);
	public static RegistryKey<ConfiguredFeature<?, ?>> MORNING_TREE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
			Main.Key("morning_tree"));
	public static WoodSet MorningSet = new WoodSet("morning", MapColor.PURPLE, MapColor.BLACK, MapColor.PURPLE,
			MORNING_TREE);

	public static void registerItems() {
		registerItem(ScopedCrossbow.ITEM, "scoped_crossbow", ItemGroups.COMBAT);
		registerItem(GOLDEN_BOW, "golden_bow", ItemGroups.COMBAT);

		registerItem(EXPERIENCE_TRANSFER_ROD, "experience_transfer_rod", ItemGroups.TOOLS);
		registerItem(MagicClock.ITEM, "magic_clock", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM, "oxidization_wand", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM_INVERSE, "deoxidization_wand", ItemGroups.TOOLS);

		registerItem(TeamBand.ITEM, "team_band", ItemGroups.COMBAT);

		registerItem(QUARTZ_CRYSTAL, "quartz_crystal", ItemGroups.INGREDIENTS);
		registerBlock(QUARTZ_CRYSTAL_ORE, "quartz_crystal_ore", ItemGroups.BUILDING_BLOCKS);
		registerBlock(DEEPSLATE_QUARTZ_CRYSTAL_ORE, "deepslate_quartz_crystal_ore", ItemGroups.BUILDING_BLOCKS);
		registerBlock(QUARTZ_CRYSTAL_BLOCK, "quartz_crystal_block", ItemGroups.BUILDING_BLOCKS);

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
