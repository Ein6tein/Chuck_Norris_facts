package lv.chernishenko.chucknorrisfacts.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class ChuckNorrisFact(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "icon_url") val iconUrl: String?,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)
