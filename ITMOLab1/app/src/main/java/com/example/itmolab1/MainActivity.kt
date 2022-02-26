package com.example.itmolab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.HorizontalScrollView
import androidx.core.view.children
import com.example.itmolab1.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.IllegalArgumentException
import android.widget.Button as Button

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var inputTextValue: String = ""
    private var jobEvaluateAutomatic: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("inputTextValue", inputTextValue)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("inputTextValue")?.let { changeInputText(it) }
        jobEvaluateAutomatic = GlobalScope.launch {
            binding.answerText.text = evaluate()
            jobEvaluateAutomatic = null
        }
    }

    private fun changeInputText(newInputText: String) {
        inputTextValue = newInputText
        binding.inputText.text = newInputText
//        binding.inputTextHorizontalScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT) ????
    }

    private fun evaluate(): String {
        return try {
            val e = ExpressionBuilder(inputTextValue).build()
            if (e.validate().isValid) {
                e.evaluate().toString()
            } else {
                "Ошибка"
            }
        } catch (e: IllegalArgumentException) {
            ""
        } catch (e: ArithmeticException) {
            "Ошибка"
        }
    }

    private fun initButtons() {
        for (button in binding.buttonsGrid.children) {
            button.setOnClickListener {
                jobEvaluateAutomatic?.cancel()

                jobEvaluateAutomatic = GlobalScope.launch {
                    delay(500L)
                    binding.answerText.text = evaluate()
                    jobEvaluateAutomatic = null
                }

                when (it.id) {

                    R.id.buttonDelete -> changeInputText(inputTextValue.dropLast(1))

                    R.id.buttonDeleteAll -> changeInputText("")

                    R.id.buttonEquals ->
                        changeInputText(evaluate())

                    else -> changeInputText(inputTextValue + (it as Button).tag)
                }

                binding.answerText.text = ""
            }

        }
    }
}

