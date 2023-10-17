package com.amazonaws.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import org.koin.core.context.startKoin

class ApplicationRunner : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //  androidLogger(Level.ERROR)
            //    androidContext(this@ApplicationRunner)
            koin.loadModules(
                emptyList()
            )
        }
    }
}

class AmazonawsRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, ApplicationRunner::class.java.name, context)
    }
}