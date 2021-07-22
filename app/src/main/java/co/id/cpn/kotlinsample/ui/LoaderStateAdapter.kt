package co.id.cpn.kotlinsample.ui

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoaderStateAdapter constructor(private val retry: () -> Unit) : LoadStateAdapter<LoaderStateViewHolder>() {
    
    override fun onBindViewHolder(holder: LoaderStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderStateViewHolder {
        return LoaderStateViewHolder.create(parent, retry)
    }

}