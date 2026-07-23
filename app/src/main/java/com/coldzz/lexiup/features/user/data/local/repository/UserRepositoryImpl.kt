package com.coldzz.lexiup.features.user.data.local.repository

import android.util.Log
import androidx.room.Transaction
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.user.data.local.UserDao
import com.coldzz.lexiup.features.user.data.local.entities.UserStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

private const val TAG = "UserStatsRepositoryImpl"

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {
    override fun getUserFlow(): Flow<UserStats> {
        return dao.getUserFlow().onEach {
            if (it == null) {
                try {
                    dao.createUser(
                        UserStats(
                            id = Constants.USER_ID,
                            streak = 0,
                            blocksLearnedToday = null,
                            newBlocksLearnedToday = null,
                            lastUpdateDate = null
                        )
                    )
                    Log.d(TAG, "User was created")
                } catch (e: Exception) {
                    Log.d(TAG, "Error creating user: ${e.message}")
                }
            }
        }
            .filterNotNull()
            .distinctUntilChanged()
    }

    override suspend fun getBlocksLearnedToday(): Int? {
        return dao.getBlocksLearnedToday()
    }

    override suspend fun getLastUpdateDate(): LocalDate? {
        return dao.getLastUpdateDate()
    }

    /**
     * We use this function to change user daily limit stats. Also [newBlock] is to mark whether block was first time learned.
     * */
    @Transaction
    override suspend fun increaseBlocksLearnedToday(newBlock: Boolean) {
        val today = LocalDate.now()

        val userData = getUserFlow().firstOrNull()

        // if those are nulls then treat as 0/today date, because it is first time user learned block
        val blocksLearned = userData?.blocksLearnedToday ?: 0
        val lastUpdateDate = userData?.lastUpdateDate ?: today
        val newBlocksLearned = userData?.newBlocksLearnedToday ?: 0

        if (lastUpdateDate.isBefore(today)) {
            // New day. When user learn block on new day we RESET the counters to 1
            dao.updateBlocksLearnedToday(
                blocksLearned = 1,
                lastUpdateDate = today
            )
            /*if this block is new then we update it counter to 1 since user learned it.
            But if it is not new block then we reset counter to 0*/
            dao.updateNewBlocksLearned(
                newBlocksLearned = if (newBlock) 1 else 0,
            )
        } else {
            // same day, with limit check
            if (blocksLearned >= Constants.MAX_LEARNED_BLOCKS_BY_DAY) return
            dao.updateBlocksLearnedToday(
                blocksLearned + 1,
                lastUpdateDate = today
            )
            if (newBlock) {
                dao.updateNewBlocksLearned(newBlocksLearned + 1)
            }
        }
    }
}