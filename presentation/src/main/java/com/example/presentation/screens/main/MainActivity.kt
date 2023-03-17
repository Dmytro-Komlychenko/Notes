package com.example.presentation.screens.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding
import com.example.presentation.app.App
import com.example.presentation.models.Note
import com.example.presentation.screens.notelist.NoteListViewModel
import com.example.presentation.screens.notelist.NoteListViewModelFactory
import com.example.presentation.utils.CalendarConverter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private lateinit var viewModel: NoteListViewModel

    @Inject
    lateinit var numberViewModelFactory: NoteListViewModelFactory

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, numberViewModelFactory)[NoteListViewModel::class.java]

        setCreateNoteButtonHandler()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController.currentDestination

        navHostFragment.navController
            .addOnDestinationChangedListener { controller, destination, arguments ->
                binding.fabCreateNote.isVisible = destination.id != R.id.noteFragment
            }

        viewModel.notes.observe(this) {
            if (viewModel.notes.value!!.isEmpty() && navHostFragment.navController.currentDestination?.id != R.id.emptyFragment) {
                viewModel.removePosition = null
                startEmptyFragment()
            } else if (viewModel.notes.value!!.isNotEmpty() && navHostFragment.navController.currentDestination?.id == R.id.emptyFragment) {
                startNoteListFragmentFromEmpty()
            }
        }

        connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d("MyFragment", "Internet connection available")
                if (navHostFragment.navController.currentDestination?.id != R.id.noInternetFragment) return
                runOnUiThread {
                    startNoteListFragmentFromNoInternet()
                }
                if (viewModel.isFirstLaunchApp!!) {
                    viewModel.restartLoading()
                }
            }

            override fun onLost(network: Network) {
                viewModel.stopLoading()
                runOnUiThread {
                    viewModel.internetConnectionLost.value = true
                }
                if (!viewModel.isFirstLaunchApp!!) {
                    Toast.makeText(
                        applicationContext,
                        "Internet connection lost",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    runOnUiThread {
                        startNoInternetFragment()
                    }
                }
                Log.d("MyFragment", "Internet connection lost")
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (!isInternetConnected(this)) {
            viewModel.stopLoading()
            startNoInternetFragment()
        }
    }

    private fun isInternetConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected ?: false
    }

    private fun startNoInternetFragment() {
        Log.i("lifecycle", "action_noteListFragment_to_noInternetFragment")
        findNavController(binding.fragmentContainerView.id).navigate(
            R.id.action_noteListFragment_to_noInternetFragment
        )
    }

    private fun startEmptyFragment() {
        Log.i("lifecycle", "action_noteListFragment_to_emptyFragment")
        findNavController(binding.fragmentContainerView.id).navigate(
            R.id.action_noteListFragment_to_emptyFragment
        )
    }

    private fun startNoteListFragmentFromEmpty() {
        Log.i("lifecycle", "action_emptyFragment_to_noteListFragment")
        findNavController(binding.fragmentContainerView.id).navigate(
            R.id.action_emptyFragment_to_noteListFragment
        )
    }

    private fun startNoteListFragmentFromNoInternet() {
        Log.i("lifecycle", "action_noInternetFragment_to_noteListFragment")
        lifecycleScope.launch {
            viewModel.restartLoading()
        }
        findNavController(binding.fragmentContainerView.id).navigate(
            R.id.action_noInternetFragment_to_noteListFragment
        )
    }

    private fun setCreateNoteButtonHandler() {
        binding.fabCreateNote.setOnClickListener {
            Snackbar.make(binding.root, "Note is created", Snackbar.LENGTH_SHORT).show()

            if (viewModel.notes.value?.isEmpty() == true) {
                startNoteListFragmentFromEmpty()
            }

            val newId =
                if (viewModel.notes.value?.isEmpty() == true) 0
                else viewModel.notes.value?.first()!!.id.plus(1)

            val newNote =
                Note(
                    newId,
                    "Новая заметка",
                    "",
                    CalendarConverter.convertToString(CalendarConverter.getCurrentCalendar())
                )
            viewModel.addNote(newNote)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}