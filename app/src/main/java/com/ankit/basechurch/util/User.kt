package com.ankit.basechurch.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid:String,
    var userFirstName:String,
    var userLastName:String,
    var userPhone:String?=null,
    var icon:String?=null

):Parcelable{
    constructor():this ("","","","","")

}
