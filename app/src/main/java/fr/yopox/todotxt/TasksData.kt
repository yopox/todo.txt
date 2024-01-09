package fr.yopox.todotxt

import android.os.Environment
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object TasksData {
    val tasks = mutableListOf<Task>()

    fun reload() {
        tasks.clear()
        val file = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "todo" + File.separator + "todo.txt")
        val isr = InputStreamReader(file.inputStream())
        val bufferedReader = BufferedReader(isr)

        bufferedReader.readLines().forEachIndexed { index, line ->
            if (line.length > 3 && line[0] == '(' && line[1] in 'A'..'Z' && line[2] == ')' && line[3] == ' ') {
                tasks.add(Task(index + 1, line[1], line.substring(4)))
            } else if (line.isNotBlank()) {
                tasks.add(Task(index + 1, null, line))
            }
        }
    }
}