package com.example.practicaexamenmoviles.framework.personaje

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaexamenmoviles.R
import com.example.practicaexamenmoviles.databinding.ViewPersonajeBinding
import com.example.practicaexamenmoviles.domain.model.Personaje
import com.example.practicaexamenmoviles.framework.main.SwipeGesture

class PersonajeAdapter(
    val context: Context,
    val actions: PersonajeActions
) : ListAdapter<Personaje, PersonajeAdapter.ViewHolder>(DiffCallBack()){

    fun interface PersonajeActions {
        fun onDelete(personaje: Personaje)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_personaje, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ViewPersonajeBinding.bind(itemView)

        fun bind(item: Personaje){
            binding.tvId.text = item.id.toString()
            binding.tvNombre.text = item.name
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Personaje>() {
        override fun areItemsTheSame(oldItem: Personaje, newItem: Personaje): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Personaje, newItem: Personaje): Boolean {
            return oldItem == newItem
        }

    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val deletedOrder = getItem(viewHolder.adapterPosition)
                    actions.onDelete(deletedOrder)
                    currentList.toMutableList().remove(deletedOrder)
                    notifyItemRemoved(viewHolder.adapterPosition)
                }
            }
        }
    }
}