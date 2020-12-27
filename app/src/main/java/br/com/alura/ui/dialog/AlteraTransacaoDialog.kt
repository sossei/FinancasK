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
    viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(context, viewGroup) {

    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo
        super.chama(tipo, transacaoDelegate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formatoBrasileiro())
        val categorias = context.resources.getStringArray(categoriaPor(tipo))
        val indice = categorias.indexOf(transacao.categoria)
        campoCategoria.setSelection(indice, true)
    }

}