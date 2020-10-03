package com.example.todolist.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todolist.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ToDoListDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ToDoListDatabase
    private lateinit var dao: ToDoListDatabaseDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ToDoListDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.dao
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun insertTask() = runBlockingTest {
        val task = Task(taskId = 1, title = "title")
        dao.insert(task)
        val byTitle = dao.get(task.taskId)

        assertThat(byTitle.title).isEqualTo(task.title)
    }

    @Test
    fun updateTask() = runBlockingTest {
        val task = Task(taskId = 1, title = "title")
        dao.insert(task)
        dao.insert(task.copy(title = "newTitle"))
        val byTitle = dao.get(task.taskId)

        assertThat(byTitle.title).isEqualTo("newTitle")
    }

    @Test
    fun getAllTasksByStatus() = runBlockingTest {
        val task1 = Task(taskId = 1, title = "titleOne", status = 0)
        val task2 = Task(taskId = 2, title = "titleTwo", status = 1)
        dao.insert(task1)
        dao.insert(task2)

        val byStatus = dao.getAllTasksByStatus(task1.status.toString()).getOrAwaitValue()

        assertThat(byStatus).contains(task1)
        assertThat(byStatus).doesNotContain(task2)
    }

    @Test
    fun deleteTask() = runBlockingTest {
        val task = Task(taskId = 1, title = "title")
        dao.insert(task)
        dao.delete(task)
        val byTitle = dao.get(task.taskId)

        assertThat(byTitle).isNull()
    }

    @Test
    fun deleteListOfTasks() = runBlockingTest {
        val listOfTasks = listOf(
            Task(taskId = 1, title = "titleOne"),
            Task(taskId = 2, title = "titleTwo"),
            Task(taskId = 3, title = "titleThree")
        )
        dao.insert(listOfTasks[0])
        dao.insert(listOfTasks[1])
        dao.insert(listOfTasks[2])
        dao.deleteList(listOfTasks.map { it.taskId })

        val byId = dao.get(listOfTasks[0].taskId)

        assertThat(byId).isNull()
    }
}