package com.example.note

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class AddScreen : Fragment() {

    private lateinit var thisText:EditText

    private var name = ""
    private val PATH = "data/user/0/com.example.note/files/"

    companion object{

        fun newInstance(string: String?):AddScreen{
            val addScreen = AddScreen()
            addScreen.arguments?.putString("THIS", string)
            return addScreen
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_screen, container,false)
        thisText = view.findViewById(R.id.note_text_from_fragment)
        return view
    }

    fun saveThisFileName(thisName:String?){
        updateFile(thisName)
    }

    override fun onStart() {
        super.onStart()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val nameThis = activity?.intent?.getStringExtra("THIS_NAME_FILE")
            updateFile(nameThis)
        }
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val bundle = arguments
            if (bundle!=null){
                updateFile(bundle.getString("THIS"))
            }
        }

    }

    private fun updateFile(string: String?){
        try {
            val inputStream = FileInputStream("data/user/0/com.example.note/files/$string")
            val inputReader = InputStreamReader(inputStream)
            val buffer = BufferedReader(inputReader)
            val stringBuffer = StringBuffer()
            buffer.forEachLine {
                stringBuffer.append("$it\n")
            }
            thisText.setText(stringBuffer.toString())
            inputStream.close()
        }catch (e: FileNotFoundException){
            Toast.makeText(this.context, "NOFILE", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Toast.makeText(this.context, "IO", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        if (thisText.text.isNotEmpty()) {
            saveThisTextInFile()
        }
    }

    private fun saveThisTextInFile() {
        val nameOfFile = File(thisText.text.lines()[0].trim())
        val text = thisText.text.toString()
        val data = SimpleDateFormat("dd/mm/yyyy").format(Date()).toString()
        var count = 0
        val list = activity?.filesDir?.listFiles()
        if (activity?.intent?.getStringExtra("THIS_NAME_FILE") != nameOfFile.name){
            try {
                val fileOutputStream = activity?.openFileOutput(
                    "${thisText.text.lines()[0].trim()}(${count})",
                    AppCompatActivity.MODE_PRIVATE
                )
                fileOutputStream?.write(text.toByteArray())
                fileOutputStream?.close()
            } catch (e: FileNotFoundException) {
                Toast.makeText(this.context, "No file", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(this.context, "IO", Toast.LENGTH_LONG).show()
            }
        }
        list?.forEach {
            count++
            if (thisText.text.lines()[0].trim() == it.name && arguments?.getString("THIS") == it.name) {
                try {
                    val fileOutputStream = activity?.openFileOutput(
                        thisText.text.lines()[0].trim(),
                        AppCompatActivity.MODE_PRIVATE
                    )
                    fileOutputStream?.write(text.toByteArray())
                    fileOutputStream?.close()
                    return
                } catch (e: FileNotFoundException) {
                    Toast.makeText(this.context, "No file", Toast.LENGTH_LONG).show()
                } catch (e: IOException) {
                    Toast.makeText(this.context, "IO", Toast.LENGTH_LONG).show()
                }
            } else if(activity?.intent?.getStringExtra("THIS_NAME_FILE") == it.name){
                try {
                    val fileOutputStream = activity?.openFileOutput(
                        "${thisText.text.lines()[0].trim()}",
                        AppCompatActivity.MODE_PRIVATE
                    )
                    fileOutputStream?.write(text.toByteArray())
                    fileOutputStream?.close()
                    return
                } catch (e: FileNotFoundException) {
                    Toast.makeText(this.context, "No file", Toast.LENGTH_LONG).show()
                } catch (e: IOException) {
                    Toast.makeText(this.context, "IO", Toast.LENGTH_LONG).show()
                }
            }
        }


//        if (nameOfFile){
//            try {
//                val fileOutputStream = activity?.openFileOutput(
//                    nameOfFile.name,
//                    AppCompatActivity.MODE_PRIVATE
//                )
//                fileOutputStream?.write(text.toByteArray())
//                fileOutputStream?.close()
//            } catch (e: FileNotFoundException) {
//                Toast.makeText(this.context, "No file", Toast.LENGTH_LONG).show()
//            } catch (e: IOException) {
//                Toast.makeText(this.context, "IO", Toast.LENGTH_LONG).show()
//            }
//        }
//        if (!nameOfFile.exists()){
//            try {
//                val fileOutputStream = activity?.openFileOutput(
//                    nameOfFile.name,
//                    AppCompatActivity.MODE_PRIVATE
//                )
//                fileOutputStream?.write(text.toByteArray())
//                fileOutputStream?.close()
//            } catch (e: FileNotFoundException) {
//                Toast.makeText(this.context, "No file", Toast.LENGTH_LONG).show()
//            } catch (e: IOException) {
//                Toast.makeText(this.context, "IO", Toast.LENGTH_LONG).show()
//            }
//        }
//
    }
}