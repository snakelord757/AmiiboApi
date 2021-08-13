package com.example.amiiboapi.domain.mapper

import com.example.amiiboapi.domain.model.Error

/**
 * Маппер ошибок, которые могут прийти во время получения ответа
 *
 * @author Murad Luguev on 08-08-2021
 */
interface ErrorMapper {
    /**
     * Возвращает [Error], котоырй содержит информцаию об ошибке
     *
     * @param throwable исключение, которое возникло во время получения ответа
     *
     * @return [Error] с описанием ошибки
     */
    fun mapError(throwable: Throwable): Error
}