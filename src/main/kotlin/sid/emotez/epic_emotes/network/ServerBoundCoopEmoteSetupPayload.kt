package sid.emotez.epic_emotes.network

import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import sid.emotez.epic_emotes.EpicEmotez

class ServerBoundCoopEmoteSetupPayload(val entityId : Int) : CustomPacketPayload {

    companion object {
        val type: CustomPacketPayload.Type<ServerBoundCoopEmoteSetupPayload> = CustomPacketPayload.Type(
            ResourceLocation.fromNamespaceAndPath(
                EpicEmotez.ID, "server_bound_emote_setup_packet"
            )
        )

        @JvmField
        val STREAM_CODEC: StreamCodec<ByteBuf, ServerBoundCoopEmoteSetupPayload> = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ServerBoundCoopEmoteSetupPayload::entityId,
            ::ServerBoundCoopEmoteSetupPayload

        )

    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?> {
        return type
    }

}