package com.cbellmont.ejercicioandroid16

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Personaje::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PersonajesDao(): PersonajeDao
}