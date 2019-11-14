package com.thiqah.movies.presentation.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.thiqah.movies.core.utility.ThiqahException

class ObservableResourceMObservers<T> : MutableLiveData<T>() {

    val error: MutableLiveData<ThiqahException> = ErrorLiveData()
    val loading: MutableLiveData<Boolean> =
        MutableLiveData()

    fun observe(
        owner: LifecycleOwner, successObserver: Observer<T>,
        loadingObserver: Observer<Boolean>? = null,
        commonErrorObserver: Observer<ThiqahException>,
        httpErrorConsumer: Observer<ThiqahException>? = null,
        networkErrorConsumer: Observer<ThiqahException>? = null,
        unExpectedErrorConsumer: Observer<ThiqahException>? = null,

        serverDownErrorConsumer: Observer<ThiqahException>? = null,
        timeOutErrorConsumer: Observer<ThiqahException>? = null,
        unAuthorizedErrorConsumer: Observer<ThiqahException>? = null
    ) {
        super.observe(owner, successObserver)
        loadingObserver?.let { loading.observe(owner, it) }
        (error as ErrorLiveData).observe(
            owner,
            commonErrorObserver,
            httpErrorConsumer,
            networkErrorConsumer,
            unExpectedErrorConsumer,
            serverDownErrorConsumer, timeOutErrorConsumer, unAuthorizedErrorConsumer
        )
    }


    class ErrorLiveData : MutableLiveData<ThiqahException>() {
        private var ownerRef: LifecycleOwner? = null
        private var httpErrorConsumer: Observer<ThiqahException>? = null
        private var networkErrorConsumer: Observer<ThiqahException>? = null
        private var unExpectedErrorConsumer: Observer<ThiqahException>? = null
        private var commonErrorConsumer: Observer<ThiqahException>? = null

        private var serverDownErrorConsumer: Observer<ThiqahException>? = null
        private var timeOutErrorConsumer: Observer<ThiqahException>? = null
        private var unAuthorizedErrorConsumer: Observer<ThiqahException>? = null

        override fun setValue(value: ThiqahException?) {
            ownerRef?.also {
                removeObservers(it)
                value?.let { vale -> addProperObserver(vale) }
                super.setValue(value)
            }

        }

        override fun postValue(value: ThiqahException) {
            ownerRef?.also {
                removeObservers(it)
                addProperObserver(value)
                super.postValue(value)
            }

        }

        private fun addProperObserver(value: ThiqahException) {
            when (value.kind) {
                ThiqahException.Kind.NETWORK -> networkErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                ThiqahException.Kind.HTTP -> httpErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                ThiqahException.Kind.UNEXPECTED -> unExpectedErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                ThiqahException.Kind.SERVER_DOWN -> serverDownErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                ThiqahException.Kind.TIME_OUT -> timeOutErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                ThiqahException.Kind.UNAUTHORIZED -> unAuthorizedErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                else -> {
                }
            }
        }


        fun observe(
            owner: LifecycleOwner, commonErrorConsumer: Observer<ThiqahException>,
            httpErrorConsumer: Observer<ThiqahException>? = null,
            networkErrorConsumer: Observer<ThiqahException>? = null,
            unExpectedErrorConsumer: Observer<ThiqahException>? = null,
            serverDownErrorConsumer: Observer<ThiqahException>? = null,
            timeOutErrorConsumer: Observer<ThiqahException>? = null,
            unAuthorizedErrorConsumer: Observer<ThiqahException>? = null
        ) {
            super.observe(owner, commonErrorConsumer)
            this.ownerRef = owner
            this.commonErrorConsumer = commonErrorConsumer
            this.httpErrorConsumer = httpErrorConsumer
            this.networkErrorConsumer = networkErrorConsumer
            this.unExpectedErrorConsumer = unExpectedErrorConsumer
            this.serverDownErrorConsumer = serverDownErrorConsumer
            this.timeOutErrorConsumer = timeOutErrorConsumer
            this.unAuthorizedErrorConsumer = unAuthorizedErrorConsumer
        }
    }
}