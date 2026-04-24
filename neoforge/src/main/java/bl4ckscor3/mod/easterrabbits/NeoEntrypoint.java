package bl4ckscor3.mod.easterrabbits;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@Mod(EasterRabbits.MODID)
@EventBusSubscriber
public class NeoEntrypoint {
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		EasterRabbits.onEntityJoinWorld(event.getEntity());
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		EasterRabbits.onLivingDeath(event.getEntity());
	}

	@SubscribeEvent
	public static void onServerTick(ServerTickEvent.Pre event) {
		EasterRabbits.onServerTick();
	}
}
