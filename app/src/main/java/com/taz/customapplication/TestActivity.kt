package com.taz.customapplication

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_test.*
import timber.log.Timber
import timber.log.Timber.DebugTree


class TestActivity : AppCompatActivity() {
    companion object {
        const val TAG = "TestActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        Timber.plant(DebugTree())

        loadData()

        paintColorChoiceGroup.setOnCheckedChangeListener { group, checkedId ->
            val color = when (group.checkedChipId) {
                R.id.blue ->  Color.BLUE
                R.id.red ->  Color.RED
                R.id.green ->  Color.GREEN
                R.id.yellow ->  Color.YELLOW
                else ->  Color.BLUE
            }
            drawingView setColor color
        }

        paintBrushSizeChoiceGroup.setOnCheckedChangeListener { group, checkedId ->
            val brushSize = when (group.checkedChipId) {
                R.id.small ->  10f
                R.id.large ->  30f
                else ->  20f
            }
            drawingView setBrushSize  brushSize
        }

        clear.setOnClickListener {
            drawingView.clear()
         }
        undo.setOnClickListener {
            drawingView.undo()
        }
        redo.setOnClickListener {
            drawingView.redo()
        }
        save.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val sharedPreferences =
            getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(drawingView.paths)
        Timber.d("Save Path List Json $json")
        editor.putString("path list", json)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences =
            getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        val builder = GsonBuilder()
        builder.registerTypeAdapterFactory(PostProcessingEnabler())
        val gson = builder.create()
        val json = sharedPreferences.getString("path list", null)
        val type = object : TypeToken<ArrayList<SerializablePath?>?>() {}.type
        gson.fromJson<ArrayList<SerializablePath>>(json, type)?.let{
            drawingView.paths = it
            Timber.d("Path List ${drawingView.paths.size} ${drawingView.paths}")
            drawingView.invalidate()
        }

    }



}
