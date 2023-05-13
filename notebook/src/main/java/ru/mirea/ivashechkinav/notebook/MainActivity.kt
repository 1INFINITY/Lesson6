package ru.mirea.ivashechkinav.notebook

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.ivashechkinav.notebook.databinding.ActivityMainBinding
import java.io.*
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoadFile.setOnClickListener {
            val fileName = binding.etvFileName.text.toString()
            if (fileName != "" && isExternalStorageReadable()) {
                binding.etvQuote.setText(readFileFromExternalStorage(fileName))
            }
        }
        binding.btnSaveFile.setOnClickListener {
            val fileName = binding.etvFileName.text.toString()
            if (fileName != "" && isExternalStorageWritable()) {
                val quoteText = binding.etvQuote.text.toString()
                writeFileToExternalStorage(fileName, quoteText)
            }
        }
    }

    private fun writeFileToExternalStorage(fileName: String, text: String) {
        val path: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(path, fileName)
        try {
            val fileOutputStream = FileOutputStream(file.getAbsoluteFile())
            val output = OutputStreamWriter(fileOutputStream)
            // Запись строки в файл
            output.write(text)
            // Закрытие потока записи
            output.close()
        } catch (e: IOException) {
            Log.w("ExternalStorage", "Error writing $file", e)
        }
    }

    /* Проверяем хранилище на доступность чтения и записи*/
    private fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    /* Проверяем внешнее хранилище на доступность чтения */
    private fun isExternalStorageReadable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state ||
                Environment.MEDIA_MOUNTED_READ_ONLY == state
    }

    private fun readFileFromExternalStorage(fileName: String): String {
        val path: File = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOCUMENTS
        )
        val file = File(path, fileName)
        try {
            val fileInputStream = FileInputStream(file.absoluteFile)
            val inputStreamReader = InputStreamReader(
                fileInputStream, Charset.defaultCharset()
            )
            var lines = ""
            val reader = BufferedReader(inputStreamReader)
            var line: String? = reader.readLine()
            while (line != null) {
                lines += line + "\n"
                line = reader.readLine()
            }
            return lines
        } catch (e: Exception) {
            Log.w("ExternalStorage", String.format("Read from file %s failed", e.message))
        }
        return ""
    }
}