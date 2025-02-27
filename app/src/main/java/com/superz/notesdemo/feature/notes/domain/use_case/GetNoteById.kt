package com.superz.notesdemo.feature.notes.domain.use_case

import com.superz.notesdemo.feature.notes.domain.NoteRepository
import com.superz.notesdemo.feature.notes.domain.model.Note
import javax.inject.Inject

class GetNoteById @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int): Note {
        return repository.getNoteById(id = noteId)
    }


}