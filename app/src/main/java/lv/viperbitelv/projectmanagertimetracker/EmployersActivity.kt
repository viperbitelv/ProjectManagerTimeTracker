package lv.viperbitelv.projectmanagertimetracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import kotlinx.android.synthetic.main.employers_layout.*


class EmployersActivity : AppCompatActivity(), EmployerAdapterClickListener {

    private val db get() = Database.getInstance(this)
    private val items = mutableListOf<employersentry>()
    private lateinit var adapter: EmployersRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employers_layout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // just display back arrow in Action bar (need fun to work)

        // change action bar title
        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.labelAddNewEmployer)

        items.addAll(db.EmployerEntryDao().getAll())

        items.sortBy { it.name }

        adapter = EmployersRecyclerAdapter(this, items)
        mainItems.adapter = adapter


        ButtonAddEmployer.setOnClickListener {
            // check if either or text input fields are empty
            if (TextUtils.isEmpty (TextEditEmployer.text.toString()))
            {
                Toast.makeText(this, getString(R.string.toast_fill_all_fields), Toast.LENGTH_LONG).show()
            } else {
                appendItem()
            }
        }
    }

    private fun appendItem() {
        val item = employersentry(
            TextEditEmployer.text.toString()
        )
        item.uid = db.EmployerEntryDao().insertAll(item).first()

        closeKeyBoard()
        items.add(item)
        items.sortBy { it.name }
        TextEditEmployer.text.clear()
        adapter.notifyDataSetChanged()

        // delay the show of toast -- for different effect
        val TOAST_DELAY:Long = 500
        Handler().postDelayed({
            val toastMessage = getString (R.string.msg_newEmployerEntryAdded) + ": " + item.name
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
        }, TOAST_DELAY)
    }

    override fun itemClicked(item: employersentry) {
        TODO("Employer entry is not editable..just because..")
    }

    override fun deleteClicked(item: employersentry) {
        db.EmployerEntryDao().delete(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK && data != null) {
            val id = data.getLongExtra(EXTRA_ID, 0)
            val item = db.EmployerEntryDao().getItemById(id)
            val position = items.indexOfFirst { it.uid == item.uid }
            items[position] = item
            adapter.notifyItemChanged(position)
        }
    }


    companion object {
        const val EXTRA_ID = "lv.viperbitelv.projectmanagertimetracker"
        const val REQUEST_CODE_DETAILS = 404 // any random number can do
    }

    // function which when called, closes the keyboard
    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

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

interface EmployerAdapterClickListener {
    fun itemClicked(item: employersentry)
    fun deleteClicked(item: employersentry)
}