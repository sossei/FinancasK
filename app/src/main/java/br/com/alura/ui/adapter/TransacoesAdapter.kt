package br.com.alura.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import br.com.alura.R
import br.com.alura.extension.formatoBrasileiro
import br.com.alura.extension.limitaEmAte
import br.com.alura.model.Tipo
import br.com.alura.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class TransacoesAdapter(
    private val transacoes: List<Transacao>,
    private val context: Context
) : BaseAdapter() {

    private val limiteDaCategoria: Int = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
        val transacao = transacoes[position]
        adicionaValor(transacao, view)
        adicionaIcon(transacao,view)
        adicionaCategoria(view, transacao)
        adicionaData(view, transacao)
        return view

    }

    private fun adicionaData(view: View, transacao: Transacao) {
        view.transacao_data.text = transacao.data.formatoBrasileiro()
    }

    private fun adicionaCategoria(view: View, transacao: Transacao) {
        view.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcon(transacao: Transacao, view: View) {
        val icone = iconPor(transacao.tipo)
        view.transacao_icone.setBackgroundResource(icone)
    }

    private fun iconPor(tipo: Tipo): Int {
        return when (tipo) {
            Tipo.RECEITA -> R.drawable.icone_transacao_item_receita
            Tipo.DESPESA -> R.drawable.icone_transacao_item_despesa
        }
    }

    private fun adicionaValor(transacao: Transacao, view: View) {
        val cor: Int = corPor(transacao.tipo)
        view.transacao_valor.setTextColor(cor)
        view.transacao_valor.text = transacao.valor.formatoBrasileiro()
    }

    private fun corPor(tipo: Tipo): Int {
        return when (tipo) {
            Tipo.RECEITA -> ContextCompat.getColor(
                context,
                R.color.receita
            )
            Tipo.DESPESA -> ContextCompat.getColor(
                context,
                R.color.despesa
            )
        }
    }

    override fun getItem(position: Int): Any {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }

}