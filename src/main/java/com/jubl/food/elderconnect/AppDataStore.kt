package com.jubl.food.elderconnect

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define DataStore instance
val Context.appDataStore by preferencesDataStore("app_data_store")

// Define preference keys for contacts and checklist
private val CONTACTS_KEY = stringPreferencesKey("contacts")
private val CHECKLIST_KEY = stringPreferencesKey("checklist_items")


// Save and Load Contacts
suspend fun saveContacts(context: Context, contacts: List<ContactRow>) {
    val json = Gson().toJson(contacts)
    context.appDataStore.edit { preferences ->
        preferences[CONTACTS_KEY] = json
    }
}

fun loadContacts(context: Context): Flow<List<ContactRow>> {
    return context.appDataStore.data.map { preferences ->
        val json = preferences[CONTACTS_KEY] ?: "[]"
        val type = object : TypeToken<List<ContactRow>>() {}.type
        Gson().fromJson(json, type)
    }
}

// Save and Load Checklist Items
suspend fun saveChecklist(context: Context, items: List<ChecklistItem>) {
    val json = Gson().toJson(items)
    context.appDataStore.edit { preferences ->
        preferences[CHECKLIST_KEY] = json
    }
}

fun loadChecklist(context: Context): Flow<List<ChecklistItem>> {
    return context.appDataStore.data.map { preferences ->
        val json = preferences[CHECKLIST_KEY] ?: "[]"
        val type = object : TypeToken<List<ChecklistItem>>() {}.type
        Gson().fromJson(json, type)
    }
}
