package com.materiiapps.gloom.domain.manager

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.apolloStore
import com.materiiapps.gloom.util.Logger
import com.materiiapps.gloom.util.SettingsProvider
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
    val accounts = mutableStateMapOf<String, Account>()

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

    fun addAccount(
        id: String,
        token: String,
        type: Account.Type = Account.Type.REGULAR,
        baseUrl: String? = null,
        avatarUrl: String,
        username: String,
        displayName: String?,
        notificationCount: Int = 0
    ) {
        Account(id, token, type, baseUrl, avatarUrl, username, displayName, notificationCount).let {
            accounts[id] = it
            settings.putString(id, json.encodeToString(it))
        }
    }

    fun editAccount(
        id: String,
        token: String = accounts[id]!!.token,
        type: Account.Type = accounts[id]!!.type,
        baseUrl: String? = accounts[id]!!.baseUrl,
        avatarUrl: String = accounts[id]!!.avatarUrl,
        username: String = accounts[id]!!.username,
        displayName: String? = accounts[id]!!.displayName,
        notificationCount: Int = accounts[id]!!.notificationCount
    ) = editAccount(
        accounts[id]!!,
        token,
        type,
        baseUrl,
        avatarUrl,
        username,
        displayName,
        notificationCount
    )

    private fun editAccount(
        account: Account,
        token: String = account.token,
        type: Account.Type = account.type,
        baseUrl: String? = account.baseUrl,
        avatarUrl: String = account.avatarUrl,
        username: String = account.username,
        displayName: String? = account.displayName,
        notificationCount: Int = account.notificationCount
    ) {
        accounts[account.id]?.let {
            accounts[account.id] = account.copy(
                token = token,
                type = type,
                baseUrl = baseUrl,
                avatarUrl = avatarUrl,
                username = username,
                displayName = displayName,
                notificationCount = notificationCount
            )
            settings.putString(it.id, json.encodeToString(it))
        }
    }

    fun removeAccount(id: String) {
        if(id == currentAccountId) currentAccountId = ""
        accounts.remove(id)
        settings.remove(id)
    }

    fun switchToAccount(id: String) {
        if (accounts.containsKey(id)) currentAccountId = id
        clearApolloCache()
    }

    fun clearApolloCache() {
        apollo.apolloStore.clearAll()
    }

}

@Stable
@Serializable
data class Account(
    val id: String,
    val token: String,
    val type: Type,
    val baseUrl: String?,
    val avatarUrl: String,
    val username: String,
    val displayName: String?,
    val notificationCount: Int = 0
) {

    @Serializable
    enum class Type {
        REGULAR,
        ENTERPRISE
    }

}