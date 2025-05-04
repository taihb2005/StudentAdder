package hust.kat.studentadder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UpdateActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_add_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editName = findViewById<EditText>(R.id.editName)
        val editStudentId = findViewById<EditText>(R.id.editStudentId)
        val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
        val editEmail = findViewById<EditText>(R.id.editEmail)

        editName.setText(intent.getStringExtra("name"))
        editStudentId.setText(intent.getStringExtra("studentID"))
        editPhoneNumber.setText(intent.getStringExtra("phoneNumber"))
        editEmail.setText(intent.getStringExtra("email"))

        findViewById<Button>(R.id.updateStudentButton).setOnClickListener{
            if(editName.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(editStudentId.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your student ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(editPhoneNumber.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(editEmail.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent().apply{
                putExtra("name", editName.text.toString())
                putExtra("studentID", editStudentId.text.toString())
                putExtra("phoneNumber", editPhoneNumber.text.toString())
                putExtra("email", editEmail.text.toString())
                putExtra("position", intent.getIntExtra("position", -1))
            }

            setResult(RESULT_OK, resultIntent)

            finish()

        }
    }
}