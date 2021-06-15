package com.example.note

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class AddOrUpdateActivity : AppCompatActivity(), MainScreen.sendNameOfFileInFragment {

    private lateinit var thisText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_update)
        //thisText = findViewById(R.id.note_text)
    }

    override fun onStart() {
        super.onStart()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.add_container, AddScreen())
                .commit()
            getFileNameAndSendToFragment()
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            val mainScreen = MainScreen()
            val addScreen = AddScreen()
            supportFragmentManager.beginTransaction()
                .add(R.id.container2activity2, mainScreen)
                .add(R.id.continActivity2, addScreen)
                .commit()
        }
    }

    private fun getFileNameAndSendToFragment() {
        val name = intent.getStringExtra("THIS_NAME_FILE")
        AddScreen.newInstance(name)
    }

    override fun onSaveData(string: String?) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
//
//    override fun onStart() {
//        super.onStart()
//
//        updateFile()
//    }
//
//    private fun updateFile(){
//        val nameFile = intent.getStringExtra("FILE_NAME")
//        try {
//            val inputStream: FileInputStream = openFileInput(nameFile)
//            val inputReader = InputStreamReader(inputStream)
//            val buffer = BufferedReader(inputReader)
//            val stringBuffer = StringBuffer()
//            buffer.forEachLine {
//                stringBuffer.append("$it\n")
//            }
//            thisText.setText(stringBuffer.toString())
//            inputStream.close()
//        }catch (e:FileNotFoundException){
//            Toast.makeText(this, getDir("data.txt", MODE_PRIVATE).name, Toast.LENGTH_LONG).show()
//        } catch (e: IOException) {
//            Toast.makeText(this, "IO", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        saveThisTextInFile()
//    }
//
//    private fun saveThisTextInFile() {
//        val nameOfFile = File("${thisText.text.lines()[0].trim() }.txt")
//        val text = thisText.text.toString()
//        val data = SimpleDateFormat("dd/mm/yyyy").format(Date()).toString()
//        try {
//            val fileOutputStream = openFileOutput(nameOfFile.name, MODE_PRIVATE)
//            fileOutputStream.write(text.toByteArray())
//            fileOutputStream.close()
//            Toast.makeText(this, "$filesDir", Toast.LENGTH_LONG).show()
//        } catch (e: FileNotFoundException) {
//            Toast.makeText(this, "No file", Toast.LENGTH_LONG).show()
//        } catch (e: IOException) {
//            Toast.makeText(this, "IO", Toast.LENGTH_LONG).show()
//        }
//        getSharedPreferences("FILE", Context.MODE_PRIVATE)
//            .edit()
//            .apply {
//                putString("TEXT", nameOfFile.name)
//                putString("DATA", data)
//            }.apply()
//    }
}