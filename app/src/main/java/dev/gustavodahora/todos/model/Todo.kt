package dev.gustavodahora.todos.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Define the Table name
@Parcelize
@Entity(tableName = "todo_table")
data class Todo(
    @ColumnInfo var todo: String = "",
    @ColumnInfo var completed: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) : Parcelable
