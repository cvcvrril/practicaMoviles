package com.example.practicaexamenmoviles.framework.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.practicaexamenmoviles.R
import com.example.practicaexamenmoviles.databinding.ActivityMainBinding
import com.example.practicaexamenmoviles.domain.model.Videojuego
import com.example.practicaexamenmoviles.framework.personaje.PersonajeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var videojuegoAdapter: VideojuegoAdapter
    private var anteriorState: MainState? = null
    private val viewModel: MainViewModel by viewModels()

    private val callback by lazy {
        configContextBar()
    }

    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videojuegoAdapter = VideojuegoAdapter(this,
            object : VideojuegoAdapter.VideojuegoActions{
                override fun onDelete(videojuego: Videojuego) {
                    viewModel.handleEvent(MainEvent.DeleteVideojuego(videojuego))
                }

                override fun onStrartSelectMode(videojuego: Videojuego) {
                    viewModel.handleEvent(MainEvent.StartSelectedMode)
                    viewModel.handleEvent(MainEvent.SeleccionaVideojuegos(videojuego))
                }

                override fun itemHasClicked(videojuego: Videojuego) {
                    viewModel.handleEvent(MainEvent.SeleccionaVideojuegos(videojuego))
                }

                override fun onClickItem(idVideojuego: Int) {
                    click(idVideojuego)
                }

            })
        with(binding){
            rvCustomers.adapter = videojuegoAdapter
            rvCustomers.layoutManager= GridLayoutManager(this@MainActivity,1)
        }

        val touchHelper = ItemTouchHelper(videojuegoAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvCustomers)
        observarViewModel()
        viewModel.handleEvent(MainEvent.GetVideojuegos)
        configAppBar()

    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this){state->
            if (state.videojuegos != anteriorState?.videojuegos){
                if (state.videojuegos.isEmpty()){
                    videojuegoAdapter.submitList(emptyList())
                }else{
                    videojuegoAdapter.submitList(state.videojuegos)
                }
            }

            actionMode?.title =
                "${state.viedojuegosSelected.size}" + "selected"
            if (state.viedojuegosSelected != anteriorState?.viedojuegosSelected){
                videojuegoAdapter.setSelectedItems(state.viedojuegosSelected)
            }
            if (state.selectedMode != anteriorState?.selectedMode){
                if (state.selectedMode){
                    videojuegoAdapter.startSelectMode()
                    startSupportActionMode(callback)?.let {
                        actionMode = it
                    }
                } else{
                    videojuegoAdapter.resetSelectMode()
                    actionMode?.finish()
                    binding.topAppBar.visibility = View.VISIBLE
                }
            }
            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.handleEvent(MainEvent.ErrorVisto)
            }

            anteriorState = state

        }
    }

    private fun click(id: Int){
        val intent = Intent(this, PersonajeActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun configContextBar(): ActionMode.Callback {
        return object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.context_bar, menu)
                binding.topAppBar.visibility = View.GONE
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.delete -> {
                        viewModel.handleEvent(MainEvent.DeleteVideojuegosSeleccionados())
                        true
                    }

                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                binding.topAppBar.visibility = View.VISIBLE
                viewModel.handleEvent(MainEvent.ResetSelectMode)
            }
        }
    }


    private fun configAppBar() {
        val actionSearch = binding.topAppBar.menu.findItem(R.id.search).actionView as SearchView

        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filtro ->
                    viewModel.handleEvent(MainEvent.GetVideojuegosFiltrados(filtro))
                }
                return false
            }

        })

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.search->{
                    true
                }
                else-> false
            }
        }
    }

}