package com.ceiba.prueba.data.entities

import androidx.room.Embedded

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded
    val geo: Geo
)
