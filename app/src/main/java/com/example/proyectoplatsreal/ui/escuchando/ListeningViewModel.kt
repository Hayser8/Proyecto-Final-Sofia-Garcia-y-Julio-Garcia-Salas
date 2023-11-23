package com.example.proyectoplatsreal.ui.escuchando

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adamratzman.spotify.models.Track
import com.google.firebase.firestore.FirebaseFirestore

class ListeningViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    val tracksList = MutableLiveData<List<FirestoreTrack>>()

    init {
        firestore.collection("songs").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("ListeningViewModel", "Error al escuchar cambios: ", e)
                tracksList.postValue(emptyList())
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val tracks = snapshot.documents.mapNotNull { document ->
                    document.toObject(FirestoreTrack::class.java).also { firestoreTrack ->
                        Log.d("ListeningViewModel", "Track obtenido: $firestoreTrack")
                    }
                }
                tracksList.postValue(tracks)
            } else {
                Log.d("ListeningViewModel", "Snapshot es null")
            }
        }
    }
}

