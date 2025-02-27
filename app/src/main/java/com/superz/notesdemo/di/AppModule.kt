package com.superz.notesdemo.di

import android.content.Context
import androidx.room.Room
import com.superz.notesdemo.feature.notes.data.LocalDatabase
import com.superz.notesdemo.feature.notes.data.NoteRepositoryImpl
import com.superz.notesdemo.feature.notes.domain.NoteRepository
import com.superz.notesdemo.feature.notes.domain.use_case.DeleteNote
import com.superz.notesdemo.feature.notes.domain.use_case.GetAllNotes
import com.superz.notesdemo.feature.notes.domain.use_case.GetNoteById
import com.superz.notesdemo.feature.notes.domain.use_case.InsertNote
import com.superz.notesdemo.feature.notes.domain.use_case.UpdateNote
import com.superz.notesdemo.feature.notes.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLocalDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context, LocalDatabase::class.java, "local_database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNoteRepository(db: LocalDatabase): NoteRepository {
        return NoteRepositoryImpl(dao = db.noteDao())
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: NoteRepository): UseCases {
        return UseCases(
            insertNote = InsertNote(repository = repository),
            updateNote = UpdateNote(repository = repository),
            deleteNote = DeleteNote(repository = repository),
            getNoteById = GetNoteById(repository = repository),
            getAllNotes = GetAllNotes(repository = repository)
        )
    }

}