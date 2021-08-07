package com.example.amiiboapi.presentation.common.interfaces

import androidx.fragment.app.Fragment

interface Navigation {
    fun move(destination: Fragment, tag: String)
}