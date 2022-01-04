package dev.itsu.manabe

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class MyApplication : Application() {

    companion object {
        private lateinit var INSTANCE: MyApplication
        fun getInstance()  = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AndroidThreeTen.init(this)
    }

}