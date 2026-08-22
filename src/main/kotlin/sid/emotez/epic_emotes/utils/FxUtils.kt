package sid.emotez.epic_emotes.utils

import com.lowdragmc.photon.client.fx.FX
import com.lowdragmc.photon.client.fx.FXHelper
import com.lowdragmc.photon.client.fx.FXRuntime
import com.lowdragmc.photon.client.gameobject.IFXObject
import net.minecraft.resources.ResourceLocation
import org.joml.Vector3f

object FxUtils {

    var Vector3f_0 = Vector3f(0f,0f,0f)
    var Vector3f_1 = Vector3f(1f,1f,1f)





    @Suppress("unused")
    object InjectionUtils{


        fun injectScale(runtime: FXRuntime, scale: Vector3f, name: String) {


            val fxObject: IFXObject = runtime.findObject(name) ?: return

            fxObject.updateScale(scale)

        }


    }

    fun getFx (resourceLoc: String): FX? {
        return FXHelper.getFX(ResourceLocation.parse(resourceLoc))
    }


    data class Skull (
        var sfk : Float = 5f
    )


    fun getFx (rs : Float): Skull {
        return Skull(
            sfk = rs

        )
    }



}