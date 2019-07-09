package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ViewItemBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.recyclerView.apply {
            adapter = PersonAdapter(listOf(Person("Larry"), Person("Moe"), Person("Curly")))
            layoutManager = LinearLayoutManager(context)
        }
    }
}

data class Person(val name: String)

class PersonAdapter(people: List<Person>) : RadioAdapter<Person>(people) {
    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.textView.text = this[position].name
    }
}

abstract class RadioAdapter<T>(private val items: List<T>) : RecyclerView.Adapter<RadioAdapter<T>.RadioViewHolder>() {

    private var selectedPosition = -1

    inner class RadioViewHolder(val binding: ViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val clickHandler: (View) -> Unit = {
            selectedPosition = adapterPosition
            notifyDataSetChanged()
        }

        init {
            binding.apply {
                root.setOnClickListener(clickHandler)
                radioButton.setOnClickListener(clickHandler)
            }
        }
    }

    override fun getItemCount() = items.size

    operator fun get(position: Int): T = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        inflate<ViewItemBinding>(from(parent.context), R.layout.view_item, parent, false).run {
            RadioViewHolder(this)
        }

    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        holder.binding.radioButton.isChecked = position == selectedPosition
    }

}
