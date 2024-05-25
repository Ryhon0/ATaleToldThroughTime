package xyz.ryhn.tale;

import java.util.List;

import java.util.ArrayList;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class XPCraftingRecipe {
	public static ArrayList<XPCraftingRecipe> Recipes = new ArrayList<>();

	public static void addRecipe(XPCraftingRecipe r) {
		Recipes.add(r);
	}

	public static boolean tryCraftAny(PlayerEntity player, World w, BlockPos pos, List<ItemEntity> items) {
		for (XPCraftingRecipe r : Recipes) {
			if (r.tryCraft(player, w, pos, items))
				return true;
		}

		return false;
	}

	public List<Ingredient> ingredients;
	public int experience;
	public Item output;
	public int outputAmount;

	public boolean tryCraft(PlayerEntity player, World w, BlockPos pos, List<ItemEntity> items) {
		items = new ArrayList<>(items);
		if (items.size() == 0)
			return false;

		if (player.totalExperience < experience)
			return false;

		List<ItemEntity> candidates = new ArrayList<>() {
		};
		for (Ingredient i : ingredients) {
			boolean found = false;
			for (ItemEntity ie : items) {
				ItemStack s = ie.getStack();
				if (i.ingredient.test(s) && s.getCount() >= i.count) {
					candidates.add(ie);
					items.remove(ie);
					found = true;
					break;
				}
			}
			if (!found)
				return false;
		}

		for (int i = 0; i < ingredients.size(); i++) {
			ItemEntity ie = candidates.get(i);
			ie.getStack().setCount(ie.getStack().getCount() - ingredients.get(i).count);
		}

		player.addExperience(-experience);

		ItemStack stack = output.getDefaultStack();
		if (!player.giveItemStack(stack)) {
			ItemEntity ie = player.dropItem(stack, false);
			if (ie == null) {
				player.sendMessage(Text.literal("Failed to create drop!"));
				return false;
			}
			ie.resetPickupDelay();
			ie.setOwner(player.getUuid());
		}

		return true;
	}

	public static class Ingredient {
		public net.minecraft.recipe.Ingredient ingredient;
		public int count;

		public Ingredient(net.minecraft.recipe.Ingredient ind, int count) {
			this.ingredient = ind;
			this.count = count;
		}
	}

	public static class Builder {
		public ArrayList<Ingredient> ingredients = new ArrayList<>();

		public Builder() {
		}

		public Builder requireTag(TagKey<Item> t) {
			return requireTag(t, 1);
		}

		public Builder requireTag(TagKey<Item> t, int count) {
			ingredients.add(new Ingredient(net.minecraft.recipe.Ingredient.fromTag(t), count));
			return this;
		}

		public Builder requireItem(Item i) {
			return requireItem(i, 1);
		}

		public Builder requireItem(Item i, int count) {
			ingredients.add(new Ingredient(net.minecraft.recipe.Ingredient.ofItems(i), count));
			return this;
		}

		public XPCraftingRecipe build(int xp, Item output) {
			return build(xp, output, 1);
		}

		public XPCraftingRecipe build(int xp, Item output, int outputAmount) {
			XPCraftingRecipe recipe = new XPCraftingRecipe();
			recipe.ingredients = ingredients;
			recipe.experience = xp;
			recipe.output = output;
			recipe.outputAmount = outputAmount;
			return recipe;
		}
	}
}