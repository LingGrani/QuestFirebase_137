package com.example.p15firebase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p15firebase.model.Mahasiswa
import com.example.p15firebase.repository.RepositoryMhs
import kotlinx.coroutines.launch

data class MahasiswaEvent (
    val nim: String = "",
    val nama: String = "",
    val gender: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val judulSkripsi: String = "",
    val dosen1: String = "",
    val dosen2: String = ""
)

fun MahasiswaEvent.toMahasiswa(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    gender = gender,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan,
    judulSkripsi = judulSkripsi,
    dosen1 = dosen1,
    dosen2 = dosen2
)

data class FormErrorState (
    val nim: String? = null,
    val nama: String? = null,
    val gender: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
    val judulSkripsi: String? = null,
    val dosen1: String? = null,
    val dosen2: String? = null
) {
    fun isValid(): Boolean {
        return nim == null && nama == null && gender == null && alamat == null && kelas == null && angkatan == null && judulSkripsi == null && dosen1 == null && dosen2 == null
    }
}
data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}

class InsertViewModel (
    private val mhs: RepositoryMhs
): ViewModel() {

    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    // Memperbarui State berdasarkan input
    fun updateUiEvent(event: MahasiswaEvent) {
        uiEvent = uiEvent.copy(insertUiEvent = event)
    }

    // Validasi data input pengguna
    fun validateFields (): Boolean {
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isEmpty()) "NIM tidak boleh kosong" else null,
            nama = if (event.nama.isEmpty()) "Nama tidak boleh kosong" else null,
            gender = if (event.gender.isEmpty()) "Jenis Kelamin tidak boleh kosong" else null,
            alamat = if (event.alamat.isEmpty()) "Alamat tidak boleh kosong" else null,
            kelas = if (event.kelas.isEmpty()) "Kelas tidak boleh kosong" else null,
            angkatan = if (event.angkatan.isEmpty()) "Angkatan tidak boleh kosong" else null,
            judulSkripsi = if (event.judulSkripsi.isEmpty()) "Judul Skripsi tidak boleh kosong" else null,
            dosen1 = if (event.dosen1.isEmpty()) "Dosen Pembimbing 1 tidak boleh kosong" else null,
            dosen2 = if (event.dosen2.isEmpty()) "Dosen Pembimbing 2 tidak boleh kosong" else null
        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMhs () {
        if (validateFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    mhs.insertMhs(uiEvent.insertUiEvent.toMahasiswa())
                    uiState = FormState.Success("Berhasil Menambahkan Mahasiswa")
                } catch (e: Exception) {
                    uiState = FormState.Error("Gagal Menambahkan Mahasiswa")
                }
            }
        } else {
            uiState = FormState.Error("Data Tidak Valid")
        }
    }

    fun resetForm() {
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    fun resetSnackBarMessage() {
        uiState = FormState.Idle
    }
}