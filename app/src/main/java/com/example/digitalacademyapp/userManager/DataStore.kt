package com.example.digitalacademyapp.userManager

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserManager(private val context: Context) {

    companion object {
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
    }

    suspend fun registerUser(username: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
            preferences[PASSWORD] = password
        }
    }

    suspend fun loginUser(username: String, password: String): Boolean {
        val prefs = context.dataStore.data.map { it }.first()
        val storedUsername = prefs[USERNAME]
        val storedPassword = prefs[PASSWORD]
        return username == storedUsername && password == storedPassword
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { prefs ->
            prefs[USERNAME] != null && prefs[PASSWORD] != null
        }

    suspend fun logout() {
        context.dataStore.edit { it.clear() }
    }
}
