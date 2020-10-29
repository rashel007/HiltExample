package com.learn.hiltexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // this is field injection
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.someWork())
        println(someClass.otherWork1())
        println(someClass.otherWork2())
    }
}


class SomeClass @Inject constructor(
    @Impl1 private val otherClass1: OtherInterface,
    @Impl2 private val otherClass2: OtherInterface,
    private  val gson: Gson
) {
    fun someWork(): String {
        return "Look I am doing some work"
    }

    fun otherWork1(): String {
        return otherClass1.otherWork()
    }

    fun otherWork2(): String {
        return otherClass2.otherWork()
    }
}

class OtherInterfaceImpl1 @Inject constructor(private val name: String) : OtherInterface {
    override fun otherWork(): String {
        return "Look i am doing some other work 1. ${name}"
    }
}

class OtherInterfaceImpl2 @Inject constructor(private val name: String) : OtherInterface {
    override fun otherWork(): String {
        return "Look i am doing some other work 2. ${name}"
    }
}

interface OtherInterface {
    fun otherWork(): String
}

@InstallIn(ApplicationComponent::class)
@Module
class Module {


    @Singleton
    @Provides
    fun provideName(): String {
        return "EA Rashel"
    }

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return Gson()
    }

    @Impl1
    @Singleton
    @Provides
    fun provideOtherInterface1(name: String): OtherInterface {
        return OtherInterfaceImpl1(name)
    }

    @Impl2
    @Singleton
    @Provides
    fun provideOtherInterface2(name: String): OtherInterface {
        return OtherInterfaceImpl2(name)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2