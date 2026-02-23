package com.example.programmierungentwurfsarbeit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * Data Access Object (DAO) für die Aktivitäten-Tabelle.
 * */
@Dao
interface ActivitiesDao {

    @Insert
    suspend fun insert(entity: ActivityEntity)

    //Fügt eine neue Aktivität in die Datenbank ein.
    @Query("SELECT * FROM activities ORDER BY createdAt ASC")
    fun getAll(): Flow<List<ActivityEntity>>

    // Ruft alle Aktivitäten aus der Datenbank ab.
    // Die Aktivitäten werden nach ihrem Erstellungsdatum aufsteigend sortiert.
    @Query(
        """
        UPDATE activities SET
            title = :title,
            category = :category,
            description = :description,
            imageUri = :imageUri,
            imageRes = :imageRes,
            durationMinutes = :durationMinutes
        WHERE createdAt = :createdAt
    """
    )
    //Aktualisiert eine bestehende Aktivität anhand ihres createdAt.
    suspend fun updateByCreatedAt(
        title: String,
        category: String,
        description: String,
        imageUri: String?,
        imageRes: Int?,
        durationMinutes: Int,
        createdAt: Long
    )

    // Löscht eine Aktivität anhand ihres createdAt.
    @Query("DELETE FROM activities WHERE createdAt = :createdAt")
    suspend fun deleteByCreatedAt(createdAt: Long)
}
