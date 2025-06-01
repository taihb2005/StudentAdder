package hust.kat.studentadder.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import hust.kat.studentadder.StudentModel

class StudentDbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
                CREATE TABLE student(
                    name TEXT NOT NULL,
                    studentID TEXT PRIMARY KEY NOT NULL,
                    phone TEXT NOT NULL,
                    email TEXT NOT NULL
                );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS student")
        onCreate(db)
    }

    fun loadAllStudent(): MutableList<StudentModel>{
        val db = readableDatabase

        val query = "SELECT * FROM student"
        val studentList: MutableList<StudentModel> = mutableListOf()
        val cursor = db.rawQuery(query, null)

        with(cursor){
            while(moveToNext()){
                val name = getString(0)
                val studentID = getString(1)
                val phone = getString(2)
                val email = getString(3)

                studentList.add(StudentModel(name, studentID, phone, email))
            }
        }
        Log.d("StudentDbHelper", "studentList: $studentList")
        cursor.close()

        return studentList
    }

    fun addStudent(name: String, studentID: String, phone: String, email: String) {
        val db = writableDatabase
        val sqlInsertQuery =
            """
                INSERT INTO student(name, studentID, phone, email)
                VALUES (?, ?, ?, ?)
            """.trimIndent()

        db.execSQL(sqlInsertQuery, arrayOf(name, studentID, phone, email))
    }

    fun updateStudent(name: String, studentID: String, phone: String, email: String) {
        val db = writableDatabase
        val sqlUpdateQuery =
            """
                UPDATE student
                SET name = ?, phone = ?, email = ?
                WHERE studentID = ?
            """.trimIndent()

        db.execSQL(sqlUpdateQuery, arrayOf(name, phone, email, studentID))
    }


    fun deleteStudent(studentID: String) {
        val db = writableDatabase
        val sqlDeleteQuery = "DELETE FROM student WHERE studentID = ?"
        db.execSQL(sqlDeleteQuery, arrayOf(studentID))
    }


    companion object{
        const val DATABASE_NAME = "student.db"
        const val DATABASE_VERSION = 2
    }
}