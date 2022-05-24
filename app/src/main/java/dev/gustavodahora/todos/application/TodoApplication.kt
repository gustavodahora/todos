package dev.gustavodahora.todos.application

import android.app.Application
import dev.gustavodahora.todos.database.TodoRepository
import dev.gustavodahora.todos.database.TodoRoomDatabase

class TodoApplication : Application() {
    private val database by lazy { TodoRoomDatabase.getDatabase(this@TodoApplication) }
    val repository by lazy { TodoRepository(database.todoDao()) }
}