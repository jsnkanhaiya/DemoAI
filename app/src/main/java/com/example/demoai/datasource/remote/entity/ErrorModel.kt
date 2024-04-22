package com.example.demoai.datasource.remote.entity

data class ErrorModel(
    var code: String? = "",
    var message: String? = "",
    var statusCode: Int? = null,
    var data: DataError? = null,
    var error: Error? = null,
)

data class DataError(var info: Info?)
data class Error(var code: String?, var message: String?)

data class Info(val agreementRequired: Boolean?, val document: String?)
