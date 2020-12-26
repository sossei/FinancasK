package br.com.alura.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    val receita get(): BigDecimal = somarPort(Tipo.RECEITA)

    val despesa get(): BigDecimal = somarPort(Tipo.DESPESA)

    val total get(): BigDecimal = receita.subtract(despesa)

    private fun somarPort(tipo: Tipo): BigDecimal {
        return transacoes
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() }
            .toBigDecimal()
    }
}