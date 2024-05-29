package com.example.hide
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.common.util.concurrent.Service
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class DeviceManager {
    companion object {
        fun postRegistrarDispositivoEnServidor(token: String, context: Context) {
            // Instantiate the RequestQueue.
            val queue: RequestQueue = Volley.newRequestQueue(context)
            val url = "url_servidor"

            // Request a string response from the provided URL.
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    try {
                        val respObj = JSONObject(response)
                        val code = respObj.getString("code")
                        val message = respObj.getString("message")
                        val id = respObj.getInt("id")
                        if ("OK" == code) {
                            if (id != 0) {
                                context.getSharedPreferences("SP_FILE", 0)
                                    .edit().putInt("ID", id).apply()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        context,
                        "Error registrando token en servidor: " + error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                override fun getParams():Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["DEVICEID"] = context.getSharedPreferences("SP_FILE", 0)
                        .getString("DEVICEID", null) ?: ""
                    val id = context.getSharedPreferences("SP_FILE", 0)
                        .getInt("ID", 0)
                    if (id != 0) {
                        params["ID"] = id.toString()
                    }
                    return params
                }
            }
            queue.add(stringRequest)
        }
    }
}