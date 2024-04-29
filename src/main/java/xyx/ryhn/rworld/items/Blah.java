package xyx.ryhn.rworld.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Blah extends Item {
	public Blah(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.playSound(SoundEvents.ENTITY_SHEEP_AMBIENT, 1.0F, 1.0F);
		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
