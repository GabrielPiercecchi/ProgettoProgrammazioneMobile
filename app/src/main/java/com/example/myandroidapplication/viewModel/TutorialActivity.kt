package com.example.myandroidapplication.viewModel

import TutorialContent
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



class TutorialActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var playButton: Button

    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val tutorial = TutorialContent(
            title = "Tutorial App",
            description = "Questa è la descrizione sintetica dell'App." +
                    "\nLa versione completa può essere trovata nella tesina in formato .pdf." +
                    "\nDurante la creazione del proprio profilo bisogna inserire le corrette credenziali:" +
                    "\n--> Il Tag [combinazione alfanumerica preceduta da <<#>>] può essere trovato " +
                    "all'interno del prorpio profilo una volta avviato il gioco " +
                    "<<Clash of Clans>> sotto il proprio nickname " +
                    "(La prima immagine sottostante mostra dove si trova)." +
                    "\n--> Il proprio nome" +
                    "\n--> Un'email valida" +
                    "\n--> Una Password" +
                    "\nA seguito della creazione del profilo b ed eseguito l'accesso bisogna inserire" +
                    "una chiave necessaria ad usare le API di <<Clash of Clans>>." +
                    "\nPer far questo bisgna conoscere il proprio indirizzo IP " +
                    "(consultabile al primo link evidenziato)." +
                    "\nUna volta scoperto il proprio IP bisogna accedere al sito tramite " +
                    "il secondo link evidenziato in blu " +
                    "(La seconda immagina mostra la schermata del sito) " +
                    "fornendo le seguenti credenziali:" +
                    "\nEmail: ciao@gmail.com" +
                    "\nPassword: voleviEhh" +
                    "\nEntrati nel sito bisogna generare una chiave fornendo il proprio indirizzo IP (come mostrato nel video guida)." +
                    "\nGenerata la chiave bisogna copia/incollarla all'interno della schermata " +
                    "apposita, chiamata <<Api Key>>, e presente nell'hamburger menu " +
                    "all'interno dell'applicazione " +
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
