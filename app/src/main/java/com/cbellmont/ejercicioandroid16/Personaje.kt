package com.cbellmont.ejercicioandroid16

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Personaje(val nombre : String, val raza : String, val image : Int, val esBueno : Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}