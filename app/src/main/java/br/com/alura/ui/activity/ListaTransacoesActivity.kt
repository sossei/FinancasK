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
    private val viewDaActivity by lazy {
        window.decorView
    }
    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

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
            viewGroupDaActivity,
            this
        ).chama(tipo,
            object :
                TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    adiciona(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }

            })
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTranscaoes()
    }


    private fun atualizaTranscaoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atuliza()
    }

    private fun configuraLista() {
        with(lista_transacoes_listview) {
            adapter = TransacoesAdapter(transacoes, this@ListaTransacoesActivity)
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)
            }
        }
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
            .chama(transacao, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    altera(posicao, transacao)
                }

            })
    }

    private fun altera(position: Int, transacao: Transacao) {
        transacoes[position] = transacao
        atualizaTranscaoes()
    }

}