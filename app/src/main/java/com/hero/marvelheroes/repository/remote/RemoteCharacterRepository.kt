package com.hero.marvelheroes.repository.remote

import com.hero.marvelheroes.BuildConfig
import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.CharactersRepository
import com.hero.marvelheroes.repository.remote.models.CharactersResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class RemoteCharacterRepository : CharactersRepository {

    private val apiKey: String = BuildConfig.MARVEL_API_KEY

    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(logging)
    }

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://gateway.marvel.com/v1/public/").client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val marvelApiClient: MarvelAPI = retrofit.create(MarvelAPI::class.java)

    override fun getCharactersList(
        offSet: Int,
        limit: Int,
        onError: (() -> Unit)?,
        onSuccess: (List<Character>) -> Unit
    ) {
        createCharactersHash(offSet, apiKey)?.let { md5Hash ->
            marvelApiClient.getCharacterList(offSet.toString(), apiKey, md5Hash, limit, offSet)
                ?.enqueue(object : Callback<CharactersResponse?> {
                    override fun onFailure(call: Call<CharactersResponse?>?, t: Throwable?) {
                        onGetCharactersError(t, onError)
                    }

                    override fun onResponse(
                        call: Call<CharactersResponse?>?,
                        response: Response<CharactersResponse?>?
                    ) {
                        convertResponseToCharacters(response, onSuccess, onError)
                    }
                })
        } ?: onError?.invoke()
    }

    private fun convertResponseToCharacters(
        response: Response<CharactersResponse?>?,
        onSuccess: (List<Character>) -> Unit,
        onError: (() -> Unit)?
    ) {
        response?.let { response ->
            val characterResponse = response.body()
            val characters =
                characterResponse?.data?.results?.map { characterResponse ->
                    Character(
                        characterResponse.id.toString(),
                        characterResponse.name,
                        "${characterResponse.thumbnail.path}.${characterResponse.thumbnail.extension}"
                    )
                } ?: emptyList()

            onSuccess(characters)
        } ?: onError?.invoke()
    }

    private fun onGetCharactersError(t: Throwable?, onError: (() -> Unit)?) {
        t?.printStackTrace()
        onError?.invoke()
    }

    private fun createCharactersHash(
        offSet: Int,
        apiKey: String,
        privateKey: String = BuildConfig.MARVEL_PRIVATE_API_KEY
    ) = convertPassMd5("$offSet$privateKey$apiKey")


    private fun convertPassMd5(pass: String): String? {
        var pass = pass
        var password: String? = null
        val mdEnc: MessageDigest
        try {
            mdEnc = MessageDigest.getInstance("MD5")
            mdEnc.update(pass.toByteArray(), 0, pass.length)
            pass = BigInteger(1, mdEnc.digest()).toString(16)
            while (pass.length < 32) {
                pass = "0$pass"
            }
            password = pass
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        }
        return password
    }
}


interface MarvelAPI {

    @GET("characters")
    fun getCharacterList(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<CharactersResponse?>?

    @GET("characters/{id}")
    fun getCharacter(
        @Path("id") id: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Call<CharactersResponse?>?
}