package dev.gustavodahora.todos.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.gustavodahora.todos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        listeners()
    }

    private fun listeners() {
        // Start edit text
        mBinding.cntEditText.setOnClickListener {
            showEditTextAndKeyboard()
        }
        // When the keyboard enter button is pressed.
        mBinding.editText.setOnEditorActionListener { view, actionId, event ->
            if (actionId != 0 || event?.action == KeyEvent.ACTION_DOWN) {
                // Action
                Log.i("event", "finish add todo")
                view.text = ""
                false
            } else {
                true
            }
        }
    }

    private fun showEditTextAndKeyboard() {
        mBinding.editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mBinding.editText, InputMethodManager.SHOW_IMPLICIT)
    }
}