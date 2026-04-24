package bl4ckscor3.mod.easterrabbits;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class FabricEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerEntityEvents.ENTITY_LOAD.register((entity, _) -> EasterRabbits.onEntityJoinWorld(entity));
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, _) -> EasterRabbits.onLivingDeath(entity));
		ServerTickEvents.START_SERVER_TICK.register(_ -> EasterRabbits.onServerTick());
	}
}
