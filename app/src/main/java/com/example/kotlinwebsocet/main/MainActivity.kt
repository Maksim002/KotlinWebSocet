package com.example.kotlinwebsocet.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinwebsocet.R
import com.example.kotlinwebsocet.adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var webSocket: WebSocket
    private var SERVER_PATH = "wss://crm-api.molbulak.ru:2334/"
    private lateinit var sendBtn: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private var boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        sendBtn = findViewById(R.id.sendBtn)
        recyclerView = findViewById(R.id.recyclerView)
        messageAdapter = MessageAdapter(layoutInflater)
        recyclerView.setAdapter(messageAdapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        initiateSocketConnection()
    }

    private fun initiateSocketConnection() {
        val client = OkHttpClient()
        val request: Request = Request.Builder().url(SERVER_PATH).build()
        webSocket = client.newWebSocket(request, SocketListener())
    }

    private inner class SocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            runOnUiThread {
                Toast.makeText(
                    this@MainActivity,
                    "Socket Connection Successful!",
                    Toast.LENGTH_SHORT
                ).show()
                initializeView()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            runOnUiThread {
                try {
                    val jsonObject = JSONObject(text)
                    if (jsonObject != null) {
                        val jsonObject1 = JSONObject()
                        if (boolean) {
                            jsonObject1.put("method", "auth")
                            jsonObject1.put("id", "1")
                            jsonObject1.put("login", "143341")
                            jsonObject1.put("token", "123")
                            jsonObject1.put("dept", "3")
                            jsonObject1.put("type", "client")
                            webSocket.send(jsonObject1.toString())
                            boolean = false
                        }
                        if (jsonObject["method"].toString() == "send") {
                            jsonObject.put("isSent", false)
                            messageAdapter.addItem(jsonObject)
                            recyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun initializeView() {
        sendBtn.setOnClickListener {
            try {
                val jsonObject2 = JSONObject()

                val jsonObject = JSONObject()

                jsonObject2.put("to_id", "1")
                jsonObject2.put("to_type", "user")
                jsonObject2.put("msg", message_edit_text.text.toString())

                jsonObject.put("method", "send")
                jsonObject.put("id", "1")
                jsonObject.put("login", "143341")
                jsonObject.put("token", "123")
                jsonObject.put("dept", "3")
                jsonObject.put("type", "client")
                jsonObject.put("data", jsonObject2)

                webSocket.send(jsonObject.toString())
                jsonObject2.put("isSent", true)
                messageAdapter.addItem(jsonObject2)
                recyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}