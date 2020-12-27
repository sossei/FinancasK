package br.com.alura.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.alura.R
import br.com.alura.model.Tipo

class AdicionaTransacaoDialog(
    viewGroup: ViewGroup,
    context: Context
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo) =
        if (tipo == Tipo.RECEITA) R.string.adiciona_receita else R.string.adiciona_despesa

}