package sid.emotez.epic_emotes.network

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.neoforged.neoforge.network.registration.HandlerThread
import sid.emotez.epic_emotes.EpicEmotez

@EventBusSubscriber(modid = EpicEmotez.ID)
object NetworkManage {
    private const val PROTOCOL_VERSION = "1"


    //hate this boilerplate

    @SubscribeEvent
    fun register(event: RegisterPayloadHandlersEvent) {
        val registrar = event.registrar(PROTOCOL_VERSION)
            .executesOn(HandlerThread.NETWORK)

        registrar.playToServer(
            ServerBoundCoopEmoteSetupPayload.type,
            ServerBoundCoopEmoteSetupPayload.STREAM_CODEC,
            ServerSidePayloadHandle::handleSentEmPacket
        )
    }


}