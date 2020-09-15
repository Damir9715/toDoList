package com.example.todolist

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.database.ToDoListDatabaseDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ToDoListDatabaseTest {

//    private lateinit var toDoListDao: ToDoListDatabaseDao
//    private lateinit var db: ToDoListDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//
//        db = Room.inMemoryDatabaseBuilder(context, ToDoListDatabase::class.java)
//            .allowMainThreadQueries()
//            .build()
//        toDoListDao = db.toDoListDatabaseDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetTask() {
//        val task = Task(title = "title", description = "description")
//        toDoListDao.insert(task)
//        val found = toDoListDao.getLast()
//        assertEquals(task.description, found.description)
//    }
}