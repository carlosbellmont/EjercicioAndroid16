package com.cbellmont.ejercicioandroid15

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : PersonajesAdapter

    lateinit var listaMostrable : LiveData<List<Personaje>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView()

        val model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        model.loadData(this)

        val personajesObserver = Observer<List<Personaje>> { listaPersonajes ->
            adapter.updatePersonajes(listaPersonajes)
        }

        CoroutineScope(Dispatchers.Main).launch {
            listaMostrable = model.loadAll(this@MainActivity)
            listaMostrable.observe(this@MainActivity, personajesObserver)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Main){
                    when (checkedId) {
                        rbTodos.id -> {
                            listaMostrable = model.loadAll(this@MainActivity)
                            listaMostrable.observe(this@MainActivity, personajesObserver)
                            listaMostrable.value?.let { adapter.updatePersonajes(it) }
                        }
                        rbBuenos.id -> {
                            listaMostrable = model.loadBuenos(this@MainActivity)
                            listaMostrable.observe(this@MainActivity, personajesObserver)
                            listaMostrable.value?.let { adapter.updatePersonajes(it) }
                        }
                        rbMalos.id -> {
                            listaMostrable = model.loadMalos(this@MainActivity)
                            listaMostrable.observe(this@MainActivity, personajesObserver)
                            listaMostrable.value?.let { adapter.updatePersonajes(it) }
                        }
                    }
                }
            }
        }
    }

    private fun createRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                adapter = PersonajesAdapter()
            }
            withContext(Dispatchers.Main) {
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = adapter
            }
        }
    }




}