package com.coldzz.lexiup.core.data.remote

import com.coldzz.lexiup.core.data.remote.model.DictionaryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("api/v2/entries/en/{word}")
    suspend fun getWord(
        @Path("word") word: String
    ): List<DictionaryResponse>
}