package com.coldzz.lexiup.core.data.remote

import com.coldzz.lexiup.core.data.remote.model.WiktionaryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WiktionaryApi {

    /**
    * We use wiktionary to get mp3 file links of specific word
    * */
    @GET("/w/api.php")
    suspend fun getFilesData(
        @Query("titles") word: String,
        @Query("generator") generator: String = "images",
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "imageinfo",
        @Query("iiprop") iiprop: String = "url",
        @Query("format") format: String = "json",
        @Query("gimlimit") limit: String = "max"
    ): WiktionaryResponse
}