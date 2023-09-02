package com.example.myandroidapplication.view

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
import com.example.myandroidapplication.viewModel.util.NetworkUtils


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
            description = "This is the concise description of the App." +
                    "\nThe full version can be found in the PDF document." +
                    "\n\nWhen creating your profile, you need to enter the correct credentials:" +
                    "\n\n--> The Tag [alphanumeric combination preceded by <<#>>] can be found " +
                    "in your profile, once you start the game 'Clash of Clans', " +
                    "under your nickname (The first image below shows where it is located)." +
                    "\n--> Your name." +
                    "\n--> A valid email." +
                    "\n--> A password." +
                    "\n\nAfter creating the profile and logging in, you will need to enter " +
                    "a key to use 'Clash of Clans' APIs." +
                    "\nTo do this, you need to know your IP address (check the first highlighted link)." +
                    "\nWith the IP address identified, you can access the site through " +
                    "the second blue highlighted link (The second image shows the site screen), " +
                    "providing the following credentials:" +
                    "\n\nEmail: xeros35867@jwsuns.com" +
                    "\n\nPassword: Pr0g_M0b1l3" +
                    "\n\nOnce you are on the site, you can generate a key by providing your IP address " +
                    "(as shown in the video guide)." +
                    "\nWhen finally the key is generated, you need to copy and paste it into the dedicated " +
                    "'Api Key' screen, accessible from the application's hamburger menu " +
                    "(The third image shows the app screen).",
            linkText1 = "My IP page",
            linkText2 = "Clash of Clans page.",
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
                playButton.text = "Play"
            } else {
                videoView.start()
                playButton.text = "Pause"
            }
            isPlaying = !isPlaying
        }

        // Imposta il focus sul primo elemento per farlo apparire all'apertura della pagina
        nestedScrollView.post {
            nestedScrollView.scrollTo(0, textViewTitle.top)
        }
    }

    private fun getVideoPath(resourceId: Int): String {
        return "android.resource://${packageName}/${resourceId}"
    }
}
