package com.example.programmierungentwurfsarbeit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *  Datenbankklasse.
 *  * Definiert die Datenbankkonfiguration, Entitäten und Datenzugriffsobjekte (DAOs).
 *  * Implementiert das Singleton-Pattern für thread-sicheren Datenbankzugriff.
 */
@Database(
    entities = [ActivityEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activitiesDao(): ActivitiesDao

    companion object {
        // @Volatile stellt sicher, dass Änderungen sofort für alle Threads sichtbar sind.
        @Volatile
        private var INSTANCE: AppDatabase? = null;

        /**
         * Liefert die Singleton-Instanz der AppDatabase.
         * Erstellt bei erstmaligem Aufruf eine neue Datenbankinstanz.
         * */
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "healthy_me.db"
                ).build().also { INSTANCE = it }
            }
    }
}
