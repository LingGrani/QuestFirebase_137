package com.example.p15firebase.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p15firebase.model.Mahasiswa
import com.example.p15firebase.ui.viewmodel.DetailMhsViewModel
import com.example.p15firebase.ui.viewmodel.DetailUiState
import com.example.p15firebase.ui.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMhsView(
    modifier: Modifier = Modifier,
    viewModel: DetailMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Detail Mahasiswa")},
                modifier = modifier
            )
        }
    ) { innerPadding ->
        DetailStatus (
            detailUiState = viewModel.mhsDetailUiState,
            retryAction = { viewModel.getMhsbyId() },
            modifier = modifier.padding(innerPadding),
            onBack = onBack
        )
    }
}

@Composable
fun DetailStatus (
    detailUiState: DetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    ) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailUiState.Success ->
            if (detailUiState.data.nim.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                ItemDetailMhs (
                    mahasiswa = detailUiState.data,
                    modifier = modifier.fillMaxSize(),
                    onBack = onBack
                )
            }
        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxWidth(), message = detailUiState.e.localizedMessage?: "error")
        else -> {}
    }
}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa,
    onBack: () -> Unit = {},
    ) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.gender)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Judul Skripsi", isinya = mahasiswa.judulSkripsi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Dosen Pembimbing 1", isinya = mahasiswa.dosen1)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Dosen Pembimbing 2", isinya = mahasiswa.dosen2)
        }
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali")
        }
    }
}

@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}