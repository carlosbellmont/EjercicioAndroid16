package com.cbellmont.ejercicioandroid16

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {


    fun loadData(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            loadAll(context)
            loadBuenos(context)
            loadMalos(context)
        }
    }

    suspend fun loadAll(context: Context) : LiveData<List<Personaje>> {
        return withContext(Dispatchers.IO) {
            App.getDatabase(context).PersonajesDao().getAllLive()
        }
    }

    suspend fun loadBuenos(context: Context) : LiveData<List<Personaje>> {
        return withContext(Dispatchers.IO) {
            App.getDatabase(context).PersonajesDao().loadAllBuenosLive()
        }
    }

    suspend fun loadMalos(context: Context) : LiveData<List<Personaje>> {
        return withContext(Dispatchers.IO) {
            App.getDatabase(context).PersonajesDao().loadAllMalosLive()
        }
    }
}