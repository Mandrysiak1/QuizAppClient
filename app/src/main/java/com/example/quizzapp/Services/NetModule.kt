package com.example.quizzapp.Services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.UserResponse
import com.example.quizzapp.objects.*
import com.example.quizzapp.objects.Responses.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object  NetModule {

    val url : String = "https://aqueous-everglades-60502.herokuapp.com/api/"

    var username : String = ""

    var retrofit: Retrofit
    var client : OkHttpClient
    var serviceInterceptor : ServiceInterceptor = ServiceInterceptor()
    init {

         client = OkHttpClient.Builder()
        .addInterceptor(serviceInterceptor)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45,TimeUnit.SECONDS)
        .build()

        val builder = Retrofit.Builder().client(client)
                                    .baseUrl(url).addConverterFactory(GsonConverterFactory.create())

        retrofit = builder.build()
    }

    private val authService: AuthService = retrofit.create(AuthService::class.java)
    private val lobbyService: LobbyService = retrofit.create(LobbyService::class.java)
    private val gameService: GameService = retrofit.create(GameService::class.java)
    private val societyService: SocietyService= retrofit.create(SocietyService::class.java)
    private val questionService: QuestionService= retrofit.create(QuestionService::class.java)
    private val shopService: ShopService= retrofit.create(ShopService::class.java)


    fun getRankings(
        id: String,
        data: MutableLiveData<RankingResponse>
    )
    {
        societyService.getRankings(LeaveSocietyBody(id, username)).enqueue(object : Callback<RankingResponse> {

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
              Log.d("xd",t.toString())
            }

            override fun onResponse(
                call: Call<RankingResponse>,
                response: Response<RankingResponse>
            ) {
                if (response.isSuccessful)
                {
                    data.value = response.body()
                }
            }
        }

        )
    }


    fun startGame(game_id: String){

        Log.d("bbb", "$username + $game_id")

        lobbyService.startGame(game_id, NewGameBody(username,game_id)).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("StartGame", "failure on startGame")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful)
                {
                    //gameStarted.value = true
                }else
                {
                   // gameStarted.value = false
                }
            }
        })
    }

    fun login(data:LoginData, isLogged : MutableLiveData<Boolean>){


        Log.d("xd",data.email + data.password)
       authService.createAccount(data).enqueue( object :Callback<UserResponse> {
           override fun onFailure(call: Call<UserResponse>, t: Throwable) {
               Log.d("LogResponse","nie udane logowanie")
           }

           override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
            if(response.isSuccessful){
                Log.d("LogResponse","udane logowanie: " + response.body()?.username + " " + response.body()?.token)
                serviceInterceptor.token = response.body()?.token.toString()
                username = response.body()?.username.toString()
                isLogged.value = true
            }else
            {
                Log.d("LogResponse","nie udane logowanie S")
                isLogged.value = false
            }
           }


       })

    }
    fun register(data: RegisterData, registered: MutableLiveData<Boolean>) {

        authService.registerAccount(data).enqueue(object : Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("LogResponse", "request failed")
            }

            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                registered.value = response.isSuccessful
            }
        })

    }


    fun getAllSocietiesRelatedToUser(societies:MutableLiveData<SocietyResponse>)
    {
        lobbyService.getAllSocietesReletedToUser(GetSocietiesRelatedToUserRequest(username)).enqueue(object : Callback<SocietyResponse>
        {
            override fun onFailure(call: Call<SocietyResponse>, t: Throwable) {
                Log.d("SocietyResponse","błąd pobierania sovietuy")
            }

            override fun onResponse(
                call: Call<SocietyResponse>,
                response: Response<SocietyResponse>
            ) {
                if(response.isSuccessful)
                {
                    var x = response.body()
                    societies.value = x
                }else{
                    Log.d("SocietyResponse",response.body().toString())
                }
            }
        }
        )
    }


    fun createLobby(chosenSoc: String?,lobbies: MutableLiveData<LobbyResponse>)
    {
        chosenSoc?.let { NewLobbyRequest(it,username) }?.let {
            lobbyService.createNewLobby(it).enqueue( object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("LogResponse", "błąd: create Lobby");
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful) {
                        Log.d("LogResponse", "Utworzono Lobby");
                        NetModule.getAllLobbies(lobbies,chosenSoc)
                    }else{
                        Log.d("LogResponse", "Nie utworzono Lobby");
                    }
                }
            })
        }

    }

    fun getAllLobbies(lobbies: MutableLiveData<LobbyResponse>, chosenSoc: String?) {
        chosenSoc?.let { LobToSocRequest(it) }?.let {
            lobbyService.getAllLobbiesRelatedToSociety(it).enqueue(object : Callback<LobbyResponse>{
                override fun onFailure(call: Call<LobbyResponse>, t: Throwable) {
                    Log.d("LogResponse", "Coś pirdykło")
                }

                override fun onResponse(call: Call<LobbyResponse>, response: Response<LobbyResponse>) {
                    if(response.isSuccessful) {
                        lobbies.value = response.body()
                    }else {
                        Log.d("LogResponse", "Coś jebło")
                    }
                }
            })
        }
    }

    fun addPlayer(lobbyID:String,socID: String,lobbies: MutableLiveData<LobbyResponse>) {
        lobbyService.addPlayer(addPlayerBody(lobbyID, username)).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("LogResponse", "bład dodanie gracza")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  getAllLobbies(lobbies,socID)
                }
            })
    }

    fun postAnswers(game_id : String, data : PostAnswerBody) {
        gameService.sendData(game_id,data).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("postAnswers", "Service Unavaleible")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               if(!response.isSuccessful)
               {
                   Log.d("postAnswers","request failed")
               }else{
                   Log.d("postAnswers","request send successfully")
               }

            }
        })
    }

    fun leaveSociety(
        socID: String?,
        societies: MutableLiveData<SocietyResponse>
    ) {
        societyService.leaveSociety(LeaveSocietyBody(socID, username)).enqueue((object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("leaveSociety", "Service Unavaleible")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(!response.isSuccessful)
                {
                    Log.d("leaveSociety", "Service Unavaleible on reponse")
                }else{
                    getAllSocietiesRelatedToUser(societies)
                }
            }
        }))
    }

    fun joinSociety(socID : String?,societies: MutableLiveData<SocietyResponse>){
        societyService.joinSociety(JoinSocietyBody(username,socID)).enqueue ( object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("joinSociety", "Service Unavaleible")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if(!response.isSuccessful)
                {
                    Log.d("leaveSociety", "Service Unavaleible")
                }else{
                    getAllSocieties(societies)
                }
            }
        })
    }

    fun createSociety(userID : String ,socID : String)
    {

    }

    fun getAllSocieties(societies:MutableLiveData<SocietyResponse>)
    {
        societyService.getAllSocietiesNorReletedToUser(GetSocietiesRelatedToUserRequest(username)).enqueue(
            object : Callback<SocietyResponse> {
                override fun onFailure(call: Call<SocietyResponse>, t: Throwable) {
                    Log.d("idk", "asasasaS")
                }

                override fun onResponse(
                    call: Call<SocietyResponse>,
                    response: Response<SocietyResponse>
                ) {
                    if(response.isSuccessful){
                        societies.value = response.body()

                    } else{
                        Log.d("idl ",  "nie działa")
                    }
                }
            })
    }

    fun sendQuestion(
        question: Questionb,
        socID: String
    ) {

        questionService.addQuestion(QuestionBody(socID,question)).enqueue( object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("addQuestion", "Failure")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if(!response.isSuccessful)
                {
                    Log.d("addQuestion", "fail")
                }else{
                    Log.d("addQuestion", "success")
                }
            }
        })
    }

    fun getAllQuestionsToSociety(id: String?,questions:MutableLiveData<AllQuestionResponse> ) {

        questionService.getAllQuestions(QuestionBody(id,null)).enqueue(object : Callback<AllQuestionResponse> {

            override fun onFailure(call: Call<AllQuestionResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<AllQuestionResponse>,
                response: Response<AllQuestionResponse>
            ) {
                questions.value = response.body()
            }


        })
    }


    fun buyItem(
        body: BuyItemRequest,
        isSucces: MutableLiveData<Boolean>,
        playersItems: MutableLiveData<AllItemsResponse>
    )
    {
        body.userID = username
        shopService.buyItem(body).enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                isSucces.value = response.isSuccessful
                var body : BuyItemRequest = BuyItemRequest()
                body.userID = username
                getAllItems(body,playersItems)
            }
        })
    }

    fun getAllItems( body : BuyItemRequest, list : MutableLiveData<AllItemsResponse>){
        body.userID = username
        shopService.getAllItems(body).enqueue(object : Callback<AllItemsResponse> {
            override fun onFailure(call: Call<AllItemsResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<AllItemsResponse>,
                response: Response<AllItemsResponse>
            ) {
                if ( response.isSuccessful)
                {
                    list.value = response.body()
                }
            }
        })
    }

    fun setSelected( body : BuyItemRequest)
    {
        body.userID = username
        shopService.setSelected(body).enqueue( object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    Log.d("ss","idk")
                }
            }
        })
    }


}
