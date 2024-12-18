package com.example.pertemuan9.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan9.data.entity.Mahasiswa
import com.example.pertemuan9.repository.RepositoryMhs
import com.example.pertemuan9.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    var updateUIState by mutableStateOf(MhsUIState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        updateUIState = updateUIState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM Tidak boleh kosong",
                    nama = if (event.nama.isNotEmpty()) null else "Nama Tidak boleh kosong",
                    jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin Tidak boleh kosong",
                    alamat = if (event.alamat.isNotEmpty()) null else "alamat Tidak boleh kosong",
                    kelas = if (event.kelas.isNotEmpty()) null else "Kelas Tidak boleh kosong",
                    angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan Tidak boleh kosong",
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.mahasiswaEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntitiy())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil di update",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal di update"
                    )
                }
            }
        } else{
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal di update"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Mahasiswa.toUIStateMhs(): MhsUIState = MhsUIState(
    mahasiswaEvent = this.toDetailUiEvent(),
)