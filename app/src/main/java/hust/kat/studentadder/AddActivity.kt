package hust.kat.studentadder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hust.kat.studentadder.databinding.ActivityAddBinding

class AddActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_add_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViewById<Button>(R.id.addStudentButton).setOnClickListener{
            if(binding.editName.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.editStudentId.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your student ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.editPhoneNumber.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.editEmail.text.toString().isBlank()){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent().apply{
                putExtra("name", binding.editName.text.toString())
                putExtra("studentID", binding.editStudentId.text.toString())
                putExtra("phoneNumber", binding.editPhoneNumber.text.toString())
                putExtra("email", binding.editEmail.text.toString())
                putExtra("position", -1)
            }

            setResult(RESULT_OK, resultIntent)

            finish()

        }

    }
}