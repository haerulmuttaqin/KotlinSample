package co.id.cpn.kotlinsample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResultState
import co.id.cpn.kotlinsample.MainViewModel
import co.id.cpn.kotlinsample.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {
    
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.id
        viewModel.movieDetailBy(id).observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                when(movie) {
                    is ResultState.Success -> onResultSuccess(movie.data)
                    is ResultState.Error -> onResultError(movie.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        })
    }

    private fun onResultEmpty() {
        Log.i("DetailFragment", "Empty")
    }

    private fun onResultError(throwable: Throwable) {
        Log.i("DetailFragment", "Error: $throwable")
    }

    private fun onResultSuccess(movie: ResponseMovieDetail) {
        Log.i("DetailFragment", "Success: $movie")
        binding.itemTitle.text = movie.title
        binding.itemDescription.text = movie.overview
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}