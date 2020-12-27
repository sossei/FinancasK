package br.com.alura.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import br.com.alura.R
import br.com.alura.delegate.TransacaoDelegate
import br.com.alura.model.Tipo
import br.com.alura.model.Transacao
import br.com.alura.ui.ResumoView
import br.com.alura.ui.adapter.TransacoesAdapter
import br.com.alura.ui.dialog.AdicionaTransacaoDialog
import br.com.alura.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : Activity() {
    private val transacoes: MutableList<Transacao> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }
        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(
            window.decorView as ViewGroup,
            this
        ).chama(tipo,
            object :
                TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes.add(transacao)
                    atualizaTranscaoes()
                    lista_transacoes_adiciona_menu.close(true)
                }

            })
    }


    private fun atualizaTranscaoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atuliza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = TransacoesAdapter(transacoes, this)
        lista_transacoes_listview.setOnItemClickListener { parent, view, position, id ->
            val transacao = transacoes[position]
            AlteraTransacaoDialog(window.decorView as ViewGroup, this)
                .chama(transacao, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        transacoes[position] = transacao
                        atualizaTranscaoes()
                    }

                })
        }
    }

}