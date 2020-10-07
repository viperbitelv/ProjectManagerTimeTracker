package lv.viperbitelv.projectmanagertimetracker

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.about_layout.*

class about_page: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_layout)


        supportActionBar?.setDisplayHomeAsUpEnabled(true) // just display back arrow in Action bar (need fun to work)

        // change action bar title
        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.item_about)

        button_AboutClose.setOnClickListener {
            val goto_home = Intent(this, MainActivity::class.java)
            startActivity(goto_home)
            finish()
        }
    }

    // code for Back arrow to work
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}