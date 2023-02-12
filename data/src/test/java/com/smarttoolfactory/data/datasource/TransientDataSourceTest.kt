package com.smarttoolfactory.data.datasource

import com.google.common.truth.Truth
import com.smarttoolfactory.data.model.Transaction
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.LinkedList

/**
 * Used single instance of tests instead of creating new instance for each test.
 *
 * @see <a href="https://phauer.com/2018/best-practices-unit-testing-kotlin/">Kotlin Test Performance</a>
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransientDataSourceTest {

    private lateinit var dataSource: TransientDataSource

    private val list = LinkedList<Transaction>()

    @Test
    fun `given index smaller than last index returns null`() = runBlocking {
        // GIVEN
        dataSource.createTransaction(mutableMapOf("foo" to "123"))

        // WHEN
        val actual = dataSource.getTransactionAtIndex(0)

        Truth.assertThat(actual).isNotNull()
    }

    @Test
    fun `given index bigger than last index returns null`() {
        // GIVEN
        val expected: Transaction? = null

        // WHEN
        val actual = dataSource.getTransactionAtIndex(5)

        // THEN
        Truth.assertThat(actual).isEqualTo(expected)
    }


    @BeforeEach
    fun setUp() {
        dataSource = TransientDataSourceImpl(list)
    }

    @AfterEach
    fun tearDown() {
        list.clear()
    }

}