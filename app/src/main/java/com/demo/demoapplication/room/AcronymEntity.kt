package com.demo.demoapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.demoapplication.model.AcronymItem

@Entity
data class AcronymEntity(
    @PrimaryKey
    var acronymKey: String = "",
    var acronymLongFormat: List<AcronymItem> = emptyList()
)