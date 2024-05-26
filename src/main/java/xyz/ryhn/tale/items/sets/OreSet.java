package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Util;
import xyz.ryhn.tale.Main;
import xyz.ryhn.tale.items.TaleItems;

public class OreSet {
	public static ArrayList<OreSet> Sets = new ArrayList<>();

	public String name;
	public int level;
	public boolean affectedByFortune = true;

	public TagKey<Block> BLOCK_ORE_TAG;
	public TagKey<Item> ITEM_ORE_TAG;

	public Block BLOCK;
	public Block ORE_BLOCK;
	public Block DEEPSLATE_ORE_BLOCK;
	public Block RAW_BLOCK;
	public Item RAW;
	public Item INGOT;
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

	public OreSet(String name, Settings oreSettings, Settings deepSettings, Settings rawOreSettings, int level) {
		this.name = name;

		BLOCK_ORE_TAG = TagKey.of(RegistryKeys.BLOCK, Main.Key(name + "_ores"));
		ITEM_ORE_TAG = TagKey.of(RegistryKeys.ITEM, Main.Key(name + "_ores"));

		BLOCK = new Block(oreSettings);
		ORE_BLOCK = new Block(oreSettings);
		RAW_BLOCK = new Block(rawOreSettings);
		DEEPSLATE_ORE_BLOCK = new Block(deepSettings);

		RAW = new Item(new Item.Settings());
		INGOT = new Item(new Item.Settings());
		NUGGET = new Item(new Item.Settings());

		TaleItems.registerBlock(BLOCK, name + "_block", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(ORE_BLOCK, name + "_ore", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(DEEPSLATE_ORE_BLOCK, "deepslate_" + name + "_ore", ItemGroups.BUILDING_BLOCKS);
		TaleItems.registerBlock(RAW_BLOCK, "raw_" + name + "_block", ItemGroups.BUILDING_BLOCKS);

		TaleItems.registerItem(RAW, "raw_" + name, ItemGroups.INGREDIENTS);
		TaleItems.registerItem(INGOT, name + "_ingot", ItemGroups.INGREDIENTS);
		TaleItems.registerItem(NUGGET, name + "_nugget", ItemGroups.INGREDIENTS);

		Sets.add(this);
	}

	public OreSet withTools(int miningLevel, int itemDurability, float miningSpeed, float attackDamage,
			int enchantability) {
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
			return Ingredient.ofItems(parent.INGOT);
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
			return Ingredient.ofItems(parent.INGOT);
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
}
