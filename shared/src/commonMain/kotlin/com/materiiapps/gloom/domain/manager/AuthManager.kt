package com.materiiapps.gloom.domain.manager

expect class AuthManager {

    var authToken: String

    val isSignedIn: Boolean

    fun clearApolloCache()

}