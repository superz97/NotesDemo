package com.superz.notesdemo.feature.notes.domain.use_case

import com.superz.notesdemo.feature.notes.domain.NoteRepository
import com.superz.notesdemo.feature.notes.domain.model.Note
import javax.inject.Inject

class InsertNote @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) = repository.insertNote(note)

}