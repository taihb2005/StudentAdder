package hust.kat.studentadder

import android.widget.Button

data class StudentModel(
    val fullName: String,
    val studentID: String,
    private val phoneNumber: Number,
    private val email: String
)