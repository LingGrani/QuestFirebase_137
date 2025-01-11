package com.example.p15firebase.repository

import com.example.p15firebase.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkRepositoryMhs(
    private val firestore: FirebaseFirestore
) : RepositoryMhs {
    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa").add(mahasiswa).await()
        } catch (e: Exception) {
            throw Exception("Error adding Mahasiswa: ${e.message}")
        }
    }

    override fun getAllMahasiswa(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nim", Query.Direction.ASCENDING)
            .addSnapshotListener{
                value, error ->
                if (value != null) {
                    val mhsList = value.documents.mapNotNull {
                        // convert from document firestore to data class
                        it.toObject(Mahasiswa::class.java)
                    }
                    //fungsi untuk mengirim collection ke dataclass
                    trySend(mhsList)
                }
            }
        awaitClose{
            //menutup collection dari firebase
            mhsCollection.remove()
        }
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }
}