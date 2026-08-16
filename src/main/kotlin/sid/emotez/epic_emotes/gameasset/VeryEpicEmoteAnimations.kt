package sid.emotez.epic_emotes.gameasset


import com.lowdragmc.photon.Photon
import com.lowdragmc.photon.client.fx.EntityEffectExecutor
import com.lowdragmc.photon.client.fx.FX
import com.lowdragmc.photon.client.fx.FXHelper
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModList
import net.neoforged.fml.common.EventBusSubscriber
import sid.api.JointTrackedEntityEffect
import sid.emotez.epic_emotes.EpicEmotez
import sid.emotez.epic_emotes.utils.FxUtils.Vector3f_0
import sid.emotez.epic_emotes.utils.FxUtils.Vector3f_1
import yesman.epicfight.api.animation.AnimationManager
import yesman.epicfight.api.animation.property.AnimationEvent
import yesman.epicfight.api.animation.property.AnimationEvent.Side
import yesman.epicfight.api.animation.types.EmoteAnimation
import yesman.epicfight.api.utils.math.Vec3f
import yesman.epicfight.gameasset.Armatures
import yesman.epicfight.model.armature.HumanoidArmature


@Suppress("unused")
@EventBusSubscriber(modid = EpicEmotez.ID)
class VeryEpicEmoteAnimations {


    companion object {

        @JvmStatic
        @SubscribeEvent
        fun registerAnimations(event: AnimationManager.AnimationRegistryEvent) {
            event.newBuilder(EpicEmotez.ID, VeryEpicEmoteAnimations::buildAnimation)
        }

        @JvmStatic
        lateinit var GOMEN_AMANAI: AnimationManager.AnimationAccessor<EmoteAnimation>


        @JvmStatic
        fun buildAnimation(builder: AnimationManager.AnimationBuilder) {

            val biped: Armatures.ArmatureAccessor<HumanoidArmature> = Armatures.BIPED

            GOMEN_AMANAI = builder.nextAccessor("biped/emote/gomen_amanai") { emoteAnimationAnimationAccessor ->
                EmoteAnimation(0.1f, true, emoteAnimationAnimationAccessor, biped)
                    .addEvents(

                        AnimationEvent.InTimeEvent.create(
                            0.01f,
                            AnimationEvent.E0 { e, s, p ->
                                if (ModList.get().isLoaded(Photon.MOD_ID)) {
                                    val fx: FX? = FXHelper.getFX(ResourceLocation.parse("photon:amanai"))
                                    val executor = JointTrackedEntityEffect(
                                        fx,
                                        e.original.level(),
                                        e.original,
                                        biped.get().rootJoint,
                                        Vec3f(0f,0f,0f),
                                        EntityEffectExecutor.AutoRotate.NONE,
                                        true
                                    )
                                    executor.setOffset(Vector3f_0)
                                    executor.setScale(Vector3f_1)
                                    executor.setRotation(0.0, 0.0, 0.0)
                                    executor.setAllowMulti(true)
                                    executor.setForcedDeath(false)
                                    executor.start()
                                }

                            }, Side.CLIENT

                        )


                    )


            }

        }


    }
}