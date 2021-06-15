package com.example.note

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import kotlin.time.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity(), MainScreen.sendNameOfFileInFragment {

    private val thisNoteList = mutableListOf<Note>()
    private lateinit var noteRecycler: RecyclerView
    private lateinit var addBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initAll()

    }

    override fun onStart() {
        super.onStart()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_container, MainScreen(), "add Screen")
                .commit()
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            val mainScreen = MainScreen()
            val addScreen = AddScreen()
            supportFragmentManager.beginTransaction()
                .add(R.id.container2, mainScreen)
                .add(R.id.contin, addScreen)
                .commit()
        }
    }

    override fun onSaveData(string: String?) {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val intent = Intent(this, AddOrUpdateActivity::class.java)
                .putExtra("THIS_NAME_FILE", string)
            startActivity(intent)
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val bundle = Bundle()
            val addScreen = AddScreen()
            bundle.putString("THIS", string)
            addScreen.arguments = bundle
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.contin, addScreen)
                .addToBackStack(null)
                .commit()
        }
    }

//    private fun initAll() {
//        noteRecycler = findViewById(R.id.note_recycler)
//        addBtn = findViewById(R.id.add_btn)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onStart() {
//        super.onStart()
//        createListOfFiles()
//        goToAddScreen()
//        //getInfonotrmFromSharedPref()
//        createRecycler()
//        createListOfFiles()
//        Toast.makeText(this, "${filesDir.absoluteFile.listFiles().size}", Toast.LENGTH_LONG).show()
//    }
//
//    private fun goToAddScreen() {
//        addBtn.setOnClickListener {
//            addOrUpdateNote()
//        }
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    private fun createListOfFiles() {
//        var count = 0
//        while (thisNoteList.size < filesDir.absoluteFile.listFiles().size) {
//            thisNoteList.add(
//                Note(
//                    filesDir.absoluteFile.listFiles()[count].name,
//                    SimpleDateFormat("dd//MM/yyyy")
//                        .format(filesDir.absoluteFile.listFiles()[count].lastModified())
//                )
//            )
//            count++
//        }
//    }
//
//    private fun createRecycler() {
//        noteRecycler.layoutManager = LinearLayoutManager(
//            this, LinearLayoutManager.VERTICAL, false
//        )
//        noteRecycler.adapter = NoteAdapter(
//            thisNoteList, OnClick = {
//                val intent = Intent(this, AddOrUpdateActivity::class.java)
//                    .putExtra("FILE_NAME", it.text)
//                startActivity(intent)
//            })
//    }
//
//    private fun addOrUpdateNote() {

//        startActivityForResult(intent, 1)
//    }

//    private fun getInfonotrmFromSharedPref() {
//        val name = getSharedPreferences("FILE", MODE_PRIVATE).getString("TEXT", null)
//        val data = getSharedPreferences("FILE", MODE_PRIVATE).getString("DATA", null)
//        thisNoteList.add(Note(name, data))
//    }

}