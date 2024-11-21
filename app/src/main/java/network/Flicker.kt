package network

import retrofit2.http.GET
import retrofit2.http.Query

//Key:
//3dff3792b9bee98cd1a3235f7a302cd7
//
//Secret:
//853857a7183ccfcc

private const val API_KEY = "3dff3792b9bee98cd1a3235f7a302cd7"
interface Flicker {
    @GET("/")
    suspend fun fetchContents(): String

    @GET("services/rest/?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(): FlickerResponse

    @GET("services/rest/?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String): FlickerResponse
}