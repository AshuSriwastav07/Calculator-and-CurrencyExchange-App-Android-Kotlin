package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.ScrollCaptureCallback
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.mycalculator.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class MainActivity : AppCompatActivity() {

    lateinit var button1:android.widget.Button
    lateinit var button2:android.widget.Button
    lateinit var button3:android.widget.Button
    lateinit var button4:android.widget.Button
    lateinit var button5:android.widget.Button
    lateinit var button6:android.widget.Button
    lateinit var button7:android.widget.Button
    lateinit var button8:android.widget.Button
    lateinit var button9:android.widget.Button
    lateinit var button0:android.widget.Button
    lateinit var button00:android.widget.Button
    lateinit var buttonpercent:android.widget.Button
    lateinit var buttonclear:android.widget.Button
    lateinit var buttondot:android.widget.Button
    lateinit var buttonEqual:android.widget.Button
    lateinit var buttonAdd:android.widget.Button
    lateinit var buttonSub:android.widget.Button
    lateinit var buttonMultiply:android.widget.Button
    lateinit var buttonDivide:android.widget.Button
    lateinit var buttonBackspace:android.widget.Button

    lateinit var inputText:EditText
    lateinit var ResultText:EditText
    var check:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()

        button1=findViewById(R.id.button1)
        button2=findViewById(R.id.button2)
        button3=findViewById(R.id.button3)
        button4=findViewById(R.id.button4)
        button5=findViewById(R.id.button5)
        button6=findViewById(R.id.button6)
        button7=findViewById(R.id.button7)
        button8=findViewById(R.id.button8)
        button9=findViewById(R.id.button9)
        button0=findViewById(R.id.button0)
        button00=findViewById(R.id.button00)
        buttonAdd=findViewById(R.id.buttonPlus)
        buttonSub=findViewById(R.id.buttonMinus)
        buttonMultiply=findViewById(R.id.buttonMultiply)
        buttonDivide=findViewById(R.id.buttonDivide)
        buttondot=findViewById(R.id.buttonDot)
        buttonEqual=findViewById(R.id.buttonEqual)
        buttonBackspace=findViewById(R.id.buttonBackspace)
        buttonclear=findViewById(R.id.buttonClear)
        buttonpercent=findViewById(R.id.buttonModule)
        inputText=findViewById(R.id.inputNumber)
        ResultText=findViewById(R.id.ResultShow)


        inputText.movementMethod=ScrollingMovementMethod()
        inputText.setActivated(true)
        inputText.setPressed(true)
        var text:String

        button1.setOnClickListener{
            text=inputText.text.toString()+"1"
            inputText.setText(text)
            ShowResult(text)

        }

        button2.setOnClickListener{
            text=inputText.text.toString()+"2"
            inputText.setText(text)
            ShowResult(text)

        }

        button3.setOnClickListener{
            text=inputText.text.toString()+"3"
            inputText.setText(text)
            ShowResult(text)

        }

        button4.setOnClickListener{
            text=inputText.text.toString()+"4"
            inputText.setText(text)
            ShowResult(text)

        }

        button5.setOnClickListener{
            text=inputText.text.toString()+"5"
            inputText.setText(text)
            ShowResult(text)

        }

        button6.setOnClickListener{
            text=inputText.text.toString()+"6"
            inputText.setText(text)
            ShowResult(text)

        }

        button7.setOnClickListener{
            text=inputText.text.toString()+"7"
            inputText.setText(text)
            ShowResult(text)

        }

        button8.setOnClickListener{
            text=inputText.text.toString()+"8"
            inputText.setText(text)
            ShowResult(text)

        }

        button9.setOnClickListener{
            text=inputText.text.toString()+"9"
            inputText.setText(text)
            ShowResult(text)

        }

        button0.setOnClickListener{
            text=inputText.text.toString()+"0"
            inputText.setText(text)
            ShowResult(text)

        }

        button00.setOnClickListener{
            text=inputText.text.toString()+"00"
            inputText.setText(text)
            ShowResult(text)

        }

        buttondot.setOnClickListener{
            text=inputText.text.toString()+"."
            inputText.setText(text)
            ShowResult(text)

        }

        buttonAdd.setOnClickListener{
            text=inputText.text.toString()+"+"
            inputText.setText(text)
            check=check+1
        }

        buttonSub.setOnClickListener{
            text=inputText.text.toString()+"-"
            inputText.setText(text)
            check=check+1
        }

        buttonMultiply.setOnClickListener{
            text=inputText.text.toString()+"*"
            inputText.setText(text)
            check=check+1
        }

        buttonDivide.setOnClickListener{
            text=inputText.text.toString()+"/"
            inputText.setText(text)
            check=check+1
        }

        buttonpercent.setOnClickListener{
            text=inputText.text.toString()+"%"
            inputText.setText(text)
            check=check+1
        }

        buttonEqual.setOnClickListener{
            text=ResultText.text.toString()
            inputText.setText(text)
            ResultText.setText(null)
        }


        buttonclear.setOnClickListener{
            inputText.setText(null)
            ResultText.setText(null)
        }

        buttonBackspace.setOnClickListener {
            val inputTextLength = inputText.text.length

            if (inputTextLength > 1) {
                val stringBuilder = StringBuilder(inputText.text)
                val lastValue = inputText.text.toString().last()

                if (lastValue == '+' || lastValue == '-' || lastValue == '*' || lastValue == '/' || lastValue == '%') {
                    check--
                }

                stringBuilder.deleteCharAt(inputTextLength - 1)
                val backSpace = stringBuilder.toString()
                inputText.setText(backSpace)
                ShowResult(backSpace)
            } else if (inputTextLength == 1) {
                // Set input text to null when only one digit is remaining
                inputText.setText(null)
            }
        }


    }


    private fun ShowResult(text: String) {

        val engine:ScriptEngine=ScriptEngineManager().getEngineByName("rhino")
        try{
            val result:Any=engine.eval(text)
            val mainResult=result.toString()
            if(check==0){
                ResultText.setText(null)

            }else{
                ResultText.setText(mainResult)
            }


        }catch (e: ScriptException){

            Log.d("CalculationError","Error")

        }
    }

}