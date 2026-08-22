package sid.emotez.epic_emotes.event

import net.minecraft.client.Minecraft
import net.minecraft.server.TickTask
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.event.tick.PlayerTickEvent
import net.neoforged.neoforge.network.PacketDistributor
import sid.emotez.epic_emotes.EpicEmotez
import sid.emotez.epic_emotes.network.NetworkManage
import sid.emotez.epic_emotes.network.ServerBoundCoopEmoteSetupPayload
import yesman.epicfight.EpicFight
import yesman.epicfight.client.input.EpicFightKeyMappings
import yesman.epicfight.network.EpicFightNetworkManager

@EventBusSubscriber(modid = EpicEmotez.ID)
object ModEvent {

    @EventBusSubscriber(modid = EpicEmotez.ID, value = [Dist.CLIENT])
    object ClientEvent{

        @SubscribeEvent
        fun onClientTick(event: ClientTickEvent.Post) {


            val mc = Minecraft.getInstance()
            val player = mc.player ?: return


            if (EpicFightKeyMappings.OPEN_EMOTE_WHEEL.consumeClick()) {
                PacketDistributor.sendToServer(ServerBoundCoopEmoteSetupPayload(player.id))
            }
        }

    }





}