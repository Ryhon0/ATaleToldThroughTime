package xyx.ryhn.rworld;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import xyx.ryhn.rworld.items.Blah;
import xyx.ryhn.rworld.items.gear.MagicClock;
import xyx.ryhn.rworld.items.gear.OxidizationWand;
import xyx.ryhn.rworld.items.gear.ScopedCrossbow;
import xyx.ryhn.rworld.items.gear.trinkets.TeamBand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RWorld implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("rworld");

	public static final Item CUSTOM_ITEM = new Blah(new Item.Settings());
	public static final Block CUSTOM_BLOCK =
		new Block(Block.Settings.create()
			.strength(2f)
			.requiresTool());

	@Override
	public void onInitialize() {
		registerItem(ScopedCrossbow.ITEM, "scoped_crossbow", ItemGroups.COMBAT);
		registerItem(CUSTOM_ITEM, "blah", ItemGroups.TOOLS);
		registerItem(MagicClock.ITEM, "magic_clock", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM, "oxidization_wand", ItemGroups.TOOLS);
		registerItem(OxidizationWand.ITEM_INVERSE, "deoxidization_wand", ItemGroups.TOOLS);
		
		registerItem(TeamBand.ITEM, "team_band", ItemGroups.COMBAT);

		registerBlock(CUSTOM_BLOCK, "block", ItemGroups.BUILDING_BLOCKS);
	
		CustomPortalBuilder.beginPortal()
			.destDimID(Key("rworld"))
			.frameBlock(Blocks.LAPIS_BLOCK)
			.lightWithItem(Items.CLOCK)
			.onlyLightInOverworld()
			.tintColor(0,0,255)
			.registerPortal();
	}

	void registerItem(Item i, String id, RegistryKey<ItemGroup> category)
	{
		Registry.register(Registries.ITEM,
			Key(id),
			i);

		ItemGroupEvents.modifyEntriesEvent(category).register(content -> 
		{
			content.add(i);
		});
	}

	void registerBlock(Block b, String id, RegistryKey<ItemGroup> category)
	{
		BlockItem bi = new BlockItem(b, new Item.Settings());
		Registry.register(Registries.BLOCK,
			Key(id), 
			b);

		registerItem(bi, id, category);
	}

	public static Identifier Key(String id)
	{
		return new Identifier("rworld", id);
	}
}