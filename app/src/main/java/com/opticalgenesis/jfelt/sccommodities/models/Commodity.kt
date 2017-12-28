package com.opticalgenesis.jfelt.sccommodities.models

import android.os.Parcel
import android.os.Parcelable


data class Commodity(var name: String? = null, var price: Double? = null, var type: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(type)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Commodity> {
        override fun createFromParcel(parcel: Parcel) = Commodity(parcel)

        override fun newArray(size: Int): Array<Commodity?> = arrayOfNulls(size)
    }
}