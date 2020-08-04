package be.chaidev.chronote.ui.chronometer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import be.chaidev.chronote.R

class ChronoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_chrono, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val btnStart = view.findViewById(R.id.btnStart) as Button
        val btnStop = view.findViewById(R.id.btnStop) as Button
        val chrono = view.findViewById(R.id.chrono) as TextView
        chrono.text = "00:00:00"
        btnStart.setOnClickListener {
            chrono.text = "started"
        }
        btnStop.setOnClickListener {
            chrono.text = "stopped"
        }

    }

}