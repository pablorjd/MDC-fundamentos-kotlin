package com.cursosant.mdc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
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
                .setAction(getString(/* resId = */ R.string.card_to_go)) {
                    Toast.makeText(this, getString(R.string.go_to), Toast.LENGTH_LONG)
                        .show()
                }
                .setAnchorView(binding.fab)
                .show()
        }

        binding.scrollingContent.cbEnablepass.setOnClickListener {
            binding.scrollingContent.tilPassword.isEnabled = !binding.scrollingContent.tilPassword.isEnabled
        }

       /* Glide.with(this)
            .load("https://www.adslzone.net/app/uploads-adslzone.net/2020/04/android.jpg")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.scrollingContent.imgCover) */
       loadImage()
        binding.scrollingContent.etUrl.onFocusChangeListener = View.OnFocusChangeListener { _, focused ->
            val url = binding.scrollingContent.etUrl.text.toString()
            var errorStr:String? = null

            if ( !focused ) {
                if ( url.isEmpty() ) {
                    errorStr = getString(R.string.card_required)
                } else if (URLUtil.isValidUrl(url)) {
                    loadImage(url)
                } else {

                    errorStr = getString(R.string.card_url_invalid_url)
                }
            }
            binding.scrollingContent.tilUrl.error = errorStr
        }

        binding.scrollingContent.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                binding.scrollingContent.root.setBackgroundColor (
                    when (checkedId) {
                        R.id.btnRed -> Color.RED
                        R.id.btnBlue -> Color.BLUE
                        else -> Color.GREEN
                    }
                )
            }
        }
       // loadImage()

        binding.scrollingContent.swFab.setOnCheckedChangeListener { button, isCheck ->
            if ( isCheck ) {
                button.text = getString(R.string.card_hide_fab)
                binding.fab.show()
            } else {
                button.text = getString(R.string.show_fab)
                binding.fab.hide()
            }
        }

        binding.scrollingContent.sldVol.addOnChangeListener { slider, value, fromUser ->
            binding.scrollingContent.tvSubTitle.text = "Vol: ${value}"
        }

        binding.scrollingContent.cpEmail.setOnCheckedChangeListener { chip, isCheck ->
            if ( isCheck ) {
                Toast.makeText(this , "${chip.text}", Toast.LENGTH_LONG).show()
            }
        }

        binding.scrollingContent.cpEmail.setOnCloseIconClickListener {
            binding.scrollingContent.cpEmail.visibility = View.GONE
        }

    }

    private fun loadImage(url:String = "https://www.adslzone.net/app/uploads-adslzone.net/2020/04/android.jpg") {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
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