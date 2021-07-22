package co.id.cpn.kotlinsample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import co.id.cpn.kotlinsample.MainViewModel
import co.id.cpn.kotlinsample.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowPlayingMovies.collect { movies ->
                Log.i("MainFragment", "$movies")
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

    private val adapter: MainItemAdapter by lazy {
        MainItemAdapter(
            onItemClick = { movie ->
                Log.i("ListFragment", " $movie")
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(movie!!.id)
                )
            }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}