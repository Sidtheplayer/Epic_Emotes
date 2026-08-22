package sid.emotez.epic_emotes.gameasset

import com.lowdragmc.photon.Photon
import com.lowdragmc.photon.client.fx.EntityEffectExecutor
import com.lowdragmc.photon.client.fx.FX
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModList
import net.neoforged.fml.common.EventBusSubscriber
import sid.api.JointTrackedEntityEffect
import sid.emotez.epic_emotes.EpicEmotez
import sid.emotez.epic_emotes.api.animation.types.MultiPlayerEmoteAnimation
import sid.emotez.epic_emotes.utils.FxUtils.Vector3f_0
import sid.emotez.epic_emotes.utils.FxUtils.Vector3f_1
import sid.emotez.epic_emotes.utils.FxUtils.getFx
import yesman.epicfight.api.animation.AnimationManager
import yesman.epicfight.api.animation.property.AnimationEvent
import yesman.epicfight.api.animation.property.AnimationEvent.Side
import yesman.epicfight.api.animation.types.EmoteAnimation
import yesman.epicfight.api.animation.types.EntityState
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

        lateinit var GOMEN_AMANAI: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var NEARING_ENLIGHTENMENT: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var BREAKDOWN: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var CHOSO_SPIN: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var CONGA: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var CRY: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var DAMNIT: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var DEFAULT_DANCE: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var ERRATIC_SHIVERING: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var FIRE_SIT_1: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var FIRE_SIT_2: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var FIRE_SIT_3: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var GOMEN_AMANAI_HIGH: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var HAKARI_DANCE_LOW_QUALITY: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var LEVITATING_LOTUS_POSITION: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var LOTUS_POSITION: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var NEPHOPHILIA: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var NO: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var PAGE_FLIP_READ_BOOK: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var POINT: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var RAND_M: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var READ_BOOK_STILL: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var REST_POSE: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var RICKROLL: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var RUB_HEAD: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var STRETCH_LEFT: AnimationManager.AnimationAccessor<EmoteAnimation>
        lateinit var TRUE_FIRE_SIT: AnimationManager.AnimationAccessor<EmoteAnimation>

        lateinit var TEST_COOP_EMOTE: AnimationManager.AnimationAccessor<MultiPlayerEmoteAnimation>



        @JvmStatic
        fun buildAnimation(builder: AnimationManager.AnimationBuilder) {

            val biped: Armatures.ArmatureAccessor<HumanoidArmature> = Armatures.BIPED


            TEST_COOP_EMOTE = builder.nextAccessor("biped/emote/cry") {accessor -> MultiPlayerEmoteAnimation(
                0.1f, true, accessor,biped,5.15f,2.750,DEFAULT_DANCE,BREAKDOWN
            ) }


            DEFAULT_DANCE = builder.nextAccessor("biped/emote/defaultdance"){ac ->
                EmoteAnimation(0.1f,false,ac,biped)
            }


            BREAKDOWN = builder.nextAccessor("biped/emote/breakdown") { accessor ->
                EmoteAnimation(0.1f, accessor,biped)
            }




            GOMEN_AMANAI = builder.nextAccessor("biped/emote/gomen_amanai") { emoteAnimationAnimationAccessor ->
               EmoteAnimation(0.1f, true, emoteAnimationAnimationAccessor, biped)


                   .addState<Boolean, EmoteAnimation>(EntityState.MOVEMENT_LOCKED, false)
                   .addState<Boolean, EmoteAnimation>(EntityState.INACTION, true)
                   .addState<Boolean, EmoteAnimation>(EntityState.UPDATE_LIVING_MOTION, false)


                    .addEvents(

                        AnimationEvent.InTimeEvent.create(
                            0.01f,
                            AnimationEvent.E0 { e, s, p ->
                                if (ModList.get().isLoaded(Photon.MOD_ID)) {
                                    val fx: FX = getFx("photon:amanai") ?: return@E0
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