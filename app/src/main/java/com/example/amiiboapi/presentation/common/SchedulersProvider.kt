package com.example.amiiboapi.presentation.common

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun main(): Scheduler
    fun io(): Scheduler
}