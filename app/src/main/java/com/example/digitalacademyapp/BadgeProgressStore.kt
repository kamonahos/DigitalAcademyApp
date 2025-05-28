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

    private fun keyForUser(userName: String): Preferences.Key<Set<String>> {
        return stringSetPreferencesKey("completed_badges_$userName")
    }

    suspend fun markBadgeEarned(context: Context, userName: String, courseId: String) {
        val key = keyForUser(userName)
        context.dataStore.edit { prefs ->
            val updated = prefs[key]?.toMutableSet() ?: mutableSetOf()
            updated.add(courseId)
            prefs[key] = updated
        }
    }

    fun isCourseCompleted(context: Context, userName: String, courseId: String): Flow<Boolean> {
        val key = keyForUser(userName)
        return context.dataStore.data
            .map { prefs ->
                prefs[key]?.contains(courseId) ?: false
            }
    }


    suspend fun clearAllDataStore(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    suspend fun hasEarnedBadge(context: Context, userName: String, courseId: String): Boolean {
        val key = keyForUser(userName)
        val prefs = context.dataStore.data.first()
        return prefs[key]?.contains(courseId) ?: false
    }


    fun earnedBadgesFlow(context: Context, userName: String): Flow<Set<String>> {
        val key = keyForUser(userName)
        return context.dataStore.data.map { prefs ->
            prefs[key] ?: emptySet()
        }
    }
}
