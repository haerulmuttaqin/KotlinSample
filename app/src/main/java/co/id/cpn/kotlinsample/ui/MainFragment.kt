package co.id.cpn.kotlinsample.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import co.id.cpn.kotlinsample.MainViewModel
import co.id.cpn.kotlinsample.R
import co.id.cpn.kotlinsample.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when(movies) {
                    is ResultState.Success -> onResultSuccess(movies.data)
                    is ResultState.Error -> onResultError(movies.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        })
    }

    private val adapter: MainItemAdapter by lazy {
        MainItemAdapter(
            onItemClick = { movie ->
                Log.i("ListFragment", " $movie")
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id)
                )
            }
        )
    }

    private fun onResultEmpty() {
        Log.i("MainActivity", "Empty")
    }

    private fun onResultError(throwable: Throwable) {
        Log.i("MainActivity", "Error: $throwable")
    }

    private fun onResultSuccess(movies: List<ResultsItem>) {
        Log.i("MainActivity", "Success: $movies")
        movies.let {
            adapter.submitList(movies)
        }
        binding.apply {
            recyclerView.adapter = adapter
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}