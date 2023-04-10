package com.example.cadastro

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.content.DialogInterface.OnClickListener
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rvNomes:RecyclerView
    private lateinit var ffAdicionar: FloatingActionButton
    private var lista = mutableListOf<String>()
    private lateinit var etNome: EditText
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rvNomes = findViewById(R.id.rvNomes)
        this.ffAdicionar = findViewById(R.id.ffAdicionar)

        this.ffAdicionar.setOnClickListener{ add() }

        this.rvNomes.adapter = MyAdapter(this.lista)
        (this.rvNomes.adapter as MyAdapter).onItemClickRecyclerView = OnItemClick()

        this.tts = TextToSpeech(this, null)

        ItemTouchHelper(OnSwipe()).attachToRecyclerView(this.rvNomes)


    }

    fun add(){
        this.etNome = EditText(this)
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Novo Nome!")
            setMessage("Digite o novo nome")
            setView(this@MainActivity.etNome)
            setPositiveButton("Salvar", OnClick())
            setNegativeButton("Cancelar", null)
        }
        builder.create().show()
    }

    inner class OnClick: OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            val nome = this@MainActivity.etNome.text.toString()
            (this@MainActivity.rvNomes.adapter as MyAdapter).add(nome)
        }
    }

    inner class OnItemClick: OnItemClickRecyclerView{
        override fun onItemClick(position: Int) {
            val nome = this@MainActivity.lista.get(position)
            this@MainActivity.tts.speak(nome, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    inner class OnSwipe: ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            (this@MainActivity.rvNomes.adapter as MyAdapter).mov(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            (this@MainActivity.rvNomes.adapter as MyAdapter).del(viewHolder.adapterPosition)
        }
    }
}