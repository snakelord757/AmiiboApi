package com.example.amiiboapi.presentation.extensions

import androidx.fragment.app.Fragment
import com.example.amiiboapi.presentation.common.interfaces.Navigation

val Fragment.navigation: Navigation
    get() = activity as Navigation