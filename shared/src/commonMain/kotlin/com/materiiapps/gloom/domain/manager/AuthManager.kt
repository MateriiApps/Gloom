package com.materiiapps.gloom.domain.manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.materiiapps.gloom.utils.Logger
import com.materiiapps.gloom.utils.SettingsProvider
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthManager(
    settingsProvider: SettingsProvider,
    private val apollo: ApolloClient,
    private val json: Json,
    logger: Logger
) {

    private val settings = settingsProvider.createSettings()

    // ID to account
    private val accounts: MutableMap<String, Account> = mutableMapOf()

    init {
        settings.remove("authToken")
        for (key in settings.keys) {
            try {
                val acct = json.decodeFromString<Account>(settings.getString(key, ""))
                accounts[key] = acct
            } catch (e: SerializationException) {
                if(key != "logged_in_id") logger.error("AuthManager", "Error serializing account", e)
            }
        }
    }

    private var currentAccountId: String
        get() = settings.getString("logged_in_id", "")
        set(value) = settings.putString("logged_in_id", value)

    val currentAccount: Account?
        get() {
            if(!isSignedIn) return null
            return accounts[currentAccountId]
        }

    val authToken: String
        get() = currentAccount?.token ?: ""

    val isSignedIn: Boolean get() = currentAccountId.isNotBlank() && awaitingAuthType == null

    var awaitingAuthType: Account.Type? = null
        private set

    var loading by mutableStateOf(false)
        private set

    fun setAuthState(authType: Account.Type? = awaitingAuthType, loading: Boolean = this.loading) {
        awaitingAuthType = authType
        this.loading = loading
    }

    fun addAccount(id: String, token: String, type: Account.Type = Account.Type.REGULAR, baseUrl: String? = null) {
        Account(id, token, type, baseUrl).let {
            accounts[id] = it
            settings.putString(id, json.encodeToString(it))
        }
    }

    fun removeAccount(id: String) {
        accounts.remove(id)
        settings.remove(id)
    }

    fun switchToAccount(id: String) {
        if(accounts.containsKey(id)) currentAccountId = id
        clearApolloCache()
    }

    fun clearApolloCache() {
        apollo.apolloStore.clearAll()
    }

}

@Serializable
data class Account (
    val id: String,
    val token: String,
    val type: Type,
    val baseUrl: String?
) {

    @Serializable
    enum class Type {
        REGULAR,
        ENTERPRISE
    }

}