package com.wahyu.hiringapps2.dashboard.profile.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wahyu.hiringapps2.R
import com.wahyu.hiringapps2.databinding.ActivityNoteListBinding
import kotlinx.coroutines.*

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_list)
        coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

        binding.rvWords.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvWords.adapter = NoteListAdapter()

        binding.btnAddWord.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, AddNoteActivity.ADD_WORD_REQUEST_CODE)
        }

        binding.btnDeleteAllNote.setOnClickListener {
            val wordDao = NoteRoomDatabase.getWordDatabase(this).wordDao()
            coroutineScope.launch {
                wordDao.deleteAllNoteData()
                Toast.makeText(this@NoteListActivity, "All Note Deleted", Toast.LENGTH_SHORT).show()
            }
        }

        setResult(Activity.RESULT_OK)
        populateList()
    }

    private fun populateList() {
        coroutineScope.launch {
            val wordDao = NoteRoomDatabase.getWordDatabase(this@NoteListActivity).wordDao()
            val list = wordDao.getAllNoteData()
            withContext(Dispatchers.Main) {
                (binding.rvWords.adapter as NoteListAdapter).addItems(list)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == AddNoteActivity.ADD_WORD_REQUEST_CODE) {
            populateList()
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}