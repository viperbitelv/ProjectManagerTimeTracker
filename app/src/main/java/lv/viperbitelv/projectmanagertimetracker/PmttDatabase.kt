package lv.viperbitelv.projectmanagertimetracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [workentry::class, employersentry::class])
abstract class PmttDatabase : RoomDatabase() {

    abstract fun workEntryDao(): WorkEntryDao
    abstract fun EmployerEntryDao(): EmployerEntryDao

}

object Database {

    private var instance: PmttDatabase? = null

    fun getInstance(context: Context) = instance ?: Room.databaseBuilder(
        context.applicationContext, PmttDatabase::class.java, "pmtt"
    )
        .allowMainThreadQueries()
        .build()
        .also { instance = it }
}