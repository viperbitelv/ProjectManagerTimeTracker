package lv.viperbitelv.projectmanagertimetracker

import androidx.room.*
import java.util.*

@Entity(tableName = "Employers")
data class employersentry(
    val name: String,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface EmployerEntryDao {
    @Query("SELECT * FROM Employers")
    fun getAll(): List<employersentry>

    @Query("SELECT * FROM Employers WHERE uid = :itemId")
    fun getItemById(itemId: Long): employersentry

    @Insert
    fun insertAll(vararg items: employersentry): List<Long>

    @Update
    fun update(item: employersentry)

    @Delete
    fun delete(item: employersentry)
}