package hust.kat.studentadder.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import hust.kat.studentadder.entities.StudentModel

@Database(entities = [StudentModel::class], version = 1)
abstract class StudentDatabase: RoomDatabase(){
    abstract fun studentDao(): StudentDao
    companion object{
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase? {
            return INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    context=context.applicationContext,
                    klass=StudentDatabase::class.java,
                    name="student_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {INSTANCE = it}
                INSTANCE
            }
        }
    }
}