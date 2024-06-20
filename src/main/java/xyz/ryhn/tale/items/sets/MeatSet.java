package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import xyz.ryhn.tale.items.TaleItems;

public class MeatSet  extends SetGenerator {
	public static ArrayList<MeatSet> Sets = new ArrayList<>();

	public MeatSetItem RAW;
	public MeatSetItem COOKED;

	// BLOCK
	// COOKED_BLOCK

	public Item REMAINDER;

	public MeatSet(String name, int hunger, float saturation) {
		super();

		RAW = TaleItems.registerItem(
				new MeatSetItem(new Item.Settings().food(
						new FoodComponent.Builder()
								.meat()
								.hunger(hunger / 2)
								.saturationModifier(saturation / 2)
								.build())),
				"raw_" + name,
				ItemGroups.FOOD_AND_DRINK);

		COOKED = TaleItems.registerItem(
				new MeatSetItem(new Item.Settings().food(
						new FoodComponent.Builder()
								.meat()
								.hunger(hunger)
								.saturationModifier(saturation)
								.build())),
				"cooked_" + name,
				ItemGroups.FOOD_AND_DRINK);
	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(RAW, Models.GENERATED);
		generator.register(COOKED, Models.GENERATED);
	}

	public MeatSet remainder(Item i)
	{
		REMAINDER = i;
		return this;
	}

	public class MeatSetItem extends Item
	{
		public MeatSetItem(Settings settings) {
			super(settings);
		}

		@Override
		public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
			stack = super.finishUsing(stack, world, user);

			ItemStack s = REMAINDER.getDefaultStack();
			if(stack.getCount() == 0)
				return s;
				
			if(user instanceof PlayerEntity pe)
			{
				if(pe.giveItemStack(s))
				{
					return stack;
				}
			}
			user.dropStack(s);
			return stack;
		}
	}
}
