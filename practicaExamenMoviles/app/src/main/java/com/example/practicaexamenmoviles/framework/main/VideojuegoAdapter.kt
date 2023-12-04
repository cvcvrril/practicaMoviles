package com.example.practicaexamenmoviles.framework.main

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaexamenmoviles.R
import com.example.practicaexamenmoviles.databinding.ViewVideojuegoBinding
import com.example.practicaexamenmoviles.domain.model.Videojuego

class VideojuegoAdapter(
    val context: Context,
    val actions: VideojuegoActions
): ListAdapter<Videojuego, VideojuegoAdapter.ItemViewholder>(DiffCallBack()) {

    interface VideojuegoActions{
        fun onDelete(videojuego: Videojuego)
        fun onStrartSelectMode(videojuego: Videojuego)
        fun itemHasClicked(videojuego: Videojuego)
        fun onClickItem(idVideojuego: Int)
    }

    private var selectedVideojuegos = mutableListOf<Videojuego>()
    private var selectedMode: Boolean = false

    fun startSelectMode(){
        selectedMode = true
        notifyDataSetChanged()
    }

    fun resetSelectMode(){
        selectedMode = false
        selectedVideojuegos.clear()
        notifyDataSetChanged()
    }

    fun setSelectedItems(videojuegosSeleccionados: List<Videojuego>){
        selectedVideojuegos.clear()
        selectedVideojuegos.addAll(videojuegosSeleccionados)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder{
        return ItemViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_videojuego, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var binding = ViewVideojuegoBinding.bind(itemView)

        fun bind(item: Videojuego){
            itemView.setOnClickListener{
                actions.onClickItem(item.id)
            }
            itemView.setOnLongClickListener{
                if (!selectedMode){
                    selectedMode = true
                    actions.onStrartSelectMode(item)
                    item.isSelected = true
                    binding.selected.isChecked= true
                    notifyItemChanged(adapterPosition)
                }
                true
            }
            with(binding){
                selected.setOnClickListener{
                    if (selectedMode){
                        if (binding.selected.isChecked){
                            item.isSelected = true
                            itemView.setBackgroundColor(Color.GREEN)
                            selectedVideojuegos.add(item)
                        }else{
                            item.isSelected = false
                            itemView.setBackgroundColor(Color.TRANSPARENT)
                            selectedVideojuegos.remove(item)
                        }
                        actions.itemHasClicked(item)
                    }
                }

                tvTitulo.text = item.titulo
                tvId.text = item.id.toString()
                if (selectedMode) selected.visibility = View.VISIBLE
                else{
                    item.isSelected = false
                    selected.visibility = View.GONE
                }

                if (selectedVideojuegos.contains(item)){
                    itemView.setBackgroundColor(Color.GREEN)
                    binding.selected.isChecked = true
                }else{
                    itemView.setBackgroundColor(Color.TRANSPARENT)
                    binding.selected.isChecked = false
                }
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Videojuego>() {
        override fun areItemsTheSame(oldItem: Videojuego, newItem: Videojuego): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Videojuego, newItem: Videojuego): Boolean {
            return oldItem == newItem
        }
    }

    val swipeGesture = object: SwipeGesture(context){

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when(direction){
                ItemTouchHelper.LEFT ->{
                    selectedVideojuegos.remove(currentList[viewHolder.adapterPosition])
                    val deletedVideojuego = currentList[viewHolder.adapterPosition]
                    actions.onDelete(currentList[viewHolder.adapterPosition])
                    if (selectedMode){
                        actions.itemHasClicked(currentList[viewHolder.adapterPosition])
                    }
                    if (currentList.contains(deletedVideojuego)){
                        notifyItemChanged(viewHolder.adapterPosition)
                    }
                }
            }
        }

    }

}



