package hust.kat.studentadder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: MutableList<StudentModel>
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.text1)
        val tvId: TextView = itemView.findViewById(R.id.text2)
        val btnDelete: ImageButton = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.tvName.text = student.fullName
        holder.tvId.text = "MSSV: ${student.studentID}"

        holder.btnDelete.setOnClickListener {
            studentList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, studentList.size)
        }
    }

    override fun getItemCount(): Int = studentList.size
}
