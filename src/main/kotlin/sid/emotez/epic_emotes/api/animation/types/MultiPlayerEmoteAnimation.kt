package sid.emotez.epic_emotes.api.animation.types

import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.targeting.TargetingConditions
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.AABB
import yesman.epicfight.api.animation.AnimationManager
import yesman.epicfight.api.animation.types.DynamicAnimation
import yesman.epicfight.api.animation.types.EmoteAnimation
import yesman.epicfight.api.asset.AssetAccessor
import yesman.epicfight.api.model.Armature
import yesman.epicfight.world.capabilities.EpicFightCapabilities
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch
import java.util.function.Predicate


class MultiPlayerEmoteAnimation : EmoteAnimation {

    constructor(
        transitionTime: Float,
        loops: Boolean,
        accessor: AnimationManager.AnimationAccessor<out MultiPlayerEmoteAnimation>,
        armature: AssetAccessor<out Armature>,
        searchRadius : Float,
        forwardOffset : Double,
        otherPlayerEmote : AssetAccessor<EmoteAnimation>,
        onSuccessEmote: AssetAccessor<EmoteAnimation>
    ) : super(transitionTime, loops, accessor, armature) {
        this.forwardOffset = forwardOffset
        this.searchRadius = searchRadius
        this.onSuccessEmote = onSuccessEmote
        this.otherPlayerEmote = otherPlayerEmote
    }

    var otherPlayerEmote : AssetAccessor< EmoteAnimation>
    var onSuccessEmote : AssetAccessor< EmoteAnimation>
    var searchRadius : Float = 3.5F // fallback
    var forwardOffset = 2.5


    private var hasSucceeded = false

    private val otherSideIsReady: Predicate<LivingEntity> = Predicate { entity ->
        entity is Player &&
                entity.tags.contains("coop_emote_ready") &&
                entity.isAlive &&
                entity.onGround() &&
                !entity.isSpectator
    }


    override fun tick(entitypatch: LivingEntityPatch<*>?) {
        super.tick(entitypatch)

        if (entitypatch !is PlayerPatch<*>) return
        if (entitypatch.isLogicalClient) return
        if(hasSucceeded) return
        val playerPatch: PlayerPatch<*> = entitypatch

        val pos = playerPatch.original.position()

        val searchAABB = AABB(
            pos.x - searchRadius, pos.y - searchRadius, pos.z - searchRadius,
            pos.x + searchRadius, pos.y + searchRadius, pos.z + searchRadius
        )

        if(entitypatch.original.tags.contains("coop_emote_ready")){
            entitypatch.original.tags.remove("coop_emote_ready")
        }

        val playerList = playerPatch.original.level().getNearbyPlayers(
            TargetingConditions.forNonCombat().range(searchRadius.toDouble())
                .selector(otherSideIsReady),
            playerPatch.original,
            searchAABB
        )

        if(playerList.isNotEmpty()){

            val firstPlayer = playerList.firstOrNull()




            if(firstPlayer == null || firstPlayer == playerPatch.original)return

            hasSucceeded = true

            firstPlayer.tags.remove("coop_emote_ready")


            val tpPos =
                playerPatch.original.eyePosition.add(playerPatch.original.lookAngle.normalize().scale(forwardOffset))

            firstPlayer.teleportTo(tpPos.x, pos.y, tpPos.z)

            firstPlayer.lookAt(EntityAnchorArgument.Anchor.EYES, playerPatch.original.eyePosition)
            firstPlayer.yRot = firstPlayer.getYHeadRot()
            firstPlayer.setYBodyRot(firstPlayer.yRot)
            val coopPatch = EpicFightCapabilities.getPlayerPatch(firstPlayer)

            coopPatch?.setModelYRot(firstPlayer.yRot, true)

            //play animations null-safe, constructor animations cannot be empty anyway... lol

            onSuccessEmote.let { emote ->
                playerPatch.playAnimationSynchronized(emote, 0.1f)
            }

            otherPlayerEmote.let { e ->
                coopPatch?.playAnimationSynchronized(e, 0.1f)
            }


        }


    }

    override fun end(
        entitypatch: LivingEntityPatch<*>?,
        nextAnimation: AssetAccessor<out DynamicAnimation>?,
        isEnd: Boolean
    ) {

        super.end(entitypatch, nextAnimation, isEnd)

        hasSucceeded = false

        entitypatch?.let { patch ->
            if (patch is PlayerPatch<*>) {
                patch.original.tags.remove("coop_emote_ready")
            }
        }



    }


}