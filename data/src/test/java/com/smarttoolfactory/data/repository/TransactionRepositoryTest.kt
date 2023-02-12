package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.datasource.LocalDataSource
import com.smarttoolfactory.data.datasource.TransientDataSource
import com.smarttoolfactory.data.model.Transaction
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Used single instance of tests instead of creating new instance for each test.
 *
 * @see <a href="https://phauer.com/2018/best-practices-unit-testing-kotlin/">Kotlin Test Performance</a>
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TransactionRepositoryTest {

    private lateinit var repository: TransactionRepository

    private val transientDataSource: TransientDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()

    @Test
    fun `begin should call transient data source`() = runBlocking {
        // GIVEN
        val transaction = Transaction(map = mutableMapOf("foo" to "123"))

        coEvery {
            transientDataSource.currentTransaction
        } returns transaction

        coEvery {
            transientDataSource.createTransaction(transaction.map)
        } just runs

        // WHEN
        repository.begin()

        // THEN
        coVerify(exactly = 1) {
            transientDataSource.createTransaction(transaction.map)
        }
    }

    @BeforeEach
    fun setUp() {
        repository = TransactionRepositoryImpl(
            transientDataSource = transientDataSource,
            localDataSource = localDataSource
        )
    }

    @AfterEach
    fun tearDown() {
        clearMocks(transientDataSource, localDataSource)
    }
}