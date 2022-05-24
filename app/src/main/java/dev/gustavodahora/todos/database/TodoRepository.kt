package dev.gustavodahora.todos.database

import androidx.annotation.WorkerThread
import dev.gustavodahora.todos.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    @WorkerThread
    suspend fun insertTodoData(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTodoList: Flow<List<Todo>> = todoDao.getAllDishesList()

    @WorkerThread
    suspend fun updateTodoData(todo: Todo) {
        todoDao.updateTodoData(todo)
    }

    @WorkerThread
    suspend fun deleteTodoData(todo: Todo) {
        todoDao.deleteTodoData(todo)
    }
}