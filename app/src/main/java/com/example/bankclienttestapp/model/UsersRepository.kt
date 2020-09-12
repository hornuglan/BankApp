package com.example.bankclienttestapp.model

import android.util.Log
import com.example.bankclienttestapp.ResponseUser
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class UsersRepository {
    fun loadUserProfiles(onSuccess: (ResponseUser) -> Unit, onFailure: (String) -> Unit) {
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
                        val users = Gson().fromJson(result.get(), ResponseUser::class.java)
                        onSuccess(users)
                    }
                }
            }
    }
}