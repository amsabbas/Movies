package com.thiqah.movies.core.utility


class ThiqahException : Exception {

    private var data: String? = null
    private var statusCode: Int = 0
    private var errorCode: Int = 0
    var kind: Kind? = null
        private set

    constructor(kind: Kind) {
        this.kind = kind
    }

    constructor(kind: Kind, message: String) : super(message) {
        this.kind = kind
    }

    constructor(kind: Kind, message: String, cause: Throwable) : super(message, cause) {
        this.kind = kind
    }

    constructor(kind: Kind, cause: Throwable) : super(cause) {
        this.kind = kind
    }

    fun getData(): String? {
        return data
    }

    fun setData(data: String?): ThiqahException {
        this.data = data
        return this
    }

    fun getStatusCode(): Int {
        return statusCode
    }

    fun setStatusCode(statusCode: Int): ThiqahException {
        this.statusCode = statusCode
        return this
    }

    fun getErrorCode(): Int {
        return errorCode
    }

    fun setErrorCode(errorCode: Int): ThiqahException {
        this.errorCode = errorCode
        return this
    }


    enum class Kind {

        NETWORK,

        HTTP,

        UNEXPECTED,

        SERVER_DOWN,

        TIME_OUT,

        UNAUTHORIZED

    }

}
