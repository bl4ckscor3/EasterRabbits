package bl4ckscor3.mod.easterrabbits;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.item.Items;

public class EasterRabbits {
	public static final String MODID = "easterrabbits";
	public static final Map<Rabbit, Integer> TIME_UNTIL_NEXT_EGG = new HashMap<>();
	public static final Random RAND = new Random();
	private static final int FREQUENCY = 6000;

	public static void onEntityJoinWorld(Entity entity) {
		if (entity instanceof Rabbit rabbit && !TIME_UNTIL_NEXT_EGG.containsKey(rabbit))
			TIME_UNTIL_NEXT_EGG.put(rabbit, FREQUENCY);
	}

	public static void onLivingDeath(Entity entity) {
		if (entity instanceof Rabbit rabbit)
			TIME_UNTIL_NEXT_EGG.remove(rabbit);
	}

	public static void onServerTick() {
		for (Rabbit rabbit : TIME_UNTIL_NEXT_EGG.keySet()) {
			TIME_UNTIL_NEXT_EGG.put(rabbit, TIME_UNTIL_NEXT_EGG.get(rabbit) - 1);

			if (!rabbit.isBaby() && rabbit.isAlive() && TIME_UNTIL_NEXT_EGG.get(rabbit) <= 0) {
				rabbit.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (RAND.nextFloat() - RAND.nextFloat()) * 0.2F + 1.0F);
				rabbit.spawnAtLocation((ServerLevel) rabbit.level(), Items.EGG);
				TIME_UNTIL_NEXT_EGG.put(rabbit, RAND.nextInt(FREQUENCY) + FREQUENCY);
			}
		}
	}
}
