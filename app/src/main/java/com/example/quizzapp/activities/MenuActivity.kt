package com.example.quizzapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzapp.Services.MyStompSessionHandler
import com.example.quizzapp.Services.NetModule

import kotlinx.android.synthetic.main.activity_menu.*

import org.springframework.messaging.converter.MappingJackson2MessageConverter

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import java.net.URL
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.antlr.runtime.misc.IntArray




class MenuActivity : AppCompatActivity(){

    private lateinit var webSocketClient : WebSocketClient

    private val URL = "ws://aqueous-everglades-60502.herokuapp.com/websocket/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.quizzapp.R.layout.activity_menu)

        setUpOnClick()
    }

    private fun setUpOnClick(){

        newGameCardView.setOnClickListener{

            Thread {
                testWebSocet()
                runOnUiThread {
                }
            }.start()

            NetModule.newLobby()

        }
    }

    private fun testWebSocet() {
        val client = StandardWebSocketClient()

        val stompClient = WebSocketStompClient(client)
        stompClient.messageConverter = MappingJackson2MessageConverter()

        val sessionHandler = MyStompSessionHandler()
        stompClient.connect(URL, sessionHandler)

        Scanner(System.`in`).nextLine()
    }

//    private fun testWebSocet() {
//        val uri: URI
//        try {
//            // Connect to local host
//            uri = URI("ws://aqueous-everglades-60502.herokuapp.com/websocket")
//        } catch (e: URISyntaxException) {
//            e.printStackTrace()
//            return
//        }
//
//        webSocketClient = object : WebSocketClient(uri) {
//            override fun onOpen() {
//                Log.i("WebSocket", "Session is starting");
//                webSocketClient.send("Hello World!");
//            }
//
//            override fun onTextReceived(message: String?) {
//                Log.i("WebSocket", "Message received $message"  );
//            }
//
//            override fun onPongReceived(data: ByteArray?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onException(e: Exception?) {
//               Log.d("WebSocket",e.toString())
//            }
//
//            override fun onCloseReceived() {
//                Log.i("WebSocket", "Closed ");
//                System.out.println("onCloseReceived");
//            }
//
//            override fun onBinaryReceived(data: ByteArray?) {
//                Log.i("WebSocket", "BinaryReciver ");
//
//            }
//
//            override fun onPingReceived(data: ByteArray?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        }
//        webSocketClient.setConnectTimeout(10000)
//        webSocketClient.setReadTimeout(60000)
//        webSocketClient.enableAutomaticReconnection(5000)
//        webSocketClient.connect()
//    }
}

