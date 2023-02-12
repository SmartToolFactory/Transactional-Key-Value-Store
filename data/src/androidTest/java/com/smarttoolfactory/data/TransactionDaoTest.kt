package com.smarttoolfactory.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.smarttoolfactory.data.db.dao.TransactionDao
import com.smarttoolfactory.data.model.TransactionEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TransactionDaoTest : AbstractDaoTest(allowMainThreadQueries = true) {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var transactionDao: TransactionDao


    @Test
    fun givenDBEmptyShouldReturnEmptyList() = runBlocking {

        // GIVEN
        val expected = listOf<TransactionEntity>()

        // WHEN
        val actual = transactionDao.getTransactionList()

        // THEN
        Truth.assertThat(actual.size).isEqualTo(expected.size)
    }

    @Test
    fun givenDBPopulatedShouldReturnTransactionList() = runBlocking {

        // GIVEN
        val transaction1 = TransactionEntity("foo", "123")
        val transaction2 = TransactionEntity("bar", "456")
        val transaction3 = TransactionEntity("baz", "100")

        transactionDao.insertTransaction(transaction1)
        transactionDao.insertTransaction(transaction2)
        transactionDao.insertTransaction(transaction3)

        // WHEN
        val transactionList = transactionDao.getTransactionList()

        // THEN
        Truth.assertThat(transactionList.size).isEqualTo(3)
        Truth.assertThat(transactionList[0]).isEqualTo(transaction1)
        Truth.assertThat(transactionList[1]).isEqualTo(transaction2)
        Truth.assertThat(transactionList[2]).isEqualTo(transaction3)

    }

    @Test
    fun givenDBPopulatedShouldReturnTransactionMap() = runBlocking {
        // GIVEN
        val transaction1 = TransactionEntity("foo", "123")
        val transaction2 = TransactionEntity("bar", "456")
        val transaction3 = TransactionEntity("baz", "100")

        transactionDao.insertTransaction(transaction1)
        transactionDao.insertTransaction(transaction2)
        transactionDao.insertTransaction(transaction3)

        // WHEN
        val transactionMap = transactionDao.getTransactionMap()

        // THEN
        Truth.assertThat(transactionMap.size).isEqualTo(3)
        Truth.assertThat(transactionMap["foo"]).isEqualTo("123")
        Truth.assertThat(transactionMap["bar"]).isEqualTo("456")
        Truth.assertThat(transactionMap["baz"]).isEqualTo("100")
    }

    @Test
    fun givenTransactionExistsShouldUpdate() = runBlocking {
        // GIVEN
        val transaction1 = TransactionEntity("foo", "123")
        transactionDao.insertTransaction(transaction1)

        // WHEN
        transactionDao.insertTransaction(TransactionEntity("foo", "100"))

        // THEN
        val transactionMap = transactionDao.getTransactionMap()
        Truth.assertThat(transactionMap["foo"]).isEqualTo("100")

    }

    @Before
    override fun setUp() {
        super.setUp()
        transactionDao = database.transactionDao()
    }
}