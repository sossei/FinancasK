package br.com.alura.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatoBrasileiro(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    return SimpleDateFormat(formatoBrasileiro).format(this.time)
}