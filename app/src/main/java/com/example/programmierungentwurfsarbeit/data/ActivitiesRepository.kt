package com.example.programmierungentwurfsarbeit.data

import android.net.Uri
import com.example.programmierungentwurfsarbeit.data.local.ActivitiesDao
import com.example.programmierungentwurfsarbeit.data.local.ActivityEntity
import com.example.programmierungentwurfsarbeit.model.Kategorie
import com.example.programmierungentwurfsarbeit.model.UserActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActivitiesRepository(private val dao: ActivitiesDao) {

    fun getAll(): Flow<List<UserActivity>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    suspend fun insert(activity: UserActivity) {
        dao.insert(activity.toEntity())
    }

    suspend fun updateByCreatedAt(updated: UserActivity) {
        dao.updateByCreatedAt(
            title = updated.title,
            category = updated.category.name,
            description = updated.description,
            imageUri = updated.imageUri?.toString(),
            imageRes = updated.imageRes,
            durationMinutes = updated.durationMinutes,
            createdAt = updated.createdAt
        )
    }

    suspend fun deleteByCreatedAt(createdAt: Long) {
        dao.deleteByCreatedAt(createdAt)
    }
}

//Mapping
private fun ActivityEntity.toDomain(): UserActivity =
    UserActivity(
        title = title,
        category = Kategorie.valueOf(category),
        description = description,
        imageUri = imageUri?.let { Uri.parse(it) },
        cameraPreview = null,
        imageRes = imageRes,
        durationMinutes = durationMinutes,
        createdAt = createdAt
    )

private fun UserActivity.toEntity(): ActivityEntity =
    ActivityEntity(
        title = title,
        category = category.name,
        description = description,
        imageUri = imageUri?.toString(),
        imageRes = imageRes,
        durationMinutes = durationMinutes,
        createdAt = createdAt
    )
