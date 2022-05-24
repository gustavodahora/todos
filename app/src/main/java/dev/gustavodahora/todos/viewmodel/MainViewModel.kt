package dev.gustavodahora.todos.viewmodel

import androidx.lifecycle.*
import dev.gustavodahora.todos.database.TodoRepository
import dev.gustavodahora.todos.model.Todo
import dev.gustavodahora.todos.model.TypeList
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TodoRepository) : ViewModel() {
    var listTodo = repository.allTodoList.asLiveData()

    private var _typeList = MutableLiveData<TypeList>()
    var typeList = _typeList

    var isEmptyList = false

    fun insertNewItem(text: String) {
        viewModelScope.launch {
            val todo = Todo(text, false)
            repository.insertTodoData(todo)
        }
    }

    fun updateItem(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodoData(todo)
        }
    }

    fun setupFilter(type: TypeList) {
        _typeList.value = type
    }

    class TodoViewModelFactory(private val repository: TodoRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}