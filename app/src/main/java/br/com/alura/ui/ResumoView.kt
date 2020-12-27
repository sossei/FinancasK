package br.com.alura.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import br.com.alura.R
import br.com.alura.extension.formatoBrasileiro
import br.com.alura.model.Resumo
import br.com.alura.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    context: Context,
    private val view: View?,
    transacoes: List<Transacao>
) {
    private val resumo: Resumo = Resumo(transacoes)

    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atuliza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        view?.let {
            with(it.resumo_card_receita) {
                setTextColor(corReceita)
                text = totalReceita.formatoBrasileiro()
            }
        }

    }


    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa
        view?.let {
            with(it.resumo_card_despesa) {
                setTextColor(corDespesa)
                text = totalDespesa.formatoBrasileiro()
            }
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total
        val cor = corPor(total)
        view?.let {
            with(it.resumo_card_total) {
                setTextColor(cor)
                text = total.formatoBrasileiro()
            }
        }
    }

    private fun corPor(total: BigDecimal): Int {
        return if (total >= BigDecimal.ZERO) {
            corReceita
        } else {
            corDespesa
        }
    }


}