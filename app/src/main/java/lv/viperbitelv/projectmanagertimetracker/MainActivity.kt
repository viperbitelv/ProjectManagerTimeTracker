package lv.viperbitelv.projectmanagertimetracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.mainItems
//import kotlinx.android.synthetic.main.activity_main.spinnerTest
import kotlinx.android.synthetic.main.employers_layout.*


class MainActivity : AppCompatActivity(), AdapterClickListener {

    private val db get() = Database.getInstance(this)

    private val items = mutableListOf<workentry>()
    private val employer_items = mutableListOf<employersentry>()

    private lateinit var adapter: WorkEntryRecyclerAdapter
    private lateinit var adapteremployer: EmployersRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items.addAll(db.workEntryDao().getAll())
        items.sortByDescending { it.workdate }
         // todo - sort by two values

        adapter = WorkEntryRecyclerAdapter(this, items)
        mainItems.adapter = adapter

        ButtonAddEntry.setOnClickListener {
            // check if either or text input fields are empty
            if (TextUtils.isEmpty (TextEditWorkDone.text.toString()) || TextUtils.isEmpty(TextEditWorkPlace.text.toString()) ||
            TextUtils.isEmpty (TextEditWorkTimeFrom.text.toString()) || TextUtils.isEmpty(TextEditWorkTimeTo.text.toString())
            ) {
                Toast.makeText(this, getString(R.string.toast_fill_all_fields), Toast.LENGTH_LONG).show()
            } else {
                appendItem()
            }
        }
    }

        private fun appendItem() {
        val current_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))
        val item = workentry(
            current_date,
            TextEditWorkPlace.text.toString(),
            TextEditWorkDone.text.toString(),
            TextEditWorkTimeFrom.text.toString(),
            TextEditWorkTimeTo.text.toString()
        )
        item.uid = db.workEntryDao().insertAll(item).first()
        items.add(item)
        adapter.notifyItemInserted(0)
        mainItems.smoothScrollToPosition(0)


        items.sortByDescending { it.workdate }
        TextEditWorkPlace.text.clear()
        TextEditWorkDone.text.clear()
        TextEditWorkTimeFrom.text.clear()
        TextEditWorkTimeTo.text.clear()
        closeKeyBoard()
        adapter.notifyDataSetChanged()

    }

    override fun itemClicked(item: workentry) {
        val intent = Intent(this, WorkEntryEdit::class.java)
            .putExtra(EXTRA_ID, item.uid)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun deleteClicked(item: workentry) {
        db.workEntryDao().delete(item)
        true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK && data != null) {
            val id = data.getLongExtra(EXTRA_ID, 0)
            val item = db.workEntryDao().getItemById(id)
            val position = items.indexOfFirst { it.uid == item.uid }
            items[position] = item
            adapter.notifyItemChanged(position)
        }
    }

    // function which when called, closes the keyboard
    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.itemClose -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.msg_close)
                    .setMessage(R.string.msg_wishToLogOut)
                    .setPositiveButton(R.string.btn_ok) { dialog, id ->
                        finishAffinity()
                    }
                    .setNegativeButton(R.string.btn_cancel) { _, _ -> }
                val dialog = builder.create()
                dialog.show()
                true
            }
             R.id.itemAbout -> {
                 val goto_about = Intent(this, about_page::class.java);
                 startActivity(goto_about)
                true
            }
            R.id.itemEmployers -> {
                val test = Intent(this, EmployersActivity::class.java);
                startActivity(test)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_ID = "lv.viperbitelv.projectmanagertimetracker"
        const val REQUEST_CODE_DETAILS = 404 // any random number can do
    }

}

interface AdapterClickListener {
    fun itemClicked(item: workentry)
    fun deleteClicked(item: workentry)
}