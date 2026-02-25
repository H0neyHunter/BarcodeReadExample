package com.tr.zzbajans.barcodereadexample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tr.zzbajans.barcodereadexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var b : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        b.apply {

        }

    }

    private val barcodeBuffer = StringBuilder()
    private val handler = Handler(Looper.getMainLooper())
    private val scanTimeout = 400L

    private val finishScanRunnable = Runnable {
        if (barcodeBuffer.isNotEmpty()) {
            val barcode = barcodeBuffer.toString()
            barcodeBuffer.clear()
            onBarcodeScanned(barcode)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {

            val char = event.unicodeChar.toChar()

            if (char.isLetterOrDigit()) {
                barcodeBuffer.append(char)

                handler.removeCallbacks(finishScanRunnable)
                handler.postDelayed(finishScanRunnable, scanTimeout)

                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

    private fun onBarcodeScanned(code: String) {
        Toast.makeText(this, "readBarcode : $code", Toast.LENGTH_SHORT).show()
    }
}