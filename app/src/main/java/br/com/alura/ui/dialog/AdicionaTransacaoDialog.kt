package br.com.alura.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.alura.R
import br.com.alura.delegate.TransacaoDelegate
import br.com.alura.extension.converParaCalendar
import br.com.alura.extension.formatoBrasileiro
import br.com.alura.model.Tipo
import br.com.alura.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {
    private val viewDoDialog = criaLayout()
    fun chama(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        configuraCategoria(tipo)
        configuraCampoData()
        configuraFormulario(tipo,transacaoDelegate)
    }

    private fun configuraFormulario(
        tipo: Tipo,
        transacaoDelegate: TransacaoDelegate
    ) {
        val titulo = if(tipo == Tipo.RECEITA)R.string.adiciona_receita else R.string.adiciona_despesa
        AlertDialog.Builder(context)
            .setTitle(R.string.adiciona_receita)
            .setView(viewDoDialog)
            .setPositiveButton("Adicionar") { _, _ ->
                val valorTexto = viewDoDialog.form_transacao_valor.text.toString()
                val dataTexto = viewDoDialog.form_transacao_data.text.toString()
                val categoriaTexto =
                    viewDoDialog.form_transacao_categoria.selectedItem.toString()
                val valor = converteCampoValor(valorTexto)
                val data = dataTexto.converParaCalendar()
                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaTexto
                )
                transacaoDelegate.delegate(transacaoCriada)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (e: NumberFormatException) {
            Toast.makeText(
                context,
                "Falha na conversão de valor",
                Toast.LENGTH_LONG
            ).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCategoria(tipo: Tipo) {
        val categoria = if(tipo == Tipo.RECEITA) R.array.categorias_de_receita else R.array.categorias_de_despesa
        val adapter = ArrayAdapter.createFromResource(
            context,
            categoria,
            android.R.layout.simple_spinner_dropdown_item
        )
        viewDoDialog.form_transacao_categoria.adapter = adapter
    }

    private fun configuraCampoData() {
        with(viewDoDialog.form_transacao_data) {
            val hoje = Calendar.getInstance()
            val ano = hoje.get(Calendar.YEAR)
            val mes = hoje.get(Calendar.MONTH)
            val dia = hoje.get(Calendar.DAY_OF_MONTH)
            setText(hoje.formatoBrasileiro())
            setOnClickListener {
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        with(viewDoDialog.form_transacao_data) { setText(dataSelecionada.formatoBrasileiro()) }

                    }, ano, mes, dia
                ).show()
            }
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.form_transacao,
                viewGroup,
                false
            )
    }
}