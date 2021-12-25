package com.mohmdib.fbmohmd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohmdib.fbmohmd.data.Todo

class TodosViewModel: ViewModel() {

    private  val repository = TodosRepository()
    private val _todosLiveData = MutableLiveData<List<Todo>>()
    val todosLiveData : LiveData<List<Todo>>
        get()  = _todosLiveData

    fun fetch(){
        repository?.fetch(_todosLiveData)
    }



}