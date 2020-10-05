package com.wahyu.hiringapps2.dashboard.profile.room

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var coroutineScope: CoroutineScope

    companion object {
        const val ADD_WORD_REQUEST_CODE = 9013
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

        val wordDao = NoteRoomDatabase.getWordDatabase(this).wordDao()

        binding.buttonAddNote.setOnClickListener {
            if (binding.etInputNoteTitle.text.isNotEmpty()) {
                coroutineScope.launch {
                    wordDao.insert(NoteRoomEntity(binding.etInputNoteTitle.text.toString(), System.currentTimeMillis()))
                }
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}