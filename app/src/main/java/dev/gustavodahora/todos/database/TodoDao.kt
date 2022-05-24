package dev.gustavodahora.todos.database

import androidx.room.*
import dev.gustavodahora.todos.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    // Insert a new item on database
    @Insert
    suspend fun insertTodo(todo: Todo)

    // Get all items on Database
    @Query("SELECT * FROM todo_table ORDER BY ID")
    fun getAllDishesList(): Flow<List<Todo>>

    @Update
    suspend fun updateTodoData(todo: Todo)

    @Delete
    suspend fun deleteTodoData(todo: Todo)
}