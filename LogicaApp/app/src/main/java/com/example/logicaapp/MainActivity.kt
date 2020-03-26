package com.example.logicaapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Corect_Answers = arrayOf("T", "F", "t", "f")

        var correct = 0

        checkButton.setOnClickListener(){
            val input: String = answerInput.text.toString()
            val input1: String = answerInput1.text.toString()
            val input2: String = answerInput2.text.toString()
            val input3: String = answerInput3.text.toString()

            if (input.trim().length == 0 || input1.trim().length == 0 || input2.trim().length == 0 || input3.trim().length == 0){
                Toast.makeText(this,"Please make sure all inputs are filled in, the input fields cannot be blank",Toast.LENGTH_LONG).show()
            }else{
                if (input == Corect_Answers[0] || input == Corect_Answers[2]) { correct++ }
                if (input1 == Corect_Answers[1] || input1 == Corect_Answers[3]) { correct++ }
                if (input2 == Corect_Answers[1] || input2 == Corect_Answers[3]) { correct++ }
                if (input3 == Corect_Answers[1] || input3 == Corect_Answers[3]) { correct++ }

                if (correct > 4){ correct = 4}

                Toast.makeText(this,"$correct of 4 anwers are correct",Toast.LENGTH_LONG).show()
            }
        }

    }
}
