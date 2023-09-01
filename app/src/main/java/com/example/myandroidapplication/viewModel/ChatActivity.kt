package com.example.myandroidapplication.viewModel

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidapplication.model.Message
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    private lateinit var name: String
    private lateinit var receiverUid: String

    //Variabili per la comunicazione tra due room
    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        name = intent.getStringExtra("name")?: ""
        receiverUid = intent.getStringExtra("uid")?: ""

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        messageBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Calcola l'altezza desiderata in base al contenuto del testo
                val layoutParams = messageBox.layoutParams
                val lineCount = messageBox.lineCount
                val lineHeight = messageBox.lineHeight
                val extraHeight = 16.dpToPx() // Aggiungi 16 dp all'altezza
                val minHeight = 50.dpToPx() // Altezza minima di 50dp
                val desiredHeight = (lineCount * lineHeight) + extraHeight

                // Imposta l'altezza desiderata, ma assicurati che non sia inferiore all'altezza minima
                layoutParams.height = maxOf(desiredHeight, minHeight)
                messageBox.layoutParams = layoutParams
            }
        })

        // Logica per aggiungere dati su recycleView
        mDbRef.child("chats").child(senderRoom!!)
            .child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children){

                        val message = postSnapshot
                            .getValue(Message::class.java)
                        messageList.add(message!!)

                        // Focus sull'ultimo messaggio
                        chatRecyclerView.scrollToPosition(messageList.size -1)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            } )

        // Per aggiungere il messaggio al database
        sendButton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            mDbRef.child("chats").child(senderRoom!!)
                .child("messages").push()
                .setValue(messageObject).addOnSuccessListener {

                    mDbRef.child("chats").child(receiverRoom!!)
                        .child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
    }

    // Selezione per il menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // "when" case per selezionare le differenti opzioni del menu
        when(item.itemId){
            R.id.stats_player -> {
                mDbRef.child("user").child(receiverUid).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val tag = snapshot.child("tag").getValue(String::class.java) ?: ""
                        val intent = Intent(this, StatsReceiver::class.java)
                        intent.putExtra("tag", tag)
                        intent.putExtra("name", name)
                        startActivity(intent)
                    }
                }
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }
        }
        return true
    }

    //metodo per "iniettare" il menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_stats_receiver, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Estensione per convertire dp in px
    fun Int.dpToPx(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}