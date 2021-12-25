package com.mohmdib.fbmohmd

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohmdib.fbmohmd.data.Todo
import java.lang.ref.WeakReference

interface OnItemChekedListener{
    fun onItemChecked(id: String, isChecked: Boolean)
}

class ListRecyclerAdapter(var todoList: List<Todo>,
                          private val callbackWeakRef: WeakReference<TodoListInterface>
) :
    RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>() {


    interface TodoListInterface {
    }

    private val todoListLive = mutableListOf<Todo>()


    open var onListClickListen: ListClickListen? = null
    open var onItemCheckedListener: OnItemChekedListener? = null

    open fun setListClickListen(listner: ListClickListen?){
        this.onListClickListen = listner
    }

    open fun setItemChckedListener(listner: OnItemChekedListener?){
        this.onItemCheckedListener = listner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(todoList: List<Todo>?) {
        this.todoListLive.clear()
        this.todoListLive.addAll(todoList ?: emptyList())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        todoListLive.get(position)


        var todoItem = todoList.get(position)
        holder.mText?.text = todoItem.title
        holder.checkBox.isChecked = todoList.get(position).isCompleted
        holder.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                onItemCheckedListener?.onItemChecked(todoItem.id, isChecked)
            }


        })
    }

    override fun getItemCount(): Int {
        return todoListLive.size
    }


     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var mText: TextView
        var checkBox: CheckBox
        init {
            mText = itemView?.findViewById(R.id.textView)
            checkBox = itemView?.findViewById(R.id.checkbox)
        }

        override fun onClick(view: View) {
        }


    }

    companion object {
        private const val TAG = "RecyclerAdapter"
    }
}