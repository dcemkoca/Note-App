package com.example.noteappquickwizard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteappquickwizard.model.Note
import com.example.noteappquickwizard.ui.screens.NoteScreen
import com.example.noteappquickwizard.ui.screens.NoteViewModel
import com.example.noteappquickwizard.ui.theme.MyappTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // val noteViewModel = viewModel<NoteViewModel>()
            val noteViewModel: NoteViewModel by viewModels()
            NotesApp(noteViewModel)
        }
    }
}

@Composable
fun NotesApp(noteViewModel: NoteViewModel){

    val notesList = noteViewModel.noteList.collectAsStateWithLifecycle().value

    NoteScreen( notes = notesList,
        onAddNote = {
            noteViewModel.addNote(it)
        },
        onRemoveNote = {
            noteViewModel.deleteNote(it)
        }
    )

}