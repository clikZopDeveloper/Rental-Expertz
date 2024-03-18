package com.stpl.antimatter.Utils

import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

public class ApiContants {
    companion object {
        var isconnectedtonetwork = false

        const val EmailAddress = "hello.com"
        const val REQ_CODE_VERSION_UPDATE = 530
        const val PlaceLocation = "location"
        const val mobileNumber = "mobileNumber"
        const val password = "password"
        const val PlaceRegion = "locationCountry"
        const val PlaceLatLang = "locLatLang"
        const val PlaceLat = "locLat"
        const val PlaceLang = "PlaceLang"
        val WhatsAppNumber = "+91****************"
        const val PREF_IS_METRIC = "unit"
        const val UserDetails = "userDetails"
        const val UserAvailableAmt = "useravailablebal"
        const val DeviceToken = "321"
        const val AccessToken = "accessToken"
        const val Type = "android"
        const val currency = "₹"
        const val dayStatus = "dayStatus"

///////////////////Testing URL////////////////////////////////////////
           const val BaseUrl="https://rental.leadsexpertz.com//api/"//Testing URL
           const val ImageBaseUrl = "https://www.art.antimatterfit.com/"//Testing URL
           const val ImageBaseUrlWhyChoose = "https://www.art.antimatterfit.com/public/images/"//Testing URL
           const val VideoBaseUrl = "https://www.art.antimatterfit.com/"//Testing URL


        const val success = "success"
        const val failure = "failure"
        const val NoInternetConnection = "Please check your internet connection"
        const val privacyUrl = "http://antimatterfit.com/privacy-policy"
        const val returnPolicyUrl = "http://antimatterfit.com/return-policy/"
        const val termsUrl = "http://antimatterfit.com/terms-condition"
        const val downloadUrl = "https://atulautomotive.online/"
        //        api Tags
        const val login = "login"
        const val logout = "logout"
        const val getCity = "get-city"
        const val getState = "get-state"


        ////////////////New//////////////
        const val getAddLead = "add-lead"
        const val getUpdateLead = "update-lead"
        const val getCampigns = "get-campigns"
        const val getType = "get-type"
        const val getCategory = "get-category"
        const val getSubCategory = "get-sub-category"
        const val getSource = "get-source"
        const val getClassification = "get-classification"
        const val getProject = "get-project"

/////////////////////////////////////////////
        const val dashboard = "dashboard"
        const val startDay = "start-day"
        const val endDay = "end-day"
        const val GetStatus = "get-status"
        const val AllLeadData = "lead-data"
        const val LeadDetail = "get-lead-data"
        const val UpdateLead = "update-status"
        const val getSendLead = "send-lead"
        const val getInsertRFQ = "insert-rfq"
        const val DeleteProductData = "delete-product"
        const val RequestQuote = "request-quote"
        const val getLocationUpdate = "location-update"
        const val getNotification = "location-update"
        const val getProfile = "get-profile"
        const val getPasswordChange = "change-password"
      fun nameFirstLatter(text:String,textPrint:TextView){
        //  val str = "Hello World"
          val firstChar = text.first()
          textPrint.text=firstChar.toString()
      }
    }


}