package com.superz.notesdemo.feature.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superz.notesdemo.feature.notes.domain.model.Note
import com.superz.notesdemo.feature.notes.domain.use_case.UseCases
import com.superz.notesdemo.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var note by mutableStateOf(Note(id = 0, title = "", description = null, isBookmarked = false))

    private var deletedNote: Note? = null

    private val _response = MutableStateFlow<Response<List<Note>>>(Response.Loading)
    val response: StateFlow<Response<List<Note>>> = _response.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() = viewModelScope.launch {
        useCases.getAllNotes()
            .onStart {
                _response.value = Response.Loading
            }.catch {
                _response.value = Response.Error(it)
            }.collect {
                _response.value = Response.Success(it)
            }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            useCases.insertNote(note = note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            useCases.updateNote(note = note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deletedNote = note
            useCases.deleteNote(note = note)
        }
    }

    fun undoDeleteNote() {
        viewModelScope.launch {
            deletedNote?.let { note ->
                useCases.insertNote(note = note)
            }
        }
    }

    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            note = useCases.getNoteById(noteId = noteId)
        }
    }

    fun updateNoteTitle(newValue: String) {
        note = note.copy(
            title = newValue
        )
    }

    fun updateNoteDescription(newValue: String) {
        note = note.copy(
            description = newValue
        )
    }

    fun updateIsBookmarked(newValue: Boolean) {
        note = note.copy(
            isBookmarked = newValue
        )
    }

}