package hust.kat.studentadder

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private val studentList = mutableListOf<StudentModel>()
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentAddLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
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


        val studentView: RecyclerView = findViewById(R.id.studentView)

        studentAdapter = StudentAdapter(studentList)
        studentView.adapter = studentAdapter
        studentView.layoutManager = LinearLayoutManager(this)

        registerForContextMenu(studentView)

        studentAddLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data

                val name = data?.getStringExtra("name") ?: "Null"
                val id = data?.getStringExtra("studentID") ?: "Null"
                val phoneNumber = data?.getStringExtra("phoneNumber")
                val email = data?.getStringExtra("email")
                val pos = data?.getIntExtra("position", -1)

                if(pos == -1) {
                    studentList.add(StudentModel(name, id, phoneNumber, email))
                    studentAdapter.notifyItemInserted(studentList.size - 1)
                } else {
                    studentList[pos!!] = StudentModel(name, id, phoneNumber, email)
                    studentAdapter.notifyItemChanged(pos)
                }
            }
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
                        putExtra("name", studentList.get(position).fullName)
                        putExtra("studentID", studentList.get(position).studentID)
                        putExtra("phoneNumber", studentList.get(position).phoneNumber)
                        putExtra("email", studentList.get(position).email)
                        putExtra("position", position)
                    }

                    studentAddLauncher.launch(intent)
                }
                true
            }
            R.id.menu_delete -> {
                val position = studentAdapter.selectedPosition
                if (position != -1) {
                    studentList.removeAt(position)
                    studentAdapter.notifyItemRemoved(position)
                }
                true
            }
            R.id.menu_call -> {
                val position = studentAdapter.selectedPosition
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:${studentList.get(position).phoneNumber}".toUri()
                }
                startActivity(intent)
                true
            }
            R.id.menu_email -> {
                val position = studentAdapter.selectedPosition
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:${studentList.get(position).email}".toUri()
                }
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
