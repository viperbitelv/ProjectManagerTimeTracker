package lv.viperbitelv.projectmanagertimetracker

import android.app.Application
import androidx.room.Room

class App : Application() {
    val db by lazy {
        Room.databaseBuilder(this, PmttDatabase::class.java, "db-pmtt")
            .allowMainThreadQueries()
            .build()
    }
}