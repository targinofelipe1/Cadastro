package com.example.cadastro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MyAdapter(val lista: MutableList<String>): RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var onItemClickRecyclerView: OnItemClickRecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listagem, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyHolder, position: Int) {
        val nome = this.lista.get(position)
        holder.tvNome.text = nome
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    fun add(nome: String){
        this.lista.add(nome)
        this.notifyItemInserted(this.lista.size)
    }

    fun del(index: Int){
        this.lista.removeAt(index)
        this.notifyItemRemoved(index)
        this.notifyItemRangeChanged(index, this.lista.size)
    }

    fun mov(from: Int, to: Int){
        Collections.swap(this.lista, from, to)
        this.notifyItemMoved(from, to)
    }

    inner class MyHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var tvNome: TextView

        init {
            this.tvNome = itemView.findViewById(R.id.tvNomeList)

            itemView.setOnClickListener {
                this@MyAdapter.onItemClickRecyclerView?.onItemClick(this.adapterPosition)
            }
        }
    }
}