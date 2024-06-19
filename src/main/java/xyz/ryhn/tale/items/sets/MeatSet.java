package xyz.ryhn.tale.items.sets;

import java.util.ArrayList;

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import xyz.ryhn.tale.items.TaleItems;

public class MeatSet  extends SetGenerator {
	public static ArrayList<MeatSet> Sets = new ArrayList<>();

	public Item RAW;
	public Item COOKED;

	// BLOCK
	// COOKED_BLOCK

	public MeatSet(String name, int hunger, float saturation) {
		super();

		RAW = TaleItems.registerItem(
				new Item(new Item.Settings().food(
						new FoodComponent.Builder()
								.meat()
								.hunger(hunger / 2)
								.saturationModifier(saturation / 2)
								.build())),
				"raw_" + name,
				ItemGroups.FOOD_AND_DRINK);

		COOKED = TaleItems.registerItem(
				new Item(new Item.Settings().food(
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
}
