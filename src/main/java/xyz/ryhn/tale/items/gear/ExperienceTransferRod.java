package xyz.ryhn.tale.items.gear;

import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import xyz.ryhn.tale.XPCraftingRecipe;

public class ExperienceTransferRod extends Item {
	public ExperienceTransferRod(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos();
		if(context.getWorld().getBlockState(pos).getBlock() == Blocks.CAULDRON)
		{
			List<ItemEntity> items = context.getWorld().getEntitiesByClass(ItemEntity.class, new Box(pos,pos), e->true);
			if(!context.getWorld().isClient())
			{
				if(XPCraftingRecipe.tryCraftAny(context.getPlayer(), context.getWorld(), context.getBlockPos(), items))
					return ActionResult.SUCCESS;
			}
		}

		return super.useOnBlock(context);
	}
}
