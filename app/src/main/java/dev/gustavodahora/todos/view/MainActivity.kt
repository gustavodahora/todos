package dev.gustavodahora.todos.view

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
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
        mBinding.cntEditText.setOnClickListener {
            showEditTextAndKeyboard()
        }
    }

    private fun showEditTextAndKeyboard() {
        mBinding.editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mBinding.editText, InputMethodManager.SHOW_IMPLICIT)
    }
}