package com.example.recipebook.core

class NoInternetConnectionException() : Exception()

class BackendException(override val message: String?) : Exception(message)

class LocalDataSourceException(override val message: String?) : Exception(message)

class CloudDataSourceException(override val message: String? = null) : Exception(message)