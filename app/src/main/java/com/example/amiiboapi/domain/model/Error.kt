package com.example.amiiboapi.domain.model

/**
 * Модель возникаемой ошибки, которая содержит информацию о ней
 *
 * @property messageResId идентифиактор ресурса, описывающий ошибку
 *
 * @author Murad Luguev on 08-08-2021
 */
data class Error(val messageResId: Int)