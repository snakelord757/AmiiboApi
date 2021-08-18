package com.example.amiiboapi.di.amiibo.component

import com.example.amiiboapi.di.amiibo.module.AmiiboModule
import com.example.amiiboapi.di.amiibo.scope.AmiiboScope
import com.example.amiiboapi.di.common.component.AppComponent
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.presentation.common.SchedulersProvider
import dagger.Component
import javax.inject.Scope

@Component(
    modules = [AmiiboModule::class],
    dependencies = [AppComponent::class])
@AmiiboScope
interface AmiiboComponent {

    fun amiiboInteractor(): AmiiboInteractor

    fun schedulersProvider(): SchedulersProvider

    fun errorMapper(): ErrorMapper
}