package com.tahajjud.mushaf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.io.BufferedReader
import java.io.InputStreamReader

data class Ayah(val surah: String, val ayah: String, val text: String)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ayahs = loadQuranData()
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = QuranAdapter(ayahs)
    }

    private fun loadQuranData(): List<Ayah> {
        val list = mutableListOf<Ayah>()
        try {
            val inputStream = assets.open("quran-simple.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String? = reader.readLine()
            while (line != null) {
                val parts = line.split("|")
                if (parts.size >= 3) {
                    list.add(Ayah(parts[0], parts[1], parts[2]))
                }
                line = reader.readLine()
            }
            reader.close()
        } catch (e: Exception) { e.printStackTrace() }
        return list
    }
}

class QuranAdapter(private val ayahs: List<Ayah>) : RecyclerView.Adapter<QuranAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSurahName: TextView = view.findViewById(R.id.tvSurahName)
        val tvAyahText: TextView = view.findViewById(R.id.tvAyahText)
        val tvAyahNumber: TextView = view.findViewById(R.id.tvAyahNumber)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.id.item_page, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ayahs[position]
        holder.tvSurahName.text = "سورة رقم " + item.surah
        holder.tvAyahText.text = item.text
        holder.tvAyahNumber.text = "آية رقم " + item.ayah
    }
    override fun getItemCount(): Int = ayahs.size
}
