package xyx.ryhn.tale.items.gear;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OxidizationWand extends Item {
	public static final Item ITEM = new OxidizationWand(new Item.Settings(), false);
	public static final Item ITEM_INVERSE = new OxidizationWand(new Item.Settings(), true);

	public boolean Inverse = false;

	public OxidizationWand(Settings settings, boolean inverse) {
		super(settings);
		this.Inverse = inverse;
	}

	static final int SneakingOxidizationRadius = 3;

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World w = context.getWorld();
		BlockPos pos = context.getBlockPos();

		boolean success = false;
		if (context.getPlayer().isSneaking()) {
			if(tryIncreaseOxidation(w, pos))
				success = true;
		} else {
			for (BlockPos p : BlockPos.iterateOutwards(pos, SneakingOxidizationRadius, SneakingOxidizationRadius,
					SneakingOxidizationRadius)) {
				if(tryIncreaseOxidation(w, p))
					success = true;
			}
		}

		if(success)
			return ActionResult.SUCCESS;
		else
			return ActionResult.FAIL;
	}

	boolean tryIncreaseOxidation(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof Oxidizable) {
			Optional<Block> res;
			if (Inverse)
				res = Oxidizable.getDecreasedOxidationBlock(state.getBlock());
			else
				res = Oxidizable.getIncreasedOxidationBlock(state.getBlock());

			if (!res.isEmpty()) {
				world.setBlockState(pos, res.get().getStateWithProperties(state));
				return true;
			}
		}
		return false;
	}
}
