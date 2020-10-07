package lv.viperbitelv.projectmanagertimetracker

import androidx.room.*
import java.util.*

@Entity(tableName = "WorkEntries")
data class workentry(
    val workdate: String,
    val workplace: String,
    val workdone: String,
    val workfrom: String,
    val workto: String,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface WorkEntryDao {
    @Query("SELECT * FROM WorkEntries")
    fun getAll(): List<workentry>

    @Query("SELECT * FROM WorkEntries WHERE uid = :itemId")
    fun getItemById(itemId: Long): workentry

    @Insert
    fun insertAll(vararg items: workentry): List<Long>

    @Update
    fun update(item: workentry)

    @Delete
    fun delete(item: workentry)
}