package com.example.tutorial2and3card24game

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.singularsys.jep.EvaluationException
import com.singularsys.jep.Jep
import java.lang.Math.abs
import java.text.ParseException

class MainActivity : AppCompatActivity() {
    private lateinit var cards: Array<ImageButton>
    private var rePick: Button? = null
    private var checkInput: Button? = null
    private var clear: Button? = null
    private var left: Button? = null
    private var right: Button? = null
    private var plus: Button? = null
    private var minus: Button? = null
    private var multiply: Button? = null
    private var divide: Button? = null
    private lateinit var expression: TextView

    private lateinit var data: Array<Int>
    private lateinit var card: Array<Int>
    private lateinit var imageCount: Array<Int>

    // another variable to store number of cards selected byh user
    private var clicked: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cards = arrayOf(
            findViewById<ImageButton>(R.id.card1),
            findViewById<ImageButton>(R.id.card2),
            findViewById<ImageButton>(R.id.card3),
            findViewById<ImageButton>(R.id.card4)
        )

        initCardImage()

        /* To test the added library
        var jep = Jep()
        val res: Any = try {
            jep.parse("(3 + 3) * (2 + 2)")
            jep.evaluate()
        }
        catch (e: ParseException) {
        }
        catch (e: EvaluationException) {
        }
        val ca = res as Double
        Toast.makeText(this@MainActivity, ca.toString(), Toast.LENGTH_LONG).show()
        */

        rePick = findViewById<Button>(R.id.repick)
        checkInput = findViewById<Button>(R.id.checkinput)
        left = findViewById<Button>(R.id.left)
        right = findViewById<Button>(R.id.right)
        plus = findViewById<Button>(R.id.plus)
        minus = findViewById<Button>(R.id.minus)
        multiply = findViewById<Button>(R.id.multiply)
        divide = findViewById<Button>(R.id.divide)
        clear = findViewById<Button>(R.id.clear)
        expression = findViewById<TextView>(R.id.input)

        imageCount = arrayOf(0, 0, 0, 0)
        pickCard()

        cards[0].setOnClickListener(View.OnClickListener { clickCard(0) })
        cards[1].setOnClickListener(View.OnClickListener { clickCard(1) })
        cards[2].setOnClickListener(View.OnClickListener { clickCard(2) })
        cards[3].setOnClickListener(View.OnClickListener { clickCard(3) })

        left!!.setOnClickListener { expression.append("(") }
        right!!.setOnClickListener { expression.append(")") }
        plus!!.setOnClickListener { expression.append("+") }
        minus!!.setOnClickListener { expression.append("-") }
        multiply!!.setOnClickListener { expression.append("*") }
        divide!!.setOnClickListener { expression.append("/") }

        clear!!.setOnClickListener { setClear() }


        checkInput!!.setOnClickListener {
            val inputStr: String = expression.text.toString()
            if (checkInput(inputStr)) {
            Toast.makeText(
                this@MainActivity, "Correct Answer",
                Toast.LENGTH_SHORT ).show()
            pickCard() } else {
            Toast.makeText(
                this@MainActivity, "Wrong Answer",
                Toast.LENGTH_SHORT ).show()
            setClear() }
        }

    }

    private fun initCardImage() {
        for (i in 0..3) {
            val resID: Int =
                resources.getIdentifier("back_1", "drawable", "com.example.tutorial2and3card24game")
            cards[i].setImageResource(resID)
        }
    }


    private fun pickCard() {
        data = arrayOf(0, 0, 0, 0)
        card = arrayOf(0, 0, 0, 0)

        // IMPLEMENTATION OF HOMEWORK TASK 5.1 (Tutorial 3)
        rand1 = (0..52).Random()
        rand2 = (0..52).Random()
        rand3 = (0..52).Random()
        rand4 = (0..52).Random()

        // Generate random. If a random number is repeated, the next suit is automatically chosen

        card[0] = rand1
        card[1] = rand2
        card[2] = rand3
        card[3] = rand4
        data[0] = rand1
        data[1] = rand2
        data[2] = rand3
        data[3] = rand4

        setClear()

    }

    private fun setClear() {
        var resID: Int
        expression.text = ""
        for (i in 0..3) {
            imageCount[i] = 0
            resID = resources.getIdentifier(
                "card" + card[i],
                "drawable",
                "com.example.tutorial2and3card24game"
            )
            cards[i].setImageResource(resID)
            cards[i].isClickable = true
        }

    }

    private fun clickCard(i: Int) {
        val resId: Int
        val num: String
        val value: Int
        if (imageCount[i] == 0) {
            resId =
                resources.getIdentifier("back_0", "drawable", "com.example.tutorial2and3card24game")
            cards[i].setImageResource(resId)
            cards[i].isClickable = false
            clicked -= 1

            value = data[i]
            num = value.toString()
            expression.append(num)
            imageCount[i]++
        }
    }

    private fun checkInput(input: String): Boolean {

        // IMPLEMENTATION OF HOMEWORK TASK 5.2 (TUTORIAL 3)
        // Global variable clicked should be 0 for further evaluation
        // Means that the player should have selected exactly 4 cards to make 24
        if (clicked != 0){
            Toast.makeText(
                this@MainActivity,
                "Wrong Expression", Toast.LENGTH_SHORT ).show()
            return false
        }

        val jep = Jep()
        val res: Any = try { jep.parse(input)
            jep.evaluate()
        } catch (e: ParseException) {
            e.printStackTrace()
            Toast.makeText(
                    this@MainActivity,
            "Wrong Expression", Toast.LENGTH_SHORT ).show()
            return false
        } catch (e: EvaluationException) { e.printStackTrace()
            Toast.makeText( this@MainActivity,
                "Wrong Expression", Toast.LENGTH_SHORT ).show()
            return false }

        val ca = res as Double
        return abs(ca - 24) < 1e-6 }
    
}

