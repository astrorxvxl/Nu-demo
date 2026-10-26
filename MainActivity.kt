package com.example.nubankdemo

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import java.text.NumberFormat
import java.util.Locale

class MainActivity : Activity() {
    private val purple = Color.rgb(108, 42, 220)
    private val ink = Color.rgb(35, 28, 48)
    private val muted = Color.rgb(111, 103, 122)
    private val pageBg = Color.rgb(248, 246, 251)
    private var balance = 1250.75
    private lateinit var balanceView: TextView
    private lateinit var amountInput: EditText

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun money(v: Double) = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(v)

    private fun label(value: String, size: Float, color: Int, bold: Boolean = false) = TextView(this).apply {
        text = value; textSize = size; setTextColor(color)
        if (bold) typeface = Typeface.DEFAULT_BOLD
        setPadding(0, dp(3), 0, dp(3))
    }

    private fun actionButton(title: String, action: () -> Unit) = Button(this).apply {
        text = title; isAllCaps = false; textSize = 14f
        setOnClickListener { action() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = pageBg
        window.navigationBarColor = pageBg
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        render()
    }

    private fun render() {
        val scroll = ScrollView(this).apply { setBackgroundColor(pageBg) }
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(20), dp(22), dp(28))
        }
        scroll.addView(page); setContentView(scroll)

        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        top.addView(label("nu", 32f, purple, true), LinearLayout.LayoutParams(0, -2, 1f))
        top.addView(label("APP DE DEMONSTRAÇÃO", 10f, muted, true))
        page.addView(top)
        page.addView(label("Olá, você 👋", 25f, ink, true).apply { setPadding(0, dp(24), 0, dp(4)) })
        page.addView(label("Uma carteira fictícia, com saldo editável.", 14f, muted))

        val balanceCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; setPadding(dp(18), dp(16), dp(18), dp(18)); setBackgroundColor(Color.WHITE)
        }
        val cardParams = LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(20), 0, dp(16)) }
        page.addView(balanceCard, cardParams)
        balanceCard.addView(label("Saldo de demonstração", 14f, muted))
        balanceView = label(money(balance), 29f, ink, true).apply { setPadding(0, dp(8), 0, dp(8)) }
        balanceCard.addView(balanceView)
        balanceCard.addView(label("Valor fictício • nenhuma conta conectada", 12f, muted))

        page.addView(label("Editar saldo fictício", 18f, ink, true).apply { setPadding(0, dp(10), 0, dp(6)) })
        amountInput = EditText(this).apply {
            hint = "Ex.: 2500,00"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setSingleLine(true); setTextColor(ink); setPadding(dp(12), dp(8), dp(12), dp(8)); setBackgroundColor(Color.WHITE)
        }
        page.addView(amountInput, LinearLayout.LayoutParams(-1, dp(54)))
        page.addView(actionButton("Salvar saldo fictício") { saveBalance() }, LinearLayout.LayoutParams(-1, dp(52)))

        page.addView(label("Atalhos rápidos", 18f, ink, true).apply { setPadding(0, dp(20), 0, dp(6)) })
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        row.addView(actionButton("+ R$ 50") { changeBalance(50.0) }, LinearLayout.LayoutParams(0, dp(50), 1f))
        row.addView(actionButton("− R$ 50") { changeBalance(-50.0) }, LinearLayout.LayoutParams(0, dp(50), 1f))
        page.addView(row)

        page.addView(label("Movimentações de exemplo", 18f, ink, true).apply { setPadding(0, dp(20), 0, dp(8)) })
        page.addView(activity("Compra de exemplo", "Hoje • fictícia", "− R$ 32,90"))
        page.addView(activity("Entrada de exemplo", "Ontem • fictícia", "+ R$ 150,00"))
        page.addView(label("INDEPENDENTE • NÃO OFICIAL • SEM CONEXÃO BANCÁRIA", 9f, muted, true).apply {
            gravity = Gravity.CENTER; setPadding(0, dp(24), 0, dp(8))
        })
    }

    private fun activity(title: String, subtitle: String, amount: String) = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
        setPadding(dp(12), dp(9), dp(12), dp(9)); setBackgroundColor(Color.WHITE)
        val details = LinearLayout(this@MainActivity).apply {
            orientation = LinearLayout.VERTICAL
            addView(label(title, 14f, ink, true)); addView(label(subtitle, 11f, muted))
        }
        addView(details, LinearLayout.LayoutParams(0, -2, 1f)); addView(label(amount, 13f, ink, true))
        layoutParams = LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(3), 0, dp(3)) }
    }

    private fun saveBalance() {
        val raw = amountInput.text.toString().trim().replace(".", "").replace(",", ".")
        val parsed = raw.toDoubleOrNull()
        if (parsed == null || parsed < 0 || parsed > 999999999.99) {
            Toast.makeText(this, "Digite um valor válido, por exemplo 2500,00.", Toast.LENGTH_SHORT).show(); return
        }
        balance = parsed; balanceView.text = money(balance)
        Toast.makeText(this, "Saldo fictício atualizado.", Toast.LENGTH_SHORT).show()
    }

    private fun changeBalance(delta: Double) {
        balance = (balance + delta).coerceAtLeast(0.0)
        balanceView.text = money(balance)
    }
}
