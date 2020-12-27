package br.com.alura.dao

import br.com.alura.model.Transacao

class TransacaoDAO {

    companion object {
        private val transacoes: MutableList<Transacao> = mutableListOf()
    }

    val transacoes: List<Transacao> = Companion.transacoes

    fun adiciona(transacao: Transacao) {
        Companion.transacoes.add(transacao)
    }

    fun altera(transacao: Transacao, posicao: Int) {
        Companion.transacoes[posicao] = transacao
    }

    fun remove(posicao: Int) {
        Companion.transacoes.removeAt(posicao)
    }

}