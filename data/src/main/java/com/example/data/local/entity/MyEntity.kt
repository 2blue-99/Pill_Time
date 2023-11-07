package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.local.NoticeData

@Entity(tableName = "NoticeTable")
data class MyEntity(
    @PrimaryKey
    val uid : String,
    @ColumnInfo
    val time : String,
    @ColumnInfo
    val month: String,
    @ColumnInfo
    val day: String,
    @ColumnInfo
    val isChecked: Boolean,
    @ColumnInfo
    val pills: String,
)

fun MyEntity.toDomainEntity(): NoticeData = NoticeData(
    uid = this.uid,
    time = this.time,
    month = this.month,
    day = this.day,
    isChecked = this.isChecked,
    pills = this.pills
)