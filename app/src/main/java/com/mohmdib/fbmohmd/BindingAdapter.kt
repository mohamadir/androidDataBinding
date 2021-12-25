package com.mohmdib.fbmohmd

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohmdib.fbmohmd.data.Todo


// region RecyclerView
@BindingAdapter("setItems")
fun setItems(recyclerView: RecyclerView, list: List<Todo>?) {
    (recyclerView.adapter as ListRecyclerAdapter).setItems(list)
}