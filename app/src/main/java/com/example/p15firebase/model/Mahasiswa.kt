package com.example.p15firebase.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val gender: String,
    val kelas: String,
    val angkatan: String,
    val judulSkripsi: String,
    val dosen1: String,
    val dosen2: String
) {
    constructor() : this("", "", "", "", "", "", "", "", "")
}