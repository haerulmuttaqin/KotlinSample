package co.id.cpn.kotlinsample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import co.id.cpn.kotlinsample.MainViewModel
import co.id.cpn.kotlinsample.databinding.FragmentMainBinding
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