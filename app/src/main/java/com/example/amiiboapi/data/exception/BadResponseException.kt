package com.example.amiiboapi.data.exception

/**
 * BadResponseException
 * исключение, которое возникает если не удалось получить ответ
 *
 * @property responseCode код ошибки
 *
 * @author Murad Luguev on 08-08-2021
 */
class BadResponseException(val responseCode: Int): Throwable()