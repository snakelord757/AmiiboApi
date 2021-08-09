package com.example.amiiboapi.presentation.common

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class SchedulerProviderTest : SchedulersProvider {
    override fun main(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()
}