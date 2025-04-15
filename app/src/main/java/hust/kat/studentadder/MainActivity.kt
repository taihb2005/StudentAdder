package hust.kat.studentadder

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val studentList: MutableList<StudentModel> by lazy {
        mutableListOf()
    }

    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editName: EditText = findViewById(R.id.editName)
        val editStudentId: EditText = findViewById(R.id.editStudentId)
        val studentView: RecyclerView = findViewById(R.id.studentView)
        val addButton: Button = findViewById(R.id.addStudent)

        adapter = StudentAdapter(studentList)
        studentView.adapter = adapter
        studentView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val fullName = editName.text.toString()
            val studentId = editStudentId.text.toString()

            if (fullName.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Họ tên và MSSV", Toast.LENGTH_SHORT).show()
            } else {
                studentList.add(StudentModel(fullName, studentId))
                adapter.notifyItemInserted(studentList.size - 1)
                editName.text.clear()
                editStudentId.text.clear()
            }
        }
    }
}
