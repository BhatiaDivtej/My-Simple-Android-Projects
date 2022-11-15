package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var btn_add: Button? = null
    private var text_name: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add = findViewById(R.id.btn_add)
        text_name = findViewById(R.id.txt_name)

        btn_add!!.setOnClickListener {
            val name:String = text_name!!.text.toString()
            sendMessage(name)
        }
    }

    fun sendMessage(name:String) {
        val url:String = "http://10.66.119.152:5000/tutorial" + "?name=" + name

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                switchActivity(response)
            },
            Response.ErrorListener { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    fun switchActivity(jsonObj: JSONObject){
        val jsonArray: JSONArray = jsonObj.get("students") as JSONArray
        val studentList = arrayListOf<String>()

        for (i in 0..jsonArray.length() - 1){
            studentList.add(jsonArray.get(i) as String)
        }

        val intent = Intent(this, PersonListActivity::class.java).apply {
            putStringArrayListExtra("data", studentList)
        }
        startActivity(intent)
    }
}