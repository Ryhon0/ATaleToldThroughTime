package xyx.ryhn.rworld.items;

import java.util.ArrayList;
import java.util.Optional;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import xyx.ryhn.rworld.RWorld;
import xyx.ryhn.rworld.RWorldSounds;
import xyx.ryhn.rworld.XPCraftingRecipe;
import xyx.ryhn.rworld.items.blocks.MultifaceBlock;
import xyx.ryhn.rworld.items.blocks.SpawnBeacon;
import xyx.ryhn.rworld.items.blocks.totem.RespawnTotem;
import xyx.ryhn.rworld.items.enchants.MendingClothEnchantment;
import xyx.ryhn.rworld.items.gear.ExperienceTransferRod;
import xyx.ryhn.rworld.items.gear.MagicClock;
import xyx.ryhn.rworld.items.gear.MendingBottle;
import xyx.ryhn.rworld.items.gear.MendingTransferItem;
import xyx.ryhn.rworld.items.gear.OxidizationWand;
import xyx.ryhn.rworld.items.gear.ScopedCrossbow;
import xyx.ryhn.rworld.items.gear.trinkets.TeamBand;

public class RWorldItems {
	public static final TagKey<Item> TAG_ITEM_QUARTZ_CRYSTAL_ORES = TagKey.of(RegistryKeys.ITEM,
			RWorld.Key("quartz_crystal_ores"));
	public static final TagKey<Block> TAG_BLOCK_QUARTZ_CRYSTAL_ORES = TagKey.of(RegistryKeys.BLOCK,
			RWorld.Key("quartz_crystal_ores"));
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
			RWorld.Key("petrified_experience"));
	public static final TagKey<Block> TAG_BLOCK_PETRIFIED_EXPERIENCE = TagKey.of(RegistryKeys.BLOCK,
			RWorld.Key("petrified_experience"));

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

	public static class MendingSet {
		public MendingClothEnchantment ENCHANTMENT;
		public MendingBottle BOTTLE;
		public MendingTransferItem CLOTH;
		public MendingTransferItem PLATE;

		public MendingSet(String prefix, Item material, int bottleDurability, Item[] tools, Item[] armors) {
			Item[] toolsAndArmor = new Item[tools.length + armors.length];
			System.arraycopy(tools, 0, toolsAndArmor, 0, tools.length);
			System.arraycopy(armors, 0, toolsAndArmor, tools.length, armors.length);
			ENCHANTMENT = new MendingClothEnchantment(toolsAndArmor);
			BOTTLE = new MendingBottle(new Item.Settings().maxDamage(bottleDurability), ENCHANTMENT);
			CLOTH = new MendingTransferItem(new Item.Settings(), ENCHANTMENT, tools, RWorldSounds.CLOTH_FASTEN, false);
			PLATE = new MendingTransferItem(new Item.Settings(), ENCHANTMENT, armors, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
					true);

			XPCraftingRecipe.addRecipe(new XPCraftingRecipe.Builder()
					.requireItem(Items.GLASS_BOTTLE)
					.requireItem(Items.LAPIS_LAZULI, 5)
					.requireItem(material)
					.build(0, BOTTLE));

			registerEnchantment((Enchantment) ENCHANTMENT, prefix + "_mending");
			registerItem(CLOTH, prefix + "_mending_cloth", ItemGroups.INGREDIENTS);
			registerItem(PLATE, prefix + "_mending_plate", ItemGroups.INGREDIENTS);
			registerItem(BOTTLE, prefix + "_mending_bottle", ItemGroups.INGREDIENTS);
		}
	}

	public static class WoodSet {
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
			this.name = name;
			TREE = tree;

			ITEM_LOG_TAG = TagKey.of(RegistryKeys.ITEM, RWorld.Key(name + "_logs"));
			BLOCK_LOG_TAG = TagKey.of(RegistryKeys.BLOCK, RWorld.Key(name + "_logs"));

			LOG = new PillarBlock(Settings.copy(Blocks.OAK_LOG).mapColor(logColor));
			CUT_LOG = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_LOG).mapColor(logColor));
			STRIPPED_LOG = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_LOG).mapColor(plankColor));

			WOOD = new PillarBlock(Settings.copy(Blocks.OAK_WOOD).mapColor(logColor));
			CUT_WOOD = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(logColor));
			STRIPPED_WOOD = new PillarBlock(Settings.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(plankColor));

			PLANK = new Block(Settings.copy(Blocks.OAK_PLANKS).mapColor(plankColor));
			LEAVES = new LeavesBlock(Settings.copy(Blocks.OAK_LEAVES).mapColor(leafColor));

			registerBlock(LOG, name + "_log", ItemGroups.BUILDING_BLOCKS);
			registerBlock(CUT_LOG, "cut_" + name + "_log", ItemGroups.BUILDING_BLOCKS);
			registerBlock(STRIPPED_LOG, "stripped_" + name + "_log", ItemGroups.BUILDING_BLOCKS);

			registerBlock(WOOD, name + "_wood", ItemGroups.BUILDING_BLOCKS);
			registerBlock(CUT_WOOD, "cut_" + name + "_wood", ItemGroups.BUILDING_BLOCKS);
			registerBlock(STRIPPED_WOOD, "stripped_" + name + "_wood", ItemGroups.BUILDING_BLOCKS);

			registerBlock(PLANK, name + "_planks", ItemGroups.BUILDING_BLOCKS);
			registerBlock(LEAVES, name + "_leaves", ItemGroups.BUILDING_BLOCKS);

			SAPLING = new SaplingBlock(
					new SaplingGenerator(name, 0f, Optional.empty(),
							Optional.empty(),
							Optional.of(TREE),
							Optional.empty(),
							Optional.empty(),
							Optional.empty()),
					Settings.copy(Blocks.OAK_SAPLING).mapColor(leafColor));
			POTTED_SAPLING = new FlowerPotBlock(SAPLING, Settings.copy(Blocks.POTTED_OAK_SAPLING).mapColor(leafColor));

			registerBlock(SAPLING, name + "_sapling", ItemGroups.BUILDING_BLOCKS);
			registerBlockWithNoItem(POTTED_SAPLING, "potted_" + name + "_sapling");

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
		}
	}

	public static class BlockSet {
		public static ArrayList<BlockSet> Sets = new ArrayList<>();

		public ArrayList<Block> BLOCKS = new ArrayList<>();

		public BlockSetType SET_TYPE;
		public BlockFamily FAMILY;
		public WoodType WOOD_TYPE;
		public String Name;

		public Block PARENT;
		public Block[] All;

		public SlabBlock SLAB;
		public StairsBlock STAIRS;

		public PressurePlateBlock PRESSURE_PLATE;
		public ButtonBlock BUTTON;

		/// public SignBlock SIGN;
		/// public WallSignBlock WALL_SIGN;
		/// public HangingSignBlock HANGING_SIGN;
		/// public WallHangingSignBlock WALL_HANGING_SIGN;

		public WallBlock WALL;

		public FenceBlock FENCE;
		public FenceGateBlock FENCE_GATE;

		public DoorBlock DOOR;
		public TrapdoorBlock TRAPDOOR;

		public BlockSet(Block parent, String name, boolean isWood) {
			PARENT = parent;
			Name = name;
			BLOCKS.add(PARENT);

			BlockFamily.Builder bfb = BlockFamilies.register(PARENT);
			if (isWood)
				SET_TYPE = new BlockSetType(name);
			else
				SET_TYPE = new BlockSetType(name, true, true, false,
						net.minecraft.block.BlockSetType.ActivationRule.MOBS, BlockSoundGroup.STONE,
						SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN,
						SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
						SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF,
						SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,
						SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON);

			SLAB = new SlabBlock(Settings.copy(PARENT));
			BLOCKS.add(registerBlock(SLAB, name + "_slab", ItemGroups.BUILDING_BLOCKS));
			bfb.slab(SLAB);

			STAIRS = new StairsBlock(PARENT.getDefaultState(), Settings.copy(PARENT));
			BLOCKS.add(registerBlock(STAIRS, name + "_stairs", ItemGroups.BUILDING_BLOCKS));
			bfb.stairs(STAIRS);

			PRESSURE_PLATE = new PressurePlateBlock(SET_TYPE,
					Settings.copy(Blocks.OAK_PRESSURE_PLATE)
							.noCollision()
							.pistonBehavior(PistonBehavior.DESTROY));
			BLOCKS.add(registerBlock(PRESSURE_PLATE, name + "_pressure_plate", ItemGroups.REDSTONE));
			bfb.pressurePlate(PRESSURE_PLATE);

				BUTTON = new ButtonBlock(SET_TYPE, isWood ? 30 : 20,
				Settings.copy(PARENT)
				.noCollision()
				.pistonBehavior(PistonBehavior.DESTROY));
			BLOCKS.add(registerBlock(BUTTON, name + "_button", ItemGroups.REDSTONE));
			bfb.button(BUTTON);

			if (isWood) {
				WOOD_TYPE = new WoodType(name, SET_TYPE);

				DOOR = new DoorBlock(SET_TYPE, Settings.copy(PARENT)
						.nonOpaque()
						.pistonBehavior(PistonBehavior.DESTROY));
				BLOCKS.add(registerBlock(DOOR, name + "_door", ItemGroups.BUILDING_BLOCKS));
				bfb.door(DOOR);

				TRAPDOOR = new TrapdoorBlock(SET_TYPE, Settings.copy(PARENT)
						.nonOpaque()
						.pistonBehavior(PistonBehavior.DESTROY));
				BLOCKS.add(registerBlock(TRAPDOOR, name + "_trapdoor", ItemGroups.BUILDING_BLOCKS));
				bfb.trapdoor(TRAPDOOR);

				FENCE = new FenceBlock(Settings.copy(Blocks.OAK_FENCE));
				BLOCKS.add(registerBlock(FENCE, name + "_fence", ItemGroups.BUILDING_BLOCKS));
				bfb.fence(FENCE);

				FENCE_GATE = new FenceGateBlock(WOOD_TYPE, Settings.copy(Blocks.OAK_FENCE_GATE));
				BLOCKS.add(registerBlock(FENCE_GATE, name + "_fence_gate", ItemGroups.BUILDING_BLOCKS));
				bfb.fenceGate(FENCE_GATE);
			} else {
				WALL = new WallBlock(Settings.copy(PARENT));
				BLOCKS.add(registerBlock(WALL, name + "_wall", ItemGroups.BUILDING_BLOCKS));
				bfb.wall(WALL);
			}

			FAMILY = bfb.build();

			Sets.add(this);
		}
	}

	public static RegistryKey<ConfiguredFeature<?, ?>> MAPLE_TREE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
			RWorld.Key("maple_tree"));
	public static WoodSet MapleSet = new WoodSet("maple", MapColor.YELLOW, MapColor.WHITE, MapColor.YELLOW, MAPLE_TREE);
	public static RegistryKey<ConfiguredFeature<?, ?>> MORNING_TREE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
			RWorld.Key("morning_tree"));
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

	static Item registerItem(Item i, String id, RegistryKey<ItemGroup> category) {
		Registry.register(Registries.ITEM,
				RWorld.Key(id),
				i);

		ItemGroupEvents.modifyEntriesEvent(category).register(content -> {
			content.add(i);
		});
		return i;
	}

	static <T extends Block> T registerBlock(T b, String id, RegistryKey<ItemGroup> category) {
		registerBlockWithNoItem(b, id);
		registerItem(new BlockItem(b, new Item.Settings()), id, category);
		return b;
	}

	static <T extends Block> T registerBlockWithNoItem(T b, String id) {
		Registry.register(Registries.BLOCK, RWorld.Key(id), b);
		return b;
	}

	static Enchantment registerEnchantment(Enchantment enchant, String id) {
		Registry.register(Registries.ENCHANTMENT, RWorld.Key(id), enchant);
		return enchant;
	}
}
