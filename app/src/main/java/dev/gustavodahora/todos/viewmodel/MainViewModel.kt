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

    fun getListData() {
        val arrayList = ArrayList<Todo>()
        arrayList.add(Todo("Todo 1", false))
        arrayList.add(Todo("Todo 2", false))
        arrayList.add(Todo("Todo 3", false))
        arrayList.add(Todo("Todo 4", false))
        arrayList.add(Todo("Todo 5", true))
        arrayList.add(Todo("Todo 6", true))
        arrayList.add(Todo("Todo 7", true))
        arrayList.add(Todo("Todo 8", true))
        arrayList.add(Todo("Todo 9", true))
        arrayList.add(Todo("Todo 10", true))
        arrayList.add(Todo("Todo 11", true))
        arrayList.add(Todo("Todo 12", true))
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