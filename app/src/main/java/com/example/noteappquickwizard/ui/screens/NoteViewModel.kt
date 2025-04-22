package com.example.noteappquickwizard.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappquickwizard.data.NotesDataSource
import com.example.noteappquickwizard.model.Note
import com.example.noteappquickwizard.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {
    //var noteList = mutableStateListOf<Note>()

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        getAllNotes()
    }

     fun addNote(note: Note) = viewModelScope.launch {
        repository.addNote(note)
    }

     fun updateNote(note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

     fun deleteAllNotes(){
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

     fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collect() { listOfNotes ->
                if(listOfNotes.isNullOrEmpty()){
                    Log.d("Empty", "Empty List")
                }
                else{
                    _noteList.value = listOfNotes
                }
            }
        }
    }


}