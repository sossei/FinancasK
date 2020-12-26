package br.com.alura.ui.activity

import android.app.Activity
import android.os.Bundle
import br.com.alura.R
import br.com.alura.model.Resumo
import br.com.alura.model.Tipo
import br.com.alura.model.Transacao
import br.com.alura.ui.ResumoView
import br.com.alura.ui.adapter.TransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListaTransacoesActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = transacoesDeExemplo()

        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atuliza()

        configuraLista(transacoes)
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = TransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(
                valor = BigDecimal("20.5"),
                categoria = "Comida",
                tipo = Tipo.DESPESA
            ),
            Transacao(
                valor = BigDecimal("50.0"),
                categoria = "Economia",
                tipo = Tipo.RECEITA
            ),
            Transacao(
                valor = BigDecimal("50.0"),
                categoria = "Despesa",
                tipo = Tipo.DESPESA
            ),
            Transacao(
                valor = BigDecimal("500.0"),
                categoria = "Receita",
                tipo = Tipo.RECEITA
            ),
            Transacao(
                valor = BigDecimal("500.0"),
                categoria = "Despesa",
                tipo = Tipo.DESPESA
            )
        )
    }
}