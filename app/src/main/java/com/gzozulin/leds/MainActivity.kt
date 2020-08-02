package com.gzozulin.leds

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class MainActivity : AppCompatActivity() {

    @VisibleForTesting
    internal lateinit var viewModel: LedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = LedViewModel.fromActivity(this)
        updateUi()
        receiveInput()
    }

    private fun updateUi() {
        viewModel.state.observe(this, Observer<LedState> { state ->
            answer.text = state.answer.joinToString()
            guess.text = state.guess.joinToString()
            // 1 - for each guess create a corresponding color
            val colors = mutableListOf<Int>()
            state.guess.forEachIndexed { index, guess ->
                colors.add(when {
                    state.answer[index] == guess -> R.color.green
                    state.answer.contains(guess) -> R.color.orange
                    else -> R.color.red
                })
            }
            // 2 - map this list onto leds in reversed order
            val reversed = colors.asReversed()
            listOf<View>(led_3, led_2, led_1).forEach { led ->
                val color = ContextCompat.getColor(this,
                    if (reversed.isNotEmpty()) {
                        reversed.removeAt(0)
                    } else {
                        R.color.dark_grey
                    })
                led.background.mutate().colorFilter =
                    PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        })
    }

    private fun receiveInput() {
        button_a.setOnClickListener { guessSymbol('a') }
        button_b.setOnClickListener { guessSymbol('b') }
        button_c.setOnClickListener { guessSymbol('c') }
    }

    private fun guessSymbol(symbol: Char) {
        if (viewModel.guess(symbol)) {
            konfetti.launch()
            SuccessDialog().show(supportFragmentManager, "success")
        }
    }
}

private fun KonfettiView.launch() {
    build()
        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.BLUE, Color.RED)
        .setDirection(0.0, 359.0)
        .setSpeed(1f, 5f)
        .setFadeOutEnabled(true)
        .setTimeToLive(2000L)
        .addShapes(Shape.Square, Shape.Circle)
        .addSizes(Size(12))
        .setPosition(-50f, width + 50f, -50f, -50f)
        .streamFor(100, 1500L)
}

class SuccessDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return AlertDialog.Builder(context!!)
            .setTitle("Winner!")
            .setMessage("Retry?")
            .setPositiveButton("GO") { _, _ -> LedViewModel.fromActivity(activity as MainActivity).reset() }
            .setNegativeButton("LEAVE") { _, _ -> activity!!.finish() }
            .create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        LedViewModel.fromActivity(activity as MainActivity).reset()
        super.onDismiss(dialog)
    }
}