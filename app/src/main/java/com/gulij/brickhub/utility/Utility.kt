package com.gulij.brickhub.utility

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

fun <T> downloadXMLObject(
    context: Context,
    url: String,
    parser: (String) -> T,
    onComplete: (T?) -> Unit
) {
    val queue = Volley.newRequestQueue(context)

    val request = StringRequest(
        Request.Method.GET, url,
        Response.Listener<String> { response ->
            onComplete.invoke(parser.invoke(response))
        },
        Response.ErrorListener { error ->
            Log.w("TAG", error.toString())
            onComplete.invoke(null)
        })

    queue.add(request)
}

