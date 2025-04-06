package lv.chernishenko.chucknorrisfacts.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import lv.chernishenko.chucknorrisfacts.model.ChuckNorrisFact
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class ChuckNorrisRepository @Inject constructor(
    private val gson: Gson
) {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun randomFact(): Result<ChuckNorrisFact> {
        val request = Request.Builder()
            .url("https://api.chucknorris.io/jokes/random")
            .get()
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            val body = response.body?.string()
            if (body == null) {
                Result.failure(ChuckNorrisException("Response body is null or empty"))
            } else {
                val jsonObject = gson.fromJson(body, JsonObject::class.java)

                val fact = ChuckNorrisFact(
                    id = jsonObject.get("id").asString,
                    url = jsonObject.get("url")?.asString,
                    iconUrl = jsonObject.get("icon_url")?.asString,
                    value = jsonObject.get("value").asString
                )

                Result.success(fact)
            }
        } else {
            Result.failure(ChuckNorrisException("Response code: ${response.code}, message: ${response.message}"))
        }
    }
}

class ChuckNorrisException(message: String) : Exception(message)