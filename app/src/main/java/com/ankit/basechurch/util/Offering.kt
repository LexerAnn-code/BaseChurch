package com.ankit.basechurch.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offering(
    var id:String,
    var OfferingAmount:String,
    var Date:String

):Parcelable{
    constructor():this("","","")
    companion object {
        fun toHashMap(Offers: Offering)= hashMapOf<String, Any?>(
            "date" to Offers.Date,
            "id" to Offers.id,
            "offeringAmount" to Offers.OfferingAmount
        )
    }
}