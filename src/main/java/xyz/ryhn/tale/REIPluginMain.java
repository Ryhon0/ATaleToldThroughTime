package xyz.ryhn.tale;
/*
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.ryhn.tale.xpcrafting.XPCraftingRecipe;

public class RREIPlugin implements REIClientPlugin {
	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new Category());
		registry.addWorkstations(Category.DISPLAY, EntryStacks.of(Blocks.CAULDRON));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
	}

	@Override
	public void registerScreens(ScreenRegistry registry) {

	}

	public static class Display extends BasicDisplay {
	
		public Display(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
			super(inputs, outputs);
		}

		public Display(XPCraftingRecipe recipe)
		{
			super(Display.getInputs(recipe), getOutput(recipe));
		}

		private static List<EntryIngredient> getInputs(XPCraftingRecipe recipe)
		{
			ArrayList<EntryIngredient> in = new ArrayList<>();
			for (XPCraftingRecipe.Ingredient i : recipe.ingredients) {
				// TODO: tags
				ItemStack s = i.ingredient.getMatchingStacks()[0];
				s.setCount(i.count);
				in.add(EntryIngredient.of(EntryStacks.of(s)));
			}
			return in;
		}

		private static List<EntryIngredient> getOutput(XPCraftingRecipe recipe)
		{
			ItemStack stack = recipe.output.getDefaultStack();
			stack.setCount(recipe.outputAmount);
			return List.of(EntryIngredient.of(EntryStacks.of(stack)));
		}

		@Override
		public CategoryIdentifier<?> getCategoryIdentifier() {
			return Category.DISPLAY;
		}
	}
	
	public static class Category implements DisplayCategory<BasicDisplay> {
		public static final Identifier TEXTURE = Main.Key("textures/gui/cauldron_gui.png");
		public static final CategoryIdentifier<Display> DISPLAY = CategoryIdentifier.of(Main.Key("cauldron"));
		@Override
		public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
			return DISPLAY;
		}

		@Override
		public Text getTitle() {
			return Text.translatable("gui.tale.rei.category");
		}

		@Override
		public Renderer getIcon() {
			return EntryStacks.of(Blocks.CAULDRON);
		}

		@Override
		public int getDisplayHeight() {
			return 90;
		}

		@Override
		public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
			Point start = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
			List<Widget> widget = new LinkedList<>();

			// widget.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(start.x, start.y, 175, 82)));
			widget.add(Widgets.createSlot(new Point(start.x + 80, start.y + 11))
				.entries(display.getInputEntries().get(0)));
			widget.add(Widgets.createSlot(new Point(start.x + 90, start.y + 11))
				.entries(display.getInputEntries().get(1)));
			widget.add(Widgets.createSlot(new Point(start.x + 100, start.y + 11))
				.entries(display.getInputEntries().get(2)));

			widget.add(Widgets.createSlot(new Point(start.x + 100, start.y + 20))
				.markOutput().entries(display.getOutputEntries().get(0)));
		
				return widget;
		}
	}
}
*/