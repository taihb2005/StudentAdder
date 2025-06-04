package hust.kat.studentadder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hust.kat.studentadder.entities.StudentModel

@Dao
interface StudentDao {
    @Query("SELECT * FROM student")
    suspend fun getAllStudents(): MutableList<StudentModel>

    @Insert
    suspend fun insertStudent(student: StudentModel)

    @Update
    suspend fun updateStudent(vararg student: StudentModel)

    @Delete
    suspend fun deleteStudent(vararg student: StudentModel)

}