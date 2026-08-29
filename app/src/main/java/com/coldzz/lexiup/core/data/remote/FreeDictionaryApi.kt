package com.coldzz.lexiup.core.data.remote

import com.coldzz.lexiup.core.data.remote.model.FreeDictionaryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FreeDictionaryApi {
    @GET("api/v1/entries/en/{word}")
    suspend fun getWord(
        @Path("word") word: String
    ): FreeDictionaryResponse
}