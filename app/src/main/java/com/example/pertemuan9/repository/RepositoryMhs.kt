package com.example.pertemuan9.repository

import com.example.pertemuan9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)

    fun getAllMhs(): Flow<List<Mahasiswa>>
    //memanggil fungsi getMahasiswa dari MahasiswaDao

    fun getMhs(nim: String): Flow<Mahasiswa>
    //memanggil fungsi untuk memanggil data sesuai NIM

    suspend fun deleteMhs(mahasiswa: Mahasiswa)

    suspend fun updateMhs(mahasiswa: Mahasiswa)
}