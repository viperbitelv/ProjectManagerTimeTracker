package lv.viperbitelv.projectmanagertimetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.work_entry.view.*
import java.util.*

class WorkEntryRecyclerAdapter(
    private val listener: AdapterClickListener,
    private val items: MutableList<workentry>
):

    RecyclerView.Adapter<WorkEntryRecyclerAdapter.ShoppingViewHolder>() {

    class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.work_entry, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.TextWorkDate.text = item.workdate
        holder.itemView.TextWorkPlace.text = item.workplace
        holder.itemView.TextWorkDone.text = item.workdone
        holder.itemView.TextWorkTimeFrom.text = item.workfrom
        holder.itemView.TextWorkTimeTo.text = item.workto

        holder.itemView.setOnClickListener {
            listener.itemClicked(items[position])
        }

        holder.itemView.button_workEntryRemove.setOnClickListener {
            listener.deleteClicked(items[position])
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}