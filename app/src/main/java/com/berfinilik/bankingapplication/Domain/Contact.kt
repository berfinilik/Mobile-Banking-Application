package com.berfinilik.bankingapplication.Domain

import android.os.Parcel
import android.os.Parcelable
data class Contact(
    val name: String,
    val surname: String,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val picUrl: String,
    val iban: String,
    val uid:String
) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(gender)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(picUrl)
        parcel.writeString(iban)
        parcel.writeString(uid)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }
        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}