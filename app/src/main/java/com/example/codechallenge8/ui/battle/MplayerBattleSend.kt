package com.example.codechallenge8.ui.battle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.codechallenge8.R
import com.example.codechallenge8.model.Match
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_mplayer_battle_send.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MplayerBattleSend : AppCompatActivity() {

    private val personCollectionRef = Firebase.firestore.collection("Matchs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mplayer_battle_send)

        //TODO masukin pilihan ke match sesuai dengan idPlayernya

        iv_batu_send.setOnClickListener {

            val playerId = "Test"
            val player1Choice = "None"
            val player2Choice = "Batu"
            val matchName = "TestMatch"
            val winner = "None"

            val match = Match(playerId, player1Choice, player2Choice, matchName , winner)
            savePilihan(match)
        }

        iv_gunting_send.setOnClickListener {

            val playerId = "Test"
            val player1Choice = "None"
            val player2Choice = "Gunting"
            val matchName = "TestMatch"
            val winner = "None"

            val match = Match(playerId, player1Choice, player2Choice, matchName , winner)
            savePilihan(match)
        }

        iv_kertas_send.setOnClickListener {

            val playerId = "Test"
            val player1Choice = "None"
            val player2Choice = "Kertas"
            val matchName = "TestMatch"
            val winner = "None"

            val match = Match(playerId, player1Choice, player2Choice, matchName , winner)
            savePilihan(match)
        }


    }

    private fun savePilihan(match : Match) = CoroutineScope(Dispatchers.IO).launch {
        try {
            personCollectionRef.add(match).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MplayerBattleSend, "Successfully saved data.", Toast.LENGTH_LONG).show()
            }
        } catch(e: Exception){
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MplayerBattleSend, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}