package com.example.domain.models

class Response<T>(
    val statusCode: Int,
    val data: T?,
    val errorMessage: String?
)
