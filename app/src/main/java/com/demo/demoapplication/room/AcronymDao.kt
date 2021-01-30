package com.demo.demoapplication.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AcronymDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAcronym(acronymEntity: AcronymEntity)

    @Query("SELECT * FROM AcronymEntity where acronymKey =:query ORDER BY acronymKey DESC")
    fun getAllAcronyms(query: String): Flow<List<AcronymEntity>>


}