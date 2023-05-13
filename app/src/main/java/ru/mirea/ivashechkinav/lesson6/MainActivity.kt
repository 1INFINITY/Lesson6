package ru.mirea.ivashechkinav.lesson6

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.mirea.ivashechkinav.lesson6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        textsInit()
        binding.button.setOnClickListener(this::saveTextInPrefs)
        setContentView(binding.root)
    }

    private fun textsInit() {
        binding.etv1.setText(sharedPrefs.getString(GROUP_KEY, ""))
        binding.etv2.setText(sharedPrefs.getString(POSITION_KEY, ""))
        binding.etv3.setText(sharedPrefs.getString(FAVORITE_FILM_KEY, ""))
    }

    private fun saveTextInPrefs(view: View) {
        with(sharedPrefs.edit()) {
            putString(GROUP_KEY, binding.etv1.text.toString())
            putString(POSITION_KEY, binding.etv2.text.toString())
            putString(FAVORITE_FILM_KEY, binding.etv3.text.toString())
            apply()
        }
    }

    companion object {
        const val GROUP_KEY = "group"
        const val POSITION_KEY = "position"
        const val FAVORITE_FILM_KEY = "favoriteKey"
    }
}