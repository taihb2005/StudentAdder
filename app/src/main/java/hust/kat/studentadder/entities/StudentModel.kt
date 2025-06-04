package hust.kat.studentadder.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class StudentModel(
    @ColumnInfo(name = "name") val fullName: String,
    @PrimaryKey val studentID: String,
    @ColumnInfo(name = "phone") val phoneNumber: String?=null,
    @ColumnInfo(name = "email") val email: String?=null
)