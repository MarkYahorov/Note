package com.example.note

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class MainScreen() : Fragment() {

    private val thisNoteList = mutableListOf<Note>()
    private lateinit var noteRecycler: RecyclerView
    private lateinit var addBtn: Button

    private val PATH = ""

    interface sendNameOfFileInFragment{
        fun onSaveData(string: String?)
    }

    private var sendName: sendNameOfFileInFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            sendName = context as sendNameOfFileInFragment
        }catch (e: Exception){
            Toast.makeText(context, "IMPL INTERFACE", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)
        initAll(view)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            addBtn.visibility = View.VISIBLE
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            addBtn.visibility = View.GONE
        }
        return view
    }


    override fun onStart() {
        super.onStart()
        createListOfFiles()
        createRecycler()
        goToAddScreen()
    }

    private fun initAll(view: View){
            noteRecycler = view.findViewById(R.id.note_recycler_from_fragment)
            addBtn = view.findViewById(R.id.add_btn)

    }

//    private fun takeArgs(){
//        val bundle = arguments
//        val name = bundle?.getString("THIS_FILE")
//        val data = bundle?.getString("THIS_DATA")
//        thisNoteList.add(Note(name,data))
//    }

    private fun goToAddScreen() {
        addBtn.setOnClickListener {
            sendName?.onSaveData("")
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createListOfFiles() {
        var count = 0
        while (thisNoteList.size < context?.filesDir?.absoluteFile?.listFiles()?.size!!) {
            thisNoteList.add(
                Note(
                    context?.filesDir?.absoluteFile?.listFiles()!![count].name,
                    SimpleDateFormat("dd//MM/yyyy")
                        .format(context?.filesDir?.absoluteFile?.listFiles()!![count].lastModified())
                )
            )
            count++
        }
    }

    private fun createRecycler() {
        noteRecycler.layoutManager = LinearLayoutManager(
            this.context, LinearLayoutManager.VERTICAL, false
        )
        noteRecycler.adapter = NoteAdapter(
            thisNoteList, OnClick = {
                sendName?.onSaveData(it.text)
            })
    }
}