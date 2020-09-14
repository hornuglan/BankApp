package com.example.bankclienttestapp.model

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.Result
import com.google.gson.Gson

data class UserResponse(
    val users: ArrayList<User>
)

class UserRepository {
    fun loadUserProfiles(onSuccess: (UserResponse) -> Unit, onFailure: (String) -> Unit) {
        val url = "https://hr.peterpartner.net/test/android/v1/users.json"

        Fuel.get(url)
            .header(Headers.CONTENT_TYPE, "application/json")
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val e = result.getException()
                        Log.d("Request Error", "${e.message}")
                        onFailure("${e.message}")
                    }
                    is Result.Success -> {
                        val users = Gson().fromJson(result.get(), UserResponse::class.java)
                        onSuccess(users)
                    }
                }
            }
    }
}