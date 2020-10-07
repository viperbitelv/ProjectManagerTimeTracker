package lv.viperbitelv.projectmanagertimetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


// This is the loading time of the splash screen
private val SPLASH_TIME_OUT:Long = 1500 // 3000 = 1 sec

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

            Handler().postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }, SPLASH_TIME_OUT)

    }
}