package com.example.amiiboapi.presentation.extensions

import androidx.fragment.app.Fragment
import com.example.amiiboapi.presentation.common.interfaces.Navigation

/**
 * Поле-расширение для получения реализации Navigation из хоста-активити
 */
val Fragment.navigation: Navigation
    get() = activity as Navigation