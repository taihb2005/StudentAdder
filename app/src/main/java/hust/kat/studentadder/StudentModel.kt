package hust.kat.studentadder

data class StudentModel(
    val fullName: String,
    val studentID: String,
    val phoneNumber: String?=null,
    val email: String?=null
)