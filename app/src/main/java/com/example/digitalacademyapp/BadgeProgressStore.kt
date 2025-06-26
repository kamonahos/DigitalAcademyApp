package com.example.digitalacademyapp


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "badge_progress")


object BadgeProgressStore {
    private fun badgeKey(userName: String): Preferences.Key<Set<String>> {
        return stringSetPreferencesKey("completed_badges_$userName")
    }


    suspend fun markBadgeEarned(context: Context, userName: String, courseId: String) {
        val key = badgeKey(userName)
        context.dataStore.edit { prefs ->
            val updated = prefs[key]?.toMutableSet() ?: mutableSetOf()
            updated.add(courseId)
            prefs[key] = updated
        }
    }


    suspend fun hasEarnedBadge(context: Context, userName: String, courseId: String): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[badgeKey(userName)]?.contains(courseId) ?: false
    }


    fun earnedBadgesFlow(context: Context, userName: String): Flow<Set<String>> =
        context.dataStore.data.map { it[badgeKey(userName)] ?: emptySet() }


    suspend fun clearAllBadgeData(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.clear() // optional, clears all
        }
    }
}

