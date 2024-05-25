package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import xyz.ryhn.tale.items.TaleItems;

public class BlockSet {
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
		BLOCKS.add(TaleItems.registerBlock(SLAB, name + "_slab", ItemGroups.BUILDING_BLOCKS));
		bfb.slab(SLAB);

		STAIRS = new StairsBlock(PARENT.getDefaultState(), Settings.copy(PARENT));
		BLOCKS.add(TaleItems.registerBlock(STAIRS, name + "_stairs", ItemGroups.BUILDING_BLOCKS));
		bfb.stairs(STAIRS);

		PRESSURE_PLATE = new PressurePlateBlock(SET_TYPE,
				Settings.copy(Blocks.OAK_PRESSURE_PLATE)
						.noCollision()
						.pistonBehavior(PistonBehavior.DESTROY));
		BLOCKS.add(TaleItems.registerBlock(PRESSURE_PLATE, name + "_pressure_plate", ItemGroups.REDSTONE));
		bfb.pressurePlate(PRESSURE_PLATE);

			BUTTON = new ButtonBlock(SET_TYPE, isWood ? 30 : 20,
			Settings.copy(PARENT)
			.noCollision()
			.pistonBehavior(PistonBehavior.DESTROY));
		BLOCKS.add(TaleItems.registerBlock(BUTTON, name + "_button", ItemGroups.REDSTONE));
		bfb.button(BUTTON);

		if (isWood) {
			WOOD_TYPE = new WoodType(name, SET_TYPE);

			DOOR = new DoorBlock(SET_TYPE, Settings.copy(PARENT)
					.nonOpaque()
					.pistonBehavior(PistonBehavior.DESTROY));
			BLOCKS.add(TaleItems.registerBlock(DOOR, name + "_door", ItemGroups.BUILDING_BLOCKS));
			bfb.door(DOOR);

			TRAPDOOR = new TrapdoorBlock(SET_TYPE, Settings.copy(PARENT)
					.nonOpaque()
					.pistonBehavior(PistonBehavior.DESTROY));
			BLOCKS.add(TaleItems.registerBlock(TRAPDOOR, name + "_trapdoor", ItemGroups.BUILDING_BLOCKS));
			bfb.trapdoor(TRAPDOOR);

			FENCE = new FenceBlock(Settings.copy(Blocks.OAK_FENCE));
			BLOCKS.add(TaleItems.registerBlock(FENCE, name + "_fence", ItemGroups.BUILDING_BLOCKS));
			bfb.fence(FENCE);

			FENCE_GATE = new FenceGateBlock(WOOD_TYPE, Settings.copy(Blocks.OAK_FENCE_GATE));
			BLOCKS.add(TaleItems.registerBlock(FENCE_GATE, name + "_fence_gate", ItemGroups.BUILDING_BLOCKS));
			bfb.fenceGate(FENCE_GATE);
		} else {
			WALL = new WallBlock(Settings.copy(PARENT));
			BLOCKS.add(TaleItems.registerBlock(WALL, name + "_wall", ItemGroups.BUILDING_BLOCKS));
			bfb.wall(WALL);
		}

		FAMILY = bfb.build();

		Sets.add(this);
	}
}
