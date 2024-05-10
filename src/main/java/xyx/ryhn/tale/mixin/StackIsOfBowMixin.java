package xyx.ryhn.tale.mixin;

import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class StackIsOfBowMixin {
	@Inject(at = @At("TAIL"), method = "isOf", cancellable = true)
	private void isOf(Item i, CallbackInfoReturnable<Boolean> cir) {
		ItemStack stack = (ItemStack)(Object)this;
		if(i == Items.CROSSBOW)
		{
			cir.setReturnValue(stack.getItem() instanceof CrossbowItem);
		}
		else if(i == Items.BOW)
		{
			cir.setReturnValue(stack.getItem() instanceof BowItem);
		}
	}
}