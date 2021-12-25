package com.mohmdib.fbmohmd

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mohmdib.fbmohmd.data.Todo
import com.mohmdib.fbmohmd.databinding.ActivityMainBinding
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList


class TodoListActivity : AppCompatActivity(), ListRecyclerAdapter.TodoListInterface {

    var recyclerAdapter: ListRecyclerAdapter? = null

    private val viewModel: TodosViewModel by lazy {
        ViewModelProvider(this)[TodosViewModel::class.java]
    }

    var todoList: ArrayList<Todo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        var viewModel: TodosViewModel = ViewModelProvider(this)[TodosViewModel::class.java]
        binding.viewModel = viewModel
        todoList = ArrayList()
        viewModel.fetch()
        recyclerAdapter = ListRecyclerAdapter(todoList!!, WeakReference(this))
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView?.adapter = recyclerAdapter
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerView?.addItemDecoration(dividerItemDecoration)

        recyclerAdapter?.setItemChckedListener(object: OnItemChekedListener{
            override fun onItemChecked(id: String, isChecked: Boolean) {
                updateValue(id, isChecked)
            }
        })
//        saveToDatabase()
        getDatabase()
//        updateValue()
        getToken()
    }

    private fun updateValue(id: String, isChecked: Boolean) {
        var ref = FirebaseDatabase.getInstance().getReference("todos")
        ref.child(id).child("completed").setValue(isChecked)
    }




    private fun getDatabase() {

        var list = FirebaseDatabase.getInstance().getReference("todos")
        list.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                Log.i("","")
                todoList?.clear()
                for(item in data.children){
                    val element = item.getValue(Todo::class.java)!!
                    element.id = item.key!!
                    todoList?.add(element)
                }
                recyclerAdapter?.todoList = todoList!!
                recyclerAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun saveToDatabase() {
        var todo = Todo("0","clean my room", true)
        val ref = FirebaseDatabase.getInstance().getReference("todos")
        var id = ref.push().key
        todo.id = id!!
        ref.child(id!!).setValue(todo).addOnCanceledListener {
            Toast.makeText(this@TodoListActivity, "completed", Toast.LENGTH_LONG)
        }
    }




    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("my-token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.d("my-token", token)
        })
    }

}