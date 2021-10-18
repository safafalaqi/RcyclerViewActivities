package com.example.rcyclerviewactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rcyclerviewactivities.Constants.Base_Url
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class RecyclerViewActivity : AppCompatActivity() {

    lateinit var myRv: RecyclerView
    lateinit var rvAdapter: RVAdapter
    lateinit var feeds: ArrayList<Feeds>
    lateinit var progressBar:ProgressBar
    val parser = XMLParser()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view) //supportActionBar?.hide()
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mainActivity-> startActivity(Intent(this,MainActivity::class.java))
                R.id.recyclerview-> startActivity(Intent(this,RecyclerViewActivity::class.java))
                R.id.profile-> startActivity(Intent(this,Profile::class.java))
            }
            true
        }

            myRv = findViewById(R.id.rvItems)
                progressBar=findViewById(R.id.progressBar)
                getFeeds(Base_Url)

    }

    private fun getFeeds(url: String) {
        CoroutineScope(Dispatchers.IO).launch {

            feeds = async {
                fetch(url)
            }.await()

            withContext(Dispatchers.Main) {
                if(progressBar.getVisibility() == View.VISIBLE)
                {
                    progressBar.setVisibility(View.GONE);
                }
                setRV()
            }

        }
    }

    fun setRV() {
        rvAdapter = RVAdapter(feeds, this)
        myRv.adapter = rvAdapter
        myRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    fun fetch(url: String): ArrayList<Feeds> {
        val url =
            URL(url)
        val urlConnection = url.openConnection() as HttpURLConnection
        feeds =

            urlConnection.getInputStream()?.let {
                parser.parse(it)
            }
                    as ArrayList<Feeds>
        return feeds
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mainActivity -> {
                startActivity(
                    Intent(this,MainActivity::class.java))

                return true
            }
            R.id.recyclerview -> {
                startActivity(Intent(this,RecyclerViewActivity::class.java))
                return true
                }

            }

        return super.onOptionsItemSelected(item)
    }

}
