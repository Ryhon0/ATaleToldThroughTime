package xyz.ryhn.tale;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import xyz.ryhn.tale.entities.mobs.animals.Dirtpecker;
import xyz.ryhn.tale.entities.mobs.animals.GoldenChicken;
import xyz.ryhn.tale.entities.mobs.humans.Citizen;
import xyz.ryhn.tale.items.TaleItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.florens.expandability.api.fabric.PlayerSwimCallback;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("tale");

	@Override
	public void onInitialize() {
		TaleItems.registerItems();

		FabricDefaultAttributeRegistry.register(Citizen.ENTITY_TYPE, Citizen.createMobAttributes());
		FabricDefaultAttributeRegistry.register(GoldenChicken.ENTITY_TYPE, GoldenChicken.createChickenAttributes());
		FabricDefaultAttributeRegistry.register(Dirtpecker.ENTITY_TYPE, ZombieEntity.createZombieAttributes());

		PlayerSwimCallback.EVENT.register(Main::onPlayerSwim);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			for (Enchantment e : Registries.ENCHANTMENT) {
				content.add(TaleItems.UNSEALED_ENCHANTED_BOOK.of(e));

				if(e.getMaxLevel() > 1)
				{
					content.add(TaleItems.OVERFLOWING_UNSEALED_ENCHANTED_BOOK.of(e));
					content.add(TaleItems.AWAKENED_UNSEALED_ENCHANTED_BOOK.of(e));
				}
			}
		});
	}

	public static TriState onPlayerSwim(PlayerEntity player) {
		if (player.getWorld().getDimensionKey() == TaleDimensions.WHITESPACE_DIMENSION_TYPE_KEY &&
				!player.isCreative() && !player.isOnGround() && !player.isSneaking() &&
				(player.isSprinting() || player.isInSwimmingPose()))
			return TriState.TRUE;
		return TriState.DEFAULT;
	}

	public static Identifier Key(String id) {
		return new Identifier("ataletoldthroughtime", id);
	}
}