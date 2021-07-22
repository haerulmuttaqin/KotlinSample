package co.id.cpn.kotlinsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.kotlinsample.R
import co.id.cpn.kotlinsample.databinding.LoaderStateBinding

class LoaderStateViewHolder constructor(
    private val binding: LoaderStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }

        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoaderStateViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.loader_state, parent, false)
            val binding = LoaderStateBinding.bind(view)
            return LoaderStateViewHolder(binding, retry)
        }
    }
}