package network

import retrofit2.http.GET

//Key:
//3dff3792b9bee98cd1a3235f7a302cd7
//
//Secret:
//853857a7183ccfcc

private const val API_KEY = "3dff3792b9bee98cd1a3235f7a302cd7"
interface Flicker {
    @GET("/")
    suspend fun fetchContents(): String

    @GET("services/rest/?method=flickr.interestingness.getList"+
            "&api_key=$API_KEY"+
            "&format=json" +
            "&nojsoncallback=1" +
            "&extras=url_s")
    suspend fun fetchPhotos(): FlickerResponse
}