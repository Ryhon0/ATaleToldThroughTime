package xyz.ryhn.tale.items.gear.trinkets;

import java.util.UUID;

import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TeamBand extends TrinketItem implements DyeableItem {
	public static final Item ITEM = new TeamBand(new Item.Settings());

	public TeamBand(Settings settings) {
		super(settings);
	}
	
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot,
			LivingEntity entity, UUID uuid) {

		return super.getModifiers(stack, slot, entity, uuid);
	}
}
