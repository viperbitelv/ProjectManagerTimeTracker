package lv.viperbitelv.projectmanagertimetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.employers_entry.view.*
import java.util.*

class EmployersRecyclerAdapter(
    private val listener: EmployerAdapterClickListener,
    private val items: MutableList<employersentry>
):

    RecyclerView.Adapter<EmployersRecyclerAdapter.EmployerViewHolder>() {

    class EmployerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.employers_entry, parent, false)
        return EmployerViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: EmployerViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.TextEmployer.text = item.name

        holder.itemView.setOnClickListener {
            //listener.itemClicked(items[position])
        }

        holder.itemView.button_employerRemove.setOnClickListener {
            listener.deleteClicked(items[position])
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}