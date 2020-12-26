package br.com.alura.delegate

import br.com.alura.model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao: Transacao)
}