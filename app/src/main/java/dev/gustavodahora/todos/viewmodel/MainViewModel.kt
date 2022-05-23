package dev.gustavodahora.todos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.gustavodahora.todos.model.Todo
import dev.gustavodahora.todos.model.TypeList

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var _listTodo = MutableLiveData<List<Todo>>()
    var listTodo = _listTodo

    private var _typeList = MutableLiveData<TypeList>()
    var typeList = _typeList

    var isEmptyList = false

    fun getListData() {
        val arrayList = ArrayList<Todo>()
        repeat(10) { index -> arrayList.add(Todo("Task $index", false)) }
        isEmptyList = arrayList.size <= 0
        when (typeList.value) {
            TypeList.ALL -> {
                _listTodo.value = arrayList
            }
            TypeList.ACTIVE -> {
                _listTodo.value = arrayList.filter { todo -> !todo.completed }
            }
            TypeList.COMPLETED -> {
                _listTodo.value = arrayList.filter { todo -> todo.completed }
            }
            else -> {
                _listTodo.value = arrayList
            }
        }
    }

    fun setupFilter(type: TypeList) {
        _typeList.value = type
    }
}