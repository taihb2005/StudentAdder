package hust.kat.studentadder

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hust.kat.studentadder.entities.StudentModel

class StudentAdapter(
    private var studentList: List<StudentModel>
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    var selectedPosition = -1

    fun submitList(newList: List<StudentModel>) {
        this.studentList = newList
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val tvName: TextView = itemView.findViewById(R.id.displayStudentName)
        val tvId: TextView = itemView.findViewById(R.id.displayStudentId)
        init {
            itemView.setOnCreateContextMenuListener(this)
            itemView.setOnLongClickListener {
                selectedPosition = adapterPosition
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val inflater = MenuInflater(v?.context)
            inflater.inflate(R.menu.context_menu, menu)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        val studentName = "Full name: ${student.fullName}"
        holder.tvName.text = studentName

        val studentID = "Student ID: ${student.studentID}"
        holder.tvId.text = studentID
    }

    override fun getItemCount(): Int = studentList.size
}
