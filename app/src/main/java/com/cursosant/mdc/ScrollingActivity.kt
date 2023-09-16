package com.cursosant.mdc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cursosant.mdc.databinding.ActivityScrollingBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener{
            if ( binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER ) {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            } else {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }

        binding.bottomAppBar.setNavigationOnClickListener{
            Snackbar.make(binding.root, getString(R.string.evento_exitoso),Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .show()
        }
        binding.scrollingContent.btnSkip.setOnClickListener {
            binding.scrollingContent.cvContent.visibility = View.GONE
        }
        binding.scrollingContent.btnComprar.setOnClickListener {
            Snackbar.make(it, getString(R.string.comprar), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.card_to_go), {
                    Toast.makeText(this,getString(R.string.go_to),Toast.LENGTH_LONG)
                         .show()
                })
                .setAnchorView(binding.fab)
                .show()
        }

        binding.scrollingContent.cbEnablepass.setOnClickListener {
            binding.scrollingContent.tilPassword.isEnabled = !binding.scrollingContent.tilPassword.isEnabled
        }

        binding.scrollingContent.etUrl.onFocusChangeListener = View.OnFocusChangeListener { view, focused ->
            val url = binding.scrollingContent.etUrl.text.toString()
            if ( !focused ) {
                loadImage(url)
                // Toast.makeText(this,"Pierde el foco", Toast.LENGTH_LONG).show()
            }
        }

        Glide.with(this)
            .load("https://www.adslzone.net/app/uploads-adslzone.net/2020/04/android.jpg")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.scrollingContent.imgCover)
    }

    private fun loadImage(url:String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.scrollingContent.imgCover)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}