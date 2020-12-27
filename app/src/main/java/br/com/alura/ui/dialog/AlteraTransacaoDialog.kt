package br.com.alura.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.alura.R
import br.com.alura.delegate.TransacaoDelegate
import br.com.alura.extension.formatoBrasileiro
import br.com.alura.model.Tipo
import br.com.alura.model.Transacao

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo
        super.chama(tipo, transacaoDelegate)

        inicializaCampos(transacao)
    }

    private fun inicializaCampos(
        transacao: Transacao
    ) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(
        transacao: Transacao
    ) {
        val tipo = transacao.tipo
        val categorias = context.resources.getStringArray(categoriaPor(tipo))
        val indice = categorias.indexOf(transacao.categoria)
        campoCategoria.setSelection(indice, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formatoBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo) =
        if (tipo == Tipo.RECEITA) R.string.altera_receita else R.string.altera_despesa
}