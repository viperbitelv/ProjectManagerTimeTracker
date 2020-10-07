package lv.viperbitelv.projectmanagertimetracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import lv.viperbitelv.projectmanagertimetracker.MainActivity.Companion.EXTRA_ID

class WorkEntryEdit : AppCompatActivity() {

    //        private val db get() = (application as App).db
    private val db get() = Database.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // display back arrow in Action bar

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.edit_work_entry)

        val id = intent.getLongExtra(EXTRA_ID, 0)
        val item = db.workEntryDao().getItemById(id)

            when (id.toString()) {
                 "0" -> {
                    textEditDate.setText("2020-02-02")
                    textEditWorkPlace.setText("place here")
                    textEditWorkDone.setText("working..")
                    textEditWorkFrom.setText("a")
                    textEditWorkTo.setText("z")
                }
                    else -> {
                        textEditDate.setText(item.workdate)
                        textEditWorkPlace.setText(item.workplace)
                        textEditWorkDone.setText(item.workdone)
                        textEditWorkFrom.setText(item.workfrom)
                        textEditWorkTo.setText(item.workto)
                    }
            }

        detailsSave.setOnClickListener {
            db.workEntryDao().update(
                item.copy(
                    workdate = textEditDate.text.toString(),
                    workplace = textEditWorkPlace.text.toString(),
                    workdone = textEditWorkDone.text.toString(),
                    workfrom = textEditWorkFrom.text.toString(),
                    workto = textEditWorkTo.text.toString()
                )
            )
            val intent = Intent().putExtra(EXTRA_ID, item.uid)
            setResult(RESULT_OK, intent)
            finish()
        }
        var share_content = getString(R.string.labelWorkDate) + ": " + item.workdate + System.lineSeparator()
        share_content = share_content + getString(R.string.labelWorkTimeFrom) + ": " + item.workfrom + System.lineSeparator()
        share_content = share_content + getString(R.string.labelWorkTimeTo) + ": " + item.workto + System.lineSeparator()
        share_content = share_content + getString(R.string.labelWorkPlace) + ": " + item.workplace + System.lineSeparator()
        share_content = share_content + getString(R.string.labelWorkDone) + ": " + item.workdone + System.lineSeparator()

        // code for sending note via e-mail
        val sendIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            // generate email content
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            putExtra(Intent.EXTRA_TEXT, share_content)
        }

        button_ToEmail.setOnClickListener {
            startActivity(sendIntent)
        }

        // code for sharing  note via other apps
        val noteIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, share_content)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(noteIntent, null)

        buttonShareOther.setOnClickListener {
            startActivity(shareIntent)
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