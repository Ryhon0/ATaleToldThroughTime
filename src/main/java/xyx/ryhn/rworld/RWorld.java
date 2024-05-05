package xyx.ryhn.rworld;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.util.TriState;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import xyx.ryhn.rworld.dimensions.RWorldDimensions;
import xyx.ryhn.rworld.entities.mobs.animals.GoldenChicken;
import xyx.ryhn.rworld.entities.mobs.humans.Citizen;
import xyx.ryhn.rworld.items.RWorldItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.florens.expandability.api.fabric.PlayerSwimCallback;

public class RWorld implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("rworld");

	@Override
	public void onInitialize() {
		RWorldItems.registerItems();

		CustomPortalBuilder.beginPortal()
				.destDimID(Key("rworld"))
				.frameBlock(Blocks.LAPIS_BLOCK)
				.lightWithItem(Items.CLOCK)
				.onlyLightInOverworld()
				.tintColor(0, 0, 255)
				.registerPortal();

		FabricDefaultAttributeRegistry.register(Citizen.ENTITY_TYPE, Citizen.createMobAttributes());
		FabricDefaultAttributeRegistry.register(GoldenChicken.ENTITY_TYPE, GoldenChicken.createChickenAttributes());

		PlayerSwimCallback.EVENT.register(RWorld::onPlayerSwim);
	}

	public static TriState onPlayerSwim(PlayerEntity player) {
		if (player.getWorld().getDimensionKey() == RWorldDimensions.WHITESPACE_DIMENSION_TYPE_KEY &&
				!player.isOnGround() && !player.isSneaking() &&
				(player.isSprinting() || player.isInSwimmingPose()))
			return TriState.TRUE;
		return TriState.DEFAULT;
	}

	public static Identifier Key(String id) {
		return new Identifier("rworld", id);
	}
}