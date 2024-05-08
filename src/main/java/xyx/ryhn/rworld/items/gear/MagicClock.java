package xyx.ryhn.rworld.items.gear;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import xyx.ryhn.rworld.RWorldDimensions;

public class MagicClock extends Item {
	public static final Item ITEM = new MagicClock(new Item.Settings());

	public MagicClock(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user instanceof ServerPlayerEntity player) {
			if (world.getDimensionKey() == DimensionTypes.OVERWORLD) {
				player.teleport(
						world.getServer().getWorld(RWorldDimensions.WORLD_KEY), user.getX(), user.getY(), user.getZ(),
						user.getYaw(), user.getPitch());
			} else if (world.getDimensionKey() == RWorldDimensions.DIMENSION_TYPE_KEY) {
				player.teleport(
						world.getServer().getWorld(World.OVERWORLD), user.getX(), user.getY(), user.getZ(),
						user.getYaw(), user.getPitch());
			} else
				user.sendMessage(Text.of("Cannot use in this dimension..."), false);
		}

		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
