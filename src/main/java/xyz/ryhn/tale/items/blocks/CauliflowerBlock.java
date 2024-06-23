package xyz.ryhn.tale.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import xyz.ryhn.tale.items.TaleItems;

public class CauliflowerBlock extends CropBlock {
	public CauliflowerBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return TaleItems.CAULIFLOWER_SEEDS;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getShape(state.get(AGE));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {		
		if(state.get(AGE) >= 7)
			return getShape(7);
		else return VoxelShapes.empty();
	}

	VoxelShape getShape(int age)
	{
		float start;
				float size;
				float height;

		switch(age)
		{
			default:
			case 0:
			case 1:
			case 2:
			case 3:
				start = 6f / 16;
				size = 4f / 16;
				height = 8f / 16;
				return VoxelShapes.cuboid(start, 0, start, start + size, height, start + size);
			case 4:
			case 5:
			case 6:
				start = 5f / 16;
				size = 6f / 16;
				height = 8f / 16;
				return VoxelShapes.cuboid(start, 0, start, start + size, height, start + size);
			case 7:
				start = 5f / 16;
				size = 6f / 16;
				return VoxelShapes.cuboid(start, 0, start, start + size, size, start + size);
		}
	}
}
