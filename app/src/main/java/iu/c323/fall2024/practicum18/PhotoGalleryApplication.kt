package iu.c323.fall2024.practicum18

import android.app.Application

class PhotoGalleryApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesRepository.initialize(this)
    }
}