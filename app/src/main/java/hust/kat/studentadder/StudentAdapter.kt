package hust.kat.studentadder

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView


class StudentAdapter(
    private val studentList: MutableList<StudentModel>
): BaseAdapter() {
    override fun getCount() = studentList.size

    override fun getItem(p0: Int) = studentList[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(
                R.layout.student_item,
                parent,
                false
            )
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val student = studentList[position]
        holder.tvName.text = student.fullName
        holder.tvId.text = "MSSV: ${student.studentID}"
        holder.btnDelete.setOnClickListener{
            studentList.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }

    private class ViewHolder(view: View) {
        val tvName: TextView = view.findViewById(R.id.text1)
        val tvId: TextView = view.findViewById(R.id.text2)
        val btnDelete: Button = view.findViewById(R.id.delete)
    }

}