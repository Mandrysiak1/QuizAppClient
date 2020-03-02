package com.example.quizzapp.Services

import android.service.notification.NotificationListenerService
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.activities.RankingStatic
import com.example.quizzapp.objects.Responses.GameState
import com.example.quizzapp.objects.Responses.InGameRanking
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object WebSocketModule {




    private val mTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    private var mStompClient: StompClient? = null

    private var mRestPingDisposable: Disposable? = null

    private var compositeDisposable: CompositeDisposable? = null

    private val mGson = GsonBuilder().create()

    val stateGame: MutableLiveData<GameState> by lazy {
        MutableLiveData<GameState>()
    }

    var ranking: HashMap<String,Int>? = HashMap()

    val  hasStarted : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


    init {

        mStompClient = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,"wss://aqueous-everglades-60502.herokuapp.com/websocket")

        resetSubscriptions()
    }

    fun disconnectStomp() {
        mStompClient?.disconnect()
    }

    private fun resetSubscriptions() {

            compositeDisposable?.dispose()
            compositeDisposable = CompositeDisposable()
    }



    fun connectStomp() {

//        val headers = ArrayList<StompHeader>()
//        headers.add(StompHeader(LOGIN, "guest"))
//        headers.add(StompHeader(PASSCODE, "guest"))

        mStompClient?.withClientHeartbeat(1000)?.withServerHeartbeat(1000)

        resetSubscriptions()

        val dispLifecycle = mStompClient?.lifecycle()?.subscribeOn(Schedulers.io())?.observeOn(
            AndroidSchedulers.mainThread())
            ?.subscribe { lifecycleEvent ->
                when (lifecycleEvent.getType()) {
                    LifecycleEvent.Type.OPENED ->Log.e("ws", "OPENED", lifecycleEvent.exception)
                    LifecycleEvent.Type.ERROR -> {
                        Log.e("ws", "Stomp connection error", lifecycleEvent.exception)
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        resetSubscriptions()
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.e("ws", "FAILED_SERVER_HEARTBEAT", lifecycleEvent.exception)

                }
            }

        if (dispLifecycle != null) {
            compositeDisposable?.add(dispLifecycle)
        }

        mStompClient?.connect()
    }


    fun subscribetToGame(gameID: String )
    {
        resetSubscriptions()

        val dispTopic = mStompClient?.topic("/topic/games/$gameID" )?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ topicMessage ->

                Log.d("ws", "Received " + topicMessage.payload)

                var x = mGson.fromJson<Any>(topicMessage.payload, GameState::class.java) as GameState
                x.ranking?.ranking?.values?.elementAt(0)?.let {

                    RankingStatic.x = HashMap(x.ranking!!.ranking)
                }
                hasStarted.value = true

                ranking = x.ranking?.ranking

                stateGame.value = x



            }, {

                    throwable -> Log.e("ws", "Error on subscribe topic", throwable) })

        if (dispTopic != null) {
            compositeDisposable?.add(dispTopic)
        }

       // mStompClient?.connect()
    }


    fun sendEchoViaStomp(gameID : String) {
        //        if (!mStompClient.isConnected()) return;
        mStompClient?.send(
            "/topic/games/$gameID",
            "Echo STOMP " + mTimeFormat.format(Date())
        )?.compose(applySchedulers())?.subscribe({ Log.d("ws", "STOMP echo send successfully") }, { throwable ->
            Log.e("ws", "Error send STOMP echo", throwable)
            throwable.message?.let { Log.e("ws",it) }
        })?.let {
            compositeDisposable?.add(
                it
            )
        }
    }

    private fun applySchedulers(): CompletableTransformer {
        return CompletableTransformer{ it->
            it.unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    }



}


