package com.example.programmierungentwurfsarbeit.ui.activities

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmierungentwurfsarbeit.data.ActivitiesRepository
import com.example.programmierungentwurfsarbeit.data.local.AppDatabase
import com.example.programmierungentwurfsarbeit.model.UserActivity
import com.example.programmierungentwurfsarbeit.util.saveBitmapToCache
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActivitiesViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ActivitiesRepository(AppDatabase.get(app).activitiesDao())

    // UI-State als StateFlow
    val activities = repo.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** Fügt hinzu; speichert ggf. Kamera-Bitmap als Datei und nutzt deren URI. */
    fun add(context: Context, activity: UserActivity) = viewModelScope.launch {
        val withUri = if (activity.imageUri == null && activity.cameraPreview != null) {
            val uri = saveBitmapToCache(context, activity.cameraPreview)
            activity.copy(imageUri = uri, cameraPreview = null)
        } else activity.copy(cameraPreview = null)
        repo.insert(withUri)
    }

    /** Aktualisiert Eintrag an Stelle [index] (identifiziert über createdAt). */
    fun updateAt(context: Context, index: Int, updated: UserActivity) = viewModelScope.launch {
        val current = activities.value.getOrNull(index) ?: return@launch
        val normalized = if (updated.imageUri == null && updated.cameraPreview != null) {
            val uri = saveBitmapToCache(context, updated.cameraPreview)
            updated.copy(imageUri = uri, cameraPreview = null, createdAt = current.createdAt)
        } else updated.copy(cameraPreview = null, createdAt = current.createdAt)
        repo.updateByCreatedAt(normalized)
    }

    /** Löscht Eintrag an Stelle [index] (identifiziert über createdAt). */
    fun deleteAt(index: Int) = viewModelScope.launch {
        val current = activities.value.getOrNull(index) ?: return@launch
        repo.deleteByCreatedAt(current.createdAt)
    }
}
