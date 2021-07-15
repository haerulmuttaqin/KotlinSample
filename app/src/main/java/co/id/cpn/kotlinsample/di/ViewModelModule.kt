package co.id.cpn.kotlinsample.di

import co.id.cpn.kotlinsample.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module { 
    viewModel { MainViewModel(get()) }
}