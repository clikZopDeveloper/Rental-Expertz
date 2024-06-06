package com.stpl.antimatter.Utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.rentalexpertz.Activity.AddLeadActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        const val addVoiceNote = "add-voice-note"

///////////////////Testing URL////////////////////////////////////////
           const val BaseUrl="https://rental.clikzopdevp.com/api/"//Testing URL

        const val success = "success"
        const val failure = "failure"
        const val NoInternetConnection = "Please check your internet connection"
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
        const val SearchLead = "search-lead"
        const val GetTaskList = "task-list"
        const val GetUpdateTask = "update-task"
        const val startDay = "start-day"
        const val endDay = "end-day"
        const val GetAddTask = "add-task"
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

///////////////////////////////Call Upload Image/////////////////

        fun uploadImage(activity: Activity, SELECT_PICTURES: Int) {
            if (Build.VERSION.SDK_INT < 19) {
                var intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                activity.startActivityForResult(
                    Intent.createChooser(intent, "Choose Pictures"), SELECT_PICTURES
                )
            } else { // For latest versions API LEVEL 19+
                var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                activity.startActivityForResult(intent, SELECT_PICTURES);
            }
        }
        ////////////////////////
        fun ClickPicCamera(acivity: Activity, CAMERA_PERMISSION_CODE: Int) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            acivity.startActivityForResult(intent, CAMERA_PERMISSION_CODE)
        }

        fun requestCameraPermission(activity: Activity, PERMISSION_CODE: Int) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES
                ),
                PERMISSION_CODE
            )
        }


        ///////////////////Moveable Button//////////

        fun movabalebutton(view: FloatingActionButton, context: Activity?) {
            var dX = 0.0f
            var dY = 0.0f
            var downRawX = 0f
            val CLICK_DRAG_TOLERANCE = 10f
             var downRawY:kotlin.Float = 0f
            view.setOnTouchListener(OnTouchListener { v, motionEvent ->
                val layoutParams = view.layoutParams as MarginLayoutParams
                val action = motionEvent.action
                if (action == MotionEvent.ACTION_DOWN) {
                    downRawX = motionEvent.rawX
                    downRawY = motionEvent.rawY
                    dX = view.x - downRawX
                    dY = view.y - downRawY
                    return@OnTouchListener true // Consumed
                } else if (action == MotionEvent.ACTION_MOVE) {
                    val viewWidth = view.width
                    val viewHeight = view.height
                    val viewParent = view.parent as View
                    val parentWidth = viewParent.width
                    val parentHeight = viewParent.height
                    var newX: Float = motionEvent.rawX + dX
                    newX = Math.max(
                        layoutParams.leftMargin.toFloat(),
                        newX
                    ) // Don't allow the FAB past the left hand side of the parent
                    newX = Math.min(
                        (parentWidth - viewWidth - layoutParams.rightMargin).toFloat(),
                        newX
                    ) // Don't allow the FAB past the right hand side of the parent
                    var newY: Float = motionEvent.rawY + dY
                    newY = Math.max(
                        layoutParams.topMargin.toFloat(),
                        newY
                    ) // Don't allow the FAB past the top of the parent
                    newY = Math.min(
                        (parentHeight - viewHeight - layoutParams.bottomMargin).toFloat(),
                        newY
                    ) // Don't allow the FAB past the bottom of the parent
                    view.animate()
                        .x(newX)
                        .y(newY)
                        .setDuration(0)
                        .start()
                    return@OnTouchListener true // Consumed
                } else if (action == MotionEvent.ACTION_UP) {
                    val upRawX = motionEvent.rawX
                    val upRawY = motionEvent.rawY
                    val upDX: Float = upRawX - downRawX
                    val upDY: Float = upRawY - downRawY
                    if (Math.abs(upDX) <CLICK_DRAG_TOLERANCE && Math.abs(
                            upDY
                        ) <CLICK_DRAG_TOLERANCE
                    ) { // A click
                        context?.startActivity(Intent(context, AddLeadActivity::class.java).putExtra("way","Add Lead"))

                        // openWhatsApp(phoneNumber, context)
                        return@OnTouchListener true
                    } else { // A drag
                        return@OnTouchListener true // Consumed
                    }
                }
                false
            })
        }

    }


}