package com.cbellmont.ejercicioandroid16

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*


class App : Application() {

    companion object {
        private var db : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            db?.let { return it }

            db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
                .addCallback(getCallback())
                .build()
            return db as AppDatabase
        }

        private fun getCallback(): RoomDatabase.Callback {
            return object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.IO) {
                            val personajes : List<Personaje> = listOf(
                                Personaje("Aragorn", "Humano", R.mipmap.aragorn, true) ,
                                Personaje("Gandalf", "Mago", R.mipmap.gandalf, true),
                                Personaje("Boromir", "Humano", R.mipmap.boromir, true),
                                Personaje("Legolas", "Elfo", R.mipmap.legolas, true),
                                Personaje("Orco Feo", "Orco", R.mipmap.orco, false),
                                Personaje("Smagu", "Dragon", R.mipmap.smagu, false)
                            )
                            App.db?.PersonajesDao()?.insertAll(personajes)
                        }
                    }
                }
                override fun onOpen(db: SupportSQLiteDatabase) {
                }
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "database-name")
            .addCallback(getCallback())
            .build()
    }

}