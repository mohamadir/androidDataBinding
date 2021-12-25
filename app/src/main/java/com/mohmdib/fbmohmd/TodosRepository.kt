package com.mohmdib.fbmohmd

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mohmdib.fbmohmd.data.Todo

interface OnDataUpdated {
    fun onUpdated(list: ArrayList<Todo>)
}

class TodosRepository  {

    var list = FirebaseDatabase.getInstance().getReference("todos")

    fun fetch(liveData: MutableLiveData<List<Todo>>){
        list.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val list : ArrayList<Todo>  = arrayListOf()
                 data.children?.map { dataSnapshot ->
                   list.add( dataSnapshot.getValue(Todo::class.java)!!)
                }
                liveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}