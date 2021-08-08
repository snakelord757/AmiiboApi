package com.example.amiiboapi.presentation.common.interfaces

import androidx.fragment.app.Fragment

/**
 * Интерфейс для навигации между фрагментами
 */
interface Navigation {
    /**
     * Метод, выполняющий навигацию
     *
     * @param destination фрагмент, на который необходимо перейти
     * @param tag тэг фрагмента, на который необходимо перейти
     */
    fun move(destination: Fragment, tag: String)
}