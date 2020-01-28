package com.ankit.basechurch.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tithes(
    var id:String,
    var TitheAmount:String,
    var TitheDate:String,
    var TithesPid:String,
    var TithePayee:String
):Parcelable {
constructor():this("","","","","")
    companion object{
        fun toTithesHasMap(Tith: Tithes)= hashMapOf<String ,Any>(
            "id" to Tith.id,
            "titheAmount" to Tith.TitheAmount,
            "titheDate" to Tith.TitheDate,
            "tithePayee" to Tith.TithePayee
        )
    }
}