package hust.kat.studentadder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hust.kat.studentadder.database.StudentDbHelper

class StudentViewModel: ViewModel() {
    private val _studentList = MutableLiveData<List<StudentModel>>()
    val studentList: LiveData<List<StudentModel>> = _studentList

    init {
        _studentList.value = emptyList()
    }

    fun loadFromDb(dbHelper: StudentDbHelper) {
        _studentList.value = dbHelper.loadAllStudent()
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