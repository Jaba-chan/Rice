package ru.evgenykuzakov.rice.data

data class Heading(
    val heading: String,
    val dbName: DatabaseNamesEnum,
    var isExpanded: Boolean = true
)
