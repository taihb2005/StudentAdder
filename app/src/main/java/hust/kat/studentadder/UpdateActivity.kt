package hust.kat.studentadder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hust.kat.studentadder.databinding.ActivityUpdateBinding

class UpdateActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_add_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.editName.setText(intent.getStringExtra("name"))
        binding.editStudentId.setText(intent.getStringExtra("studentID"))
        binding.editPhoneNumber.setText(intent.getStringExtra("phoneNumber"))
        binding.editEmail.setText(intent.getStringExtra("email"))

        findViewById<Button>(R.id.updateStudentButton).setOnClickListener{
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
                putExtra("position", intent.getIntExtra("position", -1))
            }

            setResult(RESULT_OK, resultIntent)

            finish()

        }
    }
}