package hust.kat.studentadder

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import hust.kat.studentadder.database.StudentDao
import hust.kat.studentadder.database.StudentDatabase
import hust.kat.studentadder.entities.StudentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private var studentDao: StudentDao? = null
    private val viewModel: StudentViewModel by viewModels()
    private var studentList: MutableList<StudentModel> = mutableListOf()
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentAddLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        //KHỞI TẠO ACTIVITY THANH ACTION BAR
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Student Adder"
        supportActionBar?.show()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //THEO DÕI SỰ THAY ĐỔI CỦA DANH SÁCH
        viewModel.studentList.observe(this){newList ->
            studentAdapter.submitList(newList)
        }

        //BẮT ĐẦU CODE CHÍNH
        val studentView: RecyclerView = findViewById(R.id.studentView)

        studentAdapter = StudentAdapter(studentList)
        studentView.adapter = studentAdapter
        studentView.layoutManager = LinearLayoutManager(this)

        registerForContextMenu(studentView)

        studentAddLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data

                val name = data?.getStringExtra("name") ?: "Null"
                val studentID = data?.getStringExtra("studentID") ?: "Null"
                val phoneNumber = data?.getStringExtra("phoneNumber")
                val email = data?.getStringExtra("email")
                val position = data?.getIntExtra("position", -1)

                val newStudent = StudentModel(name, studentID, phoneNumber, email)


                if(position == -1) {
                    studentList.add(newStudent)
                    lifecycleScope.launch {
                        studentDao!!.insertStudent(newStudent)
                        studentAdapter.notifyItemInserted(studentList.size - 1)
                        viewModel.addStudent(newStudent)
                    }
                } else {
                    studentList[position!!] = StudentModel(name, studentID, phoneNumber, email)
                    //viewModel.updateStudent(position, newStudent)

                    lifecycleScope.launch {
                        studentDao!!.updateStudent(newStudent)
                        viewModel.updateStudent(position, newStudent)
                        studentAdapter.notifyItemChanged(position)
                    }
                }
            }
        }

        studentDao = StudentDatabase.getInstance(this@MainActivity)?.studentDao()
        lifecycleScope.launch {
            studentList = studentDao?.getAllStudents() ?: mutableListOf()
            viewModel.loadFromDb(studentDao)
            studentAdapter.notifyDataSetChanged()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addStudent -> {
                studentAddLauncher.launch(Intent(this, AddActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_update -> {
                val position = studentAdapter.selectedPosition
                if(position != -1){
                    val intent = Intent(this, UpdateActivity::class.java).apply {
                        putExtra("name", studentList[position].fullName)
                        putExtra("studentID", studentList[position].studentID)
                        putExtra("phoneNumber", studentList[position].phoneNumber)
                        putExtra("email", studentList[position].email)
                        putExtra("position", position)
                    }

                    studentAddLauncher.launch(intent)
                }
                true
            }
            R.id.menu_delete -> {
                val position = studentAdapter.selectedPosition
                if (position != -1) {
                    lifecycleScope.launch{
                        studentDao!!.deleteStudent(studentList[position])
                        //viewModel.deleteStudent(position)
                    }
                    //dbHelper.deleteStudent(studentList[position].studentID)

                    studentList.removeAt(position)
                    viewModel.deleteStudent(position)
                    studentAdapter.notifyItemRemoved(position)
                }
                true
            }
            R.id.menu_call -> {
                val position = studentAdapter.selectedPosition
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:${studentList[position].phoneNumber}".toUri()
                }
                startActivity(intent)
                true
            }
            R.id.menu_email -> {
                val position = studentAdapter.selectedPosition
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:${studentList[position].email}".toUri()
                }
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
