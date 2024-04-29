package xyx.ryhn.rworld.items.gear;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;

public class ScopedCrossbow extends CrossbowItem {
	public static final CrossbowItem ITEM = new ScopedCrossbow(new Item.Settings());
	
	public ScopedCrossbow(Settings settings) {
		super(settings);
	}
}
