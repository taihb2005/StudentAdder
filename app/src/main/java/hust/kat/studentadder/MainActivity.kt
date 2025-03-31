package hust.kat.studentadder

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val studentList: MutableList<StudentModel> by lazy {
        mutableListOf()
    }

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
        val adapter = StudentAdapter(studentList)
        val studentView: ListView = findViewById(R.id.studentView)

        val addButton: Button = findViewById(R.id.addStudent)
        addButton.setOnClickListener{
            val fullName = editName.text.toString()
            val studentId = editStudentId.text.toString()

            if(fullName.isEmpty() || studentId.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập đầy đủ Họ tên và MSSV", Toast.LENGTH_SHORT).show()
            } else {
                studentList.add(StudentModel(fullName, studentId, Button(this)))
                adapter.notifyDataSetChanged()
            }
        }

        studentView.adapter = adapter
    }
}