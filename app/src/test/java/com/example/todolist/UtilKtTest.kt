package com.example.todolist

import com.example.todolist.util.smartTruncate
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilKtTest {

    @Test
    fun truncateOnHalfOfWord() {
        val subject = "Hello world"
        val result = subject.smartTruncate(2)

        assertThat(result).isEqualTo("Hello...")
    }

    @Test
    fun truncateOnSpacePunctuation() {
        val subject = "Hello world"
        val result = subject.smartTruncate(5)

        assertThat(result).isEqualTo("Hello...")
    }

    @Test
    fun truncateOnOtherPunctuation() {
        val subject = "Hello, world"
        val result = subject.smartTruncate(6)

        assertThat(result).isEqualTo("Hello...")
    }

    @Test
    fun truncateAll() {
        val subject = "Hello world"
        val result = subject.smartTruncate(11)

        assertThat(result).isEqualTo("Hello world")
    }
}