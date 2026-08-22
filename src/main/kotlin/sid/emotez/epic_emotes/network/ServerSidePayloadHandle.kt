package sid.emotez.epic_emotes.network

import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext
import sid.emotez.epic_emotes.EpicEmotez

object ServerSidePayloadHandle {

    @JvmStatic
    fun handleSentEmPacket(
        payload : ServerBoundCoopEmoteSetupPayload,
        context: IPayloadContext
    ) {

        val player = context.player()

        if (player !is ServerPlayer) {
            EpicEmotez.LOGGER.warn("received coop emote packet from non-server player")
            return
        }

        context.enqueueWork {
            handleCoopEmote(player)
        }




    }

    private fun handleCoopEmote(player: ServerPlayer) {
        try {

            // Toggle the co-op emote ready tag
            val tag = "coop_emote_ready"

            if (player.tags.contains(tag)) {

                player.tags.remove(tag)


            } else {

                player.tags.add(tag)

                EpicEmotez.LOGGER.info("{} is now ready for co-op emote", player.name.string)

                // Notify player of big foot nearby
                player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal(
                        "§aYou are now ready for co-op emotes! Looking for partners..."
                    ),
                    false
                )

            }

        } catch (e: Exception) {
            EpicEmotez.LOGGER.error("Error handling co-op emote setup for {}", player.name.string, e)
            // debug
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                    "§cError toggling co-op emote: ${e.message}"
                ),
                false
            )
        }
    }


    }