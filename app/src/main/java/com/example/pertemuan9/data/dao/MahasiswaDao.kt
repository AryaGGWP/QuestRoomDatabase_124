package com.example.pertemuan9.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pertemuan9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    @Query("SELECT * FROM mahasiswa ORDER BY nama ASC")
    fun getAllMahasiswa(): Flow <List<Mahasiswa>>
    //mengambil data mahasiswa dari database, urut berdasarkan ascending(ASC)

    @Query("SELECT * FROM mahasiswa WHERE nim = :nim")
    fun getMahasiswa(nim: String): Flow<Mahasiswa>
    //mengambil data mahasiswa berdasarkan NIM

    @Delete
    suspend fun deleteMahasiswa(mahasiswa: Mahasiswa)
    //menghapus data mahasiswa tertentu

    @Update
    suspend fun updateMahasiswa(mahasiswa: Mahasiswa)
    //memperbarui informasi mahasiswa yang sudahaada di database
}

