package be.chaidev.chronote.util

import android.util.Log
import be.chaidev.chronote.util.Constants.TAG
import org.json.JSONException
import org.json.JSONObject

class ErrorHandling{

    companion object{

        const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host"
        const val UNABLE_TODO_OPERATION_WO_INTERNET = "Can't do that operation without an internet connection"
        const val ERROR_CHECK_NETWORK_CONNECTION = "Check network connection."
        const val ERROR_UNKNOWN = "Unknown error"

        fun isNetworkError(msg: String): Boolean{
            when{
                msg.contains(UNABLE_TO_RESOLVE_HOST) -> return true
                else-> return false
            }
        }

        fun parseDetailJsonResponse(rawJson: String?): String{
            Log.d(TAG, "parseDetailJsonResponse: ${rawJson}")
            try{
                if(!rawJson.isNullOrBlank()){

                    return JSONObject(rawJson).get("detail") as String
                }
            }catch (e: JSONException){
                Log.e(TAG, "parseDetailJsonResponse: ${e.message}")
            }
            return ""
        }
    }
}