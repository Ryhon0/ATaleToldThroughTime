package xyz.ryhn.tale;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
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

		PlayerSwimCallback.EVENT.register(Main::onPlayerSwim);
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