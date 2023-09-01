package com.example.myandroidapplication.viewModel

import TutorialContent
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.myandroidapplication.R
import com.example.myandroidapplication.util.NetworkUtils


class TutorialActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var playButton: Button

    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(this)
        }

        val tutorial = TutorialContent(
            title = "Tutorial App",
            description = "Questa è la sintetica descrizione dell'App." +
                    "\nLa versione completa può essere trovata nel documento in formato .pdf." +
                    "\nDurante la creazione del proprio profilo, è necessario inserire le corrette credenziali:" +
                    "\n--> Il Tag [combinazione alfanumerica preceduta da <<#>>] può essere trovato " +
                    "all'interno del proprio profilo, una volta avviato il gioco 'Clash of Clans', " +
                    "sotto il proprio nickname (La prima immagine sottostante mostra dove si trova)." +
                    "\n--> Il proprio nome." +
                    "\n--> Un'email valida." +
                    "\n--> Una password." +
                    "\nDopo aver creato il profilo ed effettuato l'accesso, sarà necessario inserire " +
                    "una chiave per utilizzare le API di 'Clash of Clans'." +
                    "\nPer fare ciò, è necessario conoscere il proprio indirizzo IP (consultabile al primo link evidenziato)." +
                    "\nUna volta individuato l'indirizzo IP, sarà possibile accedere al sito tramite " +
                    "il secondo link evidenziato in blu (La seconda immagine mostra la schermata del sito), " +
                    "fornendo le seguenti credenziali:" +
                    "\nEmail: ciao@gmail.com" +
                    "\nPassword: voleviEhh" +
                    "\nUna volta entrati nel sito, sarà possibile generare una chiave fornendo il proprio indirizzo IP " +
                    "(come illustrato nel video guida)." +
                    "\nUna volta generata la chiave, sarà necessario copiarla e incollarla nell'apposita schermata chiamata " +
                    "'Api Key', raggiungibile dall'hamburger menu all'interno dell'applicazione " +
                    "(La terza immagine mostra la schermata dell'app).",
            linkText1 = "Link per l'indirizzo IP",
            linkText2 = "Link alla pagina di Clash of Clans",
            linkUrl1 = "https://www.ilmioindirizzoip.it/",
            linkUrl2 = "https://developer.clashofclans.com/#/login",
            imageResId = R.drawable.clashapilogin,
            imageTagLocation = R.drawable.taglocation,
            imageViewApp = R.drawable.imageviewapp
        )

        val textViewLink = findViewById<TextView>(R.id.textViewLink)
        val textViewLink2 = findViewById<TextView>(R.id.textViewLink2)
        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val imageViewTutorial = findViewById<ImageView>(R.id.imageViewSite)
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)


        // Visualizza il link con il testo blu e l'icona
        textViewLink.text = tutorial.linkText1
        textViewLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tutorial.linkUrl1))
            startActivity(intent)
        }
        textViewLink2.text = tutorial.linkText2
        textViewLink2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tutorial.linkUrl2))
            startActivity(intent)
        }

        // Imposta il titolo e la descrizione
        textViewTitle.text = tutorial.title
        textViewDescription.text = tutorial.description

        // Carica l'immagine
        imageViewTutorial.setImageResource(tutorial.imageResId)

        // Inizializza le variabili per i componenti
        videoView = findViewById(R.id.videoView)
        playButton = findViewById(R.id.playButton)

        // Imposta il percorso del video
        val videoPath = getVideoPath(R.raw.videotutorialapiclash)
        videoView.setVideoURI(Uri.parse(videoPath))

        // Imposta il percorso del video per il VideoView
        videoView.setVideoURI(Uri.parse(videoPath))

        // Gestisci il click sul pulsante di avvio/pausa
        playButton.setOnClickListener {
            if (isPlaying) {
                videoView.pause()
                playButton.text = "Avvia il Video"
            } else {
                videoView.start()
                playButton.text = "Metti in Pausa"
            }
            isPlaying = !isPlaying
        }

//        playButton.setOnClickListener {
//            videoView.start() // Avvia la riproduzione del video
//        }

        // Imposta il focus sul primo elemento per farlo apparire all'apertura della pagina
        nestedScrollView.post {
            nestedScrollView.scrollTo(0, textViewTitle.top)
        }
    }

    private fun getVideoPath(resourceId: Int): String {
        return "android.resource://${packageName}/${resourceId}"
    }
}
