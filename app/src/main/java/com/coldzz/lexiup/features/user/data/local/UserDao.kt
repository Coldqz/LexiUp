package com.coldzz.lexiup.features.user.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.features.user.data.local.entities.UserStats
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface UserDao {
    /**
     * If this query returns null it means that there are no data in database yet
     * */
    @Query("SELECT blocks_learned_today FROM user_stats WHERE id = :userId LIMIT 1")
    suspend fun getBlocksLearnedToday(userId: Int = Constants.USER_ID): Int?

    /**
     * If this query returns null it means that no data are in database yet
     * */
    @Query("SELECT last_update_date FROM user_stats WHERE id = :userId LIMIT 1")
    suspend fun getLastUpdateDate(userId: Int = Constants.USER_ID): LocalDate?

    @Query("SELECT * FROM user_stats WHERE id = :userId LIMIT 1")
    fun getUserFlow(userId: Int = Constants.USER_ID): Flow<UserStats?>

    @Query("UPDATE user_stats " +
            "SET blocks_learned_today = :blocksLearned, " +
            "last_update_date = :lastUpdateDate " +
            "WHERE id = :userId")
    suspend fun updateBlocksLearnedToday(blocksLearned: Int, lastUpdateDate: LocalDate, userId: Int = Constants.USER_ID)

    @Query("UPDATE user_stats " +
            "SET new_blocks_learned_today = :newBlocksLearned " +
            "WHERE id = :userId")
    suspend fun updateNewBlocksLearned(newBlocksLearned: Int, userId: Int = Constants.USER_ID)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createUser(user: UserStats)
}