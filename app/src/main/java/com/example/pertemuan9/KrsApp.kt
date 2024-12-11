package com.example.pertemuan9

import android.app.Application
import com.example.pertemuan9.dependenciesinjection.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp // Fungsinya untuk menyimpan instance

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) // Membuat Instamnce ContainerApp
        // Instance adalah object yang dibuat dari class
    }
}