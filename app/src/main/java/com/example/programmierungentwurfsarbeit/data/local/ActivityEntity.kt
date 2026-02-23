package com.example.programmierungentwurfsarbeit.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 *  Entitätsklasse, die eine Aktivität in der Datenbank repräsentiert.
 */
@Entity(
    tableName = "activities",
    indices = [Index(value = ["createdAt"], unique = true)]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val description: String,
    val imageUri: String?,
    val imageRes: Int?,
    val durationMinutes: Int,
    val createdAt: Long        // als Schlüssel (unique)
)
