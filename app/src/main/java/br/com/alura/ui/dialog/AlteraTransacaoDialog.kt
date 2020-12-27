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

class AlteraTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {
    private val viewDoDialog = criaLayout()
    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo
        configuraCategoria(tipo)
        configuraCampoData()
        configuraFormulario(tipo, transacaoDelegate)
        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formatoBrasileiro())
        val indice =
            context.resources.getStringArray(categoriaPor(tipo)).indexOf(transacao.categoria)
        campoCategoria.setSelection(indice, true)

    }

    private val campoValor = viewDoDialog.form_transacao_valor
    private val campoData = viewDoDialog.form_transacao_data
    private val campoCategoria = viewDoDialog.form_transacao_categoria

    private fun configuraFormulario(
        tipo: Tipo,
        transacaoDelegate: TransacaoDelegate
    ) {
        val titulo = tituloPor(tipo)
        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewDoDialog)
            .setPositiveButton("Alterar") { _, _ ->
                val valorTexto = campoValor.text.toString()
                val dataTexto = campoData.text.toString()
                val categoriaTexto = campoCategoria.selectedItem.toString()
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

    private fun tituloPor(tipo: Tipo) =
        if (tipo == Tipo.RECEITA) R.string.altera_receita else R.string.altera_despesa

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
        val categoria = categoriaPor(tipo)
        val adapter = ArrayAdapter.createFromResource(
            context,
            categoria,
            android.R.layout.simple_spinner_dropdown_item
        )
        campoCategoria.adapter = adapter
    }

    private fun categoriaPor(tipo: Tipo) =
        if (tipo == Tipo.RECEITA) R.array.categorias_de_receita else R.array.categorias_de_despesa

    private fun configuraCampoData() {
        with(campoData) {
            val hoje = Calendar.getInstance()
            val ano = hoje.get(Calendar.YEAR)
            val mes = hoje.get(Calendar.MONTH)
            val dia = hoje.get(Calendar.DAY_OF_MONTH)
            setText(hoje.formatoBrasileiro())
            setOnClickListener {
                DatePickerDialog(
                    context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        with(this) { setText(dataSelecionada.formatoBrasileiro()) }

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