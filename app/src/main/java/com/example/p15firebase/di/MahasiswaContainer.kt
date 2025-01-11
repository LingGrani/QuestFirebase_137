package com.example.p15firebase.di

import com.example.p15firebase.MahasiswaApp
import com.example.p15firebase.repository.NetworkRepositoryMhs
import com.example.p15firebase.repository.RepositoryMhs
import com.google.firebase.firestore.FirebaseFirestore

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: MahasiswaApp): InterfaceContainerApp {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override val repositoryMhs: RepositoryMhs by lazy {
        NetworkRepositoryMhs(firestore)
    }
}

