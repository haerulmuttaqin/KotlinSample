package co.id.cpn.kotlinsample.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import androidx.paging.LoadState
import co.id.cpn.kotlinsample.MainViewModel
import co.id.cpn.kotlinsample.databinding.ActivityPagingBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagingActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPagingBinding

    private val viewModel: MainViewModel by viewModel()

    private val adapter: MainItemAdapter by lazy {
        MainItemAdapter(
            onItemClick = { movie ->
                Log.i("MainActivity", " $movie")
                /*findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(movie!!.id)
                )*/
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPagingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        lifecycleScope.launch {
            viewModel.nowPlayingMovies.collect { movies ->
            Log.i("MainActivity", "$movies")
                adapter.submitData(movies)
            }
        }

        binding.recyclerView.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter { adapter.retry() },
                footer = LoaderStateAdapter { adapter.retry() }
            )
        adapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Snackbar.make(binding.root, "${it.error}" , Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.recyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.retryButton.visibility = View.VISIBLE
            binding.errorMsg.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.retryButton.visibility = View.GONE
            binding.errorMsg.visibility = View.GONE
        }
    }
}