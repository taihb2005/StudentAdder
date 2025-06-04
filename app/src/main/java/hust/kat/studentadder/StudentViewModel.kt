package hust.kat.studentadder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hust.kat.studentadder.database.StudentDao
import hust.kat.studentadder.entities.StudentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentViewModel: ViewModel() {
    private val _studentList = MutableLiveData<List<StudentModel>>()
    val studentList: LiveData<List<StudentModel>> = _studentList

    init {
        _studentList.value = emptyList()
    }

    suspend fun loadFromDb(dao: StudentDao?) {
        withContext(Dispatchers.Main) {
            _studentList.value = dao?.getAllStudents()
        }
    }

    fun load(studentList: List<StudentModel>){
        _studentList.value = studentList
    }

    fun refresh(){
        _studentList.value = _studentList.value

    }

    fun addStudent(student: StudentModel) {
        val updated = _studentList.value.orEmpty().toMutableList()
        updated.add(student)
        _studentList.value = updated
    }

    fun updateStudent(pos: Int, student: StudentModel) {
        val updated = _studentList.value.orEmpty().toMutableList()
        updated[pos] = student
        _studentList.value = updated
    }

    fun deleteStudent(pos: Int) {
        val updated = _studentList.value.orEmpty().toMutableList()
        updated.removeAt(pos)
        _studentList.value = updated
    }
}