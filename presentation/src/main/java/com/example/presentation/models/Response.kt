package com.example.presentation.models

class Response<T>(
    val statusCode: Int,
    val data: T?,
    val errorMessage: String?
)
