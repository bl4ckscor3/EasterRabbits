package bl4ckscor3.mod.easterrabbits;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod("easterrabbits")
@EventBusSubscriber
public class EasterRabbits
{
	public static final HashMap<Rabbit,Integer> TIME_UNTIL_NEXT_EGG = new HashMap<>();
	public static final Random RAND = new Random();
	private static final int FREQUENCY = 6000;

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		tryAddRabbit(event.getEntity());
	}

	private static void tryAddRabbit(Entity entity)
	{
		if(entity instanceof Rabbit rabbit)
		{
			if(!TIME_UNTIL_NEXT_EGG.containsKey(rabbit))
			{
				TIME_UNTIL_NEXT_EGG.put(rabbit, RAND.nextInt(FREQUENCY) + FREQUENCY);
				return;
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if(event.getEntity() instanceof Rabbit rabbit)
			TIME_UNTIL_NEXT_EGG.remove(rabbit);
	}

	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event)
	{
		for(Rabbit rabbit : TIME_UNTIL_NEXT_EGG.keySet())
		{
			TIME_UNTIL_NEXT_EGG.put(rabbit, TIME_UNTIL_NEXT_EGG.get(rabbit) - 1);

			if(!rabbit.isBaby() && rabbit.isAlive() && TIME_UNTIL_NEXT_EGG.get(rabbit) <= 0)
			{
				rabbit.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (RAND.nextFloat() - RAND.nextFloat()) * 0.2F + 1.0F);
				rabbit.spawnAtLocation(Items.EGG, 1);
				TIME_UNTIL_NEXT_EGG.put(rabbit, RAND.nextInt(FREQUENCY) + FREQUENCY);
			}
		}
	}
}
