package bl4ckscor3.mod.easterrabbits;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

@Mod(modid="easterrabbits", name="Easter Rabbits", version="1.0")
@EventBusSubscriber
public class EasterRabbits
{
	public static final HashMap<EntityRabbit,Integer> TIME_UNTIL_NEXT_EGG = new HashMap<>();
	public static final Random RAND = new Random();
	private static final int FREQUENCY = 6000;

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityRabbit)
		{
			if(!TIME_UNTIL_NEXT_EGG.containsKey(event.getEntity()))
			{
				TIME_UNTIL_NEXT_EGG.put((EntityRabbit)event.getEntity(), RAND.nextInt(FREQUENCY) + FREQUENCY);
				return;
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if(event.getEntity() instanceof EntityRabbit)
			TIME_UNTIL_NEXT_EGG.remove(event.getEntity());
	}

	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event)
	{
		for(EntityRabbit rabbit : TIME_UNTIL_NEXT_EGG.keySet())
		{
			TIME_UNTIL_NEXT_EGG.put(rabbit, TIME_UNTIL_NEXT_EGG.get(rabbit) - 1);

			if(!rabbit.isChild() && TIME_UNTIL_NEXT_EGG.get(rabbit) <= 0)
			{
				rabbit.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (RAND.nextFloat() - RAND.nextFloat()) * 0.2F + 1.0F);
				rabbit.dropItem(Items.EGG, 1);
				TIME_UNTIL_NEXT_EGG.put(rabbit, RAND.nextInt(FREQUENCY) + FREQUENCY);
			}
		}
	}
}
