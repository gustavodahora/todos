package dev.gustavodahora.todos.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.gustavodahora.todos.R
import dev.gustavodahora.todos.adapter.TodoAdapter
import dev.gustavodahora.todos.application.TodoApplication
import dev.gustavodahora.todos.database.TodoRepository
import dev.gustavodahora.todos.databinding.ActivityMainBinding
import dev.gustavodahora.todos.model.Todo
import dev.gustavodahora.todos.model.TypeList
import dev.gustavodahora.todos.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.TodoViewModelFactory((application as TodoApplication).repository)
    }
    private var arrayListFilter: List<Todo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setFilter(TypeList.ALL)
        listeners()
        observable()
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
                viewModel.insertNewItem(view.text.toString())
                view.text = ""
                false
            } else {
                true
            }
        }
        mBinding.tvAll.setOnClickListener {
            setFilter(TypeList.ALL)
        }
        mBinding.tvActive.setOnClickListener {
            setFilter(TypeList.ACTIVE)
        }
        mBinding.tvCompleted.setOnClickListener {
            setFilter(TypeList.COMPLETED)
        }
    }

    private fun observable() {
        viewModel.listTodo.observe(this) {
            setTypeAndStartRecycle(it)
        }
        viewModel.typeList.observe(this) {
            controlFilterAndStartRecycle(it)
        }
    }

    private fun setTypeAndStartRecycle(list: List<Todo>) {
        when (viewModel.typeList.value) {
            TypeList.ALL -> {
                arrayListFilter = list
            }
            TypeList.ACTIVE -> {
                arrayListFilter = list.filter { todo -> !todo.completed }
            }
            TypeList.COMPLETED -> {
                arrayListFilter = list.filter { todo -> todo.completed }
            }
            else -> {
                arrayListFilter = list
            }
        }
        validateCompletedItemsCount()
        startRecycle(arrayListFilter)
    }

    private fun showEditTextAndKeyboard() {
        mBinding.editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mBinding.editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun showRecycleControls() {
        mBinding.cntRecycleControl.visibility = View.VISIBLE
        mBinding.imgKeyboardDown.visibility = View.VISIBLE
    }

    private fun hideRecycleControls() {
        mBinding.cntRecycleControl.visibility = View.INVISIBLE
        mBinding.imgKeyboardDown.visibility = View.INVISIBLE
    }

    private fun startRecycle(list: List<Todo>) {
        if (list.isNotEmpty() || viewModel.listTodo.value?.isNotEmpty() == true) {
            mBinding.recyclerViewListItem.layoutManager = LinearLayoutManager(this)
            mBinding.recyclerViewListItem.adapter = TodoAdapter(list, context = applicationContext, this@MainActivity)
            showRecycleControls()
        } else {
            hideRecycleControls()
        }
    }

    private fun setFilter(type: TypeList) {
        viewModel.setupFilter(type)
    }

    private fun controlFilterAndStartRecycle(type: TypeList) {
        viewModel.listTodo.value?.let { setTypeAndStartRecycle(it) }
        startRecycle(arrayListFilter)
        mBinding.tvAll.background = null
        mBinding.tvActive.background = null
        mBinding.tvCompleted.background = null

        when (type) {
            TypeList.ALL -> {
                mBinding.tvAll.background = ContextCompat.getDrawable(this, R.drawable.shape_line)
            }
            TypeList.ACTIVE -> {
                mBinding.tvActive.background = ContextCompat.getDrawable(this, R.drawable.shape_line)
            }
            TypeList.COMPLETED -> {
                mBinding.tvCompleted.background = ContextCompat.getDrawable(this, R.drawable.shape_line)
            }
        }
    }

    private fun validateCompletedItemsCount() {
        val array = viewModel.listTodo.value?.filter { todo -> !todo.completed }
        mBinding.tvItemCount.text = getString(R.string.item_left, array?.size)
    }

    fun updateItemRepo(todo: Todo) {
        viewModel.updateItem(todo)
    }

    fun deleteItemRepo(todo: Todo) {
        viewModel.deleteItemRepo(todo)
    }
}