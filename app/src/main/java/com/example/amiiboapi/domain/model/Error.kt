package com.example.amiiboapi.domain.model

/**
 * Модель возникаемой ошибки, которая содержит информацию о ней
 *
 * @property messageResId идентифиактор ресурса, описывающий ошибку
 *
 * @author Murad Luguev on 08-08-2021
 */
class Error(val messageResId: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Error

        if (messageResId != other.messageResId) return false

        return true
    }

    override fun hashCode(): Int {
        return messageResId
    }
}