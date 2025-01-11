package com.example.p15firebase

import android.app.Application
import com.example.p15firebase.di.ContainerApp

class MahasiswaApp : Application() {
    lateinit var containerApp: ContainerApp
    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}