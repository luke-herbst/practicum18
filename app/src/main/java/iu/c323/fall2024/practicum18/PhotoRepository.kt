package iu.c323.fall2024.practicum18

import network.Flicker
import network.GalleryItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class PhotoRepository {

    private val flicker: Flicker

    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        flicker = retrofit.create<Flicker>()

    }

    suspend fun fetchContents() =
        flicker.fetchContents()

    suspend fun fetchPhotos(): List<GalleryItem> =
        flicker.fetchPhotos().photos.galleryItems

}