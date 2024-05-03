package xyx.ryhn.rworld.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import xyx.ryhn.rworld.RWorld;
import xyx.ryhn.rworld.RWorldSounds;
import xyx.ryhn.rworld.items.gear.ExperienceTransferRod;
import xyx.ryhn.rworld.items.gear.MagicClock;
import xyx.ryhn.rworld.items.gear.OxidizationWand;
import xyx.ryhn.rworld.items.gear.ScopedCrossbow;
import xyx.ryhn.rworld.items.gear.trinkets.TeamBand;
import xyx.ryhn.rworld.xpcrafting.XPCraftingRecipe;

public class RWorldItems {
	public static final Item QUARTZ_CRYSTAL = new Item(new Item.Settings());
	public static final Block QUARTZ_CRYSTAL_ORE = new ExperienceDroppingBlock(UniformIntProvider.create(2, 10), Block.Settings.create()
		.hardness(2.0f)
		.resistance(2.0f)
		.requiresTool()
		.sounds(BlockSoundGroup.STONE));
	public static final Block DEEPSLATE_QUARTZ_CRYSTAL_ORE = new ExperienceDroppingBlock(UniformIntProvider.create(5, 10), Block.Settings.create()
		.hardness(3.0f)
		.resistance(3.0f)
		.requiresTool()
		.sounds(BlockSoundGroup.DEEPSLATE));
	public static final Block QUARTZ_CRYSTAL_BLOCK = new Block(Block.Settings.create()
		.hardness(1.0f)
		.resistance(1.0f)
		.requiresTool()
		.sounds(BlockSoundGroup.CALCITE));

	public static final BowItem GOLDEN_BOW = new BowItem(new Item.Settings());
	public static final ExperienceTransferRod EXPERIENCE_TRANSFER_ROD = new ExperienceTransferRod(new Item.Settings());

	public static final MendingClothEnchantment IRON_MENDING = new MendingClothEnchantment(new Item[] {
			Items.IRON_PICKAXE,
			Items.IRON_AXE,
			Items.IRON_SWORD,
			Items.IRON_SHOVEL,
			Items.IRON_HOE,
			Items.SHEARS,
			Items.IRON_HELMET,
			Items.IRON_CHESTPLATE,
			Items.IRON_LEGGINGS,
			Items.IRON_BOOTS,
			Items.CHAINMAIL_HELMET,
			Items.CHAINMAIL_CHESTPLATE,
			Items.CHAINMAIL_LEGGINGS,
			Items.CHAINMAIL_BOOTS,
	});
	public static final Item IRON_MENDING_CLOTH = new MendingTransferItem(new Item.Settings(),
			IRON_MENDING, new Item[] {
					Items.IRON_PICKAXE,
					Items.IRON_AXE,
					Items.IRON_SWORD,
					Items.IRON_SHOVEL,
					Items.IRON_HOE,
					Items.SHEARS },
			RWorldSounds.CLOTH_FASTEN, false);
	public static final Item IRON_MENDING_PLATE = new MendingTransferItem(new Item.Settings(),
			IRON_MENDING, new Item[] {
					Items.IRON_HELMET,
					Items.IRON_CHESTPLATE,
					Items.IRON_LEGGINGS,
					Items.IRON_BOOTS,
					Items.CHAINMAIL_HELMET,
					Items.CHAINMAIL_CHESTPLATE,
					Items.CHAINMAIL_LEGGINGS,
					Items.CHAINMAIL_BOOTS },
			SoundEvents.ITEM_ARMOR_EQUIP_IRON, true);
	public static final Item IRON_MENDING_BOTTLE = new MendingBottle(new Item.Settings().maxDamage(500), IRON_MENDING);

	public static final MendingClothEnchantment GOLD_MENDING = new MendingClothEnchantment(new Item[] {
			Items.GOLDEN_PICKAXE,
			Items.GOLDEN_AXE,
			Items.GOLDEN_SWORD,
			Items.GOLDEN_SHOVEL,
			Items.GOLDEN_HOE,
			Items.GOLDEN_HELMET,
			Items.GOLDEN_CHESTPLATE,
			Items.GOLDEN_LEGGINGS,
			Items.GOLDEN_BOOTS,
	});
	public static final Item GOLD_MENDING_CLOTH = new MendingTransferItem(new Item.Settings(),
			GOLD_MENDING, new Item[] {
					Items.GOLDEN_PICKAXE,
					Items.GOLDEN_AXE,
					Items.GOLDEN_SWORD,
					Items.GOLDEN_SHOVEL,
					Items.GOLDEN_HOE },
			RWorldSounds.CLOTH_FASTEN, false);
	public static final Item GOLD_MENDING_PLATE = new MendingTransferItem(new Item.Settings(),
			GOLD_MENDING, new Item[] {
					Items.GOLDEN_HELMET,
					Items.GOLDEN_CHESTPLATE,
					Items.GOLDEN_LEGGINGS,
					Items.GOLDEN_BOOTS },
			SoundEvents.ITEM_ARMOR_EQUIP_GOLD, true);
	public static final Item GOLD_MENDING_BOTTLE = new MendingBottle(new Item.Settings().maxDamage(1000), GOLD_MENDING);

	public static final MendingClothEnchantment DIAMOND_MENDING = new MendingClothEnchantment(new Item[] {
			Items.DIAMOND_PICKAXE,
			Items.DIAMOND_AXE,
			Items.DIAMOND_SWORD,
			Items.DIAMOND_SHOVEL,
			Items.DIAMOND_HOE,
			Items.DIAMOND_HELMET,
			Items.DIAMOND_CHESTPLATE,
			Items.DIAMOND_LEGGINGS,
			Items.DIAMOND_BOOTS,
	});
	public static final Item DIAMOND_MENDING_CLOTH = new MendingTransferItem(new Item.Settings(),
			DIAMOND_MENDING, new Item[] {
					Items.DIAMOND_PICKAXE,
					Items.DIAMOND_AXE,
					Items.DIAMOND_SWORD,
					Items.DIAMOND_SHOVEL,
					Items.DIAMOND_HOE },
			RWorldSounds.CLOTH_FASTEN, false);
	public static final Item DIAMOND_MENDING_PLATE = new MendingTransferItem(new Item.Settings(),
			DIAMOND_MENDING, new Item[] {
					Items.DIAMOND_HELMET,
					Items.DIAMOND_CHESTPLATE,
					Items.DIAMOND_LEGGINGS,
					Items.DIAMOND_BOOTS },
			SoundEvents.ITEM_ARMOR_EQUIP_GOLD, true);
	public static final Item DIAMOND_MENDING_BOTTLE = new MendingBottle(new Item.Settings().maxDamage(2000),
			DIAMOND_MENDING);

	public static void registerItems() {
		{
			registerItem(ScopedCrossbow.ITEM, "scoped_crossbow", ItemGroups.COMBAT);
			{
				ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("pull"),
						(stack, world, entity, seed) -> {
							if (entity == null) {
								return 0.0f;
							}
							if (CrossbowItem.isCharged(stack)) {
								return 0.0f;
							}
							return (float) (stack.getMaxUseTime() - entity.getItemUseTimeLeft())
									/ (float) CrossbowItem.getPullTime(stack);
						});
				ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("pulling"),
						(stack, world, entity, seed) -> entity != null && entity.isUsingItem()
								&& entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0f : 0.0f);
				ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("charged"),
						(stack, world, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0f : 0.0f);
				ModelPredicateProviderRegistry.register(ScopedCrossbow.ITEM, new Identifier("firework"),
						(stack, world, entity,
								seed) -> CrossbowItem.isCharged(stack)
										&& CrossbowItem.hasProjectile(stack, Items.FIREWORK_ROCKET)
												? 1.0f
												: 0.0f);
			}
		}

		{
			registerItem(GOLDEN_BOW, "golden_bow", ItemGroups.COMBAT);
			{
				ModelPredicateProviderRegistry.register(GOLDEN_BOW, new Identifier("pull"),
						(stack, world, entity, seed) -> {
							if (entity == null) {
								return 0.0f;
							}
							if (entity.getActiveItem() != stack) {
								return 0.0f;
							}
							return (float) (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
						});
				ModelPredicateProviderRegistry.register(GOLDEN_BOW, new Identifier("pulling"), (stack, world, entity,
						seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f
								: 0.0f);
			}
		}

		registerItem(EXPERIENCE_TRANSFER_ROD, "experience_transfer_rod", ItemGroups.TOOLS);
		registerItem(MagicClock.ITEM, "magic_clock", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM, "oxidization_wand", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM_INVERSE, "deoxidization_wand", ItemGroups.TOOLS);

		registerItem(TeamBand.ITEM, "team_band", ItemGroups.COMBAT);

		{
			registerMendingSet("diamond", Items.DIAMOND, DIAMOND_MENDING, DIAMOND_MENDING_CLOTH, DIAMOND_MENDING_PLATE,
					DIAMOND_MENDING_BOTTLE);
			registerMendingSet("iron", Items.IRON_INGOT, IRON_MENDING, IRON_MENDING_CLOTH, IRON_MENDING_PLATE,
					IRON_MENDING_BOTTLE);
			registerMendingSet("gold", Items.GOLD_INGOT, GOLD_MENDING, GOLD_MENDING_CLOTH, GOLD_MENDING_PLATE,
					GOLD_MENDING_BOTTLE);
		}

		registerItem(QUARTZ_CRYSTAL, "quartz_crystal", ItemGroups.INGREDIENTS);
		registerBlock(QUARTZ_CRYSTAL_ORE, "quartz_crystal_ore", ItemGroups.BUILDING_BLOCKS);
		registerBlock(DEEPSLATE_QUARTZ_CRYSTAL_ORE, "deepslate_quartz_crystal_ore", ItemGroups.BUILDING_BLOCKS);
		registerBlock(QUARTZ_CRYSTAL_BLOCK, "quartz_crystal_block", ItemGroups.BUILDING_BLOCKS);
	}

	static void registerMendingSet(String prefix, Item material, Enchantment ench, Item cloth, Item plate,
			Item bottle) {
		
		XPCraftingRecipe.addRecipe(new XPCraftingRecipe.Builder()
			.requireItem(Items.GLASS_BOTTLE)
			.requireItem(Items.LAPIS_LAZULI)
			.requireItem(material)
			.build(0, bottle));
		
		registerEnchantment(ench, prefix + "_mending");
		registerItem(cloth, prefix + "_mending_cloth", ItemGroups.INGREDIENTS);
		registerItem(plate, prefix + "_mending_plate", ItemGroups.INGREDIENTS);
		registerItem(bottle, prefix + "_mending_bottle", ItemGroups.INGREDIENTS);
	}

	static void registerItem(Item i, String id, RegistryKey<ItemGroup> category) {
		Registry.register(Registries.ITEM,
				RWorld.Key(id),
				i);

		ItemGroupEvents.modifyEntriesEvent(category).register(content -> {
			content.add(i);
		});
	}

	static void registerBlock(Block b, String id, RegistryKey<ItemGroup> category) {
		BlockItem bi = new BlockItem(b, new Item.Settings());
		Registry.register(Registries.BLOCK,
				RWorld.Key(id),
				b);

		registerItem(bi, id, category);
	}

	static void registerEnchantment(Enchantment enchant, String id) {
		Registry.register(Registries.ENCHANTMENT, RWorld.Key(id), enchant);
	}
}
