package be.chaidev.chronote.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import be.chaidev.chronote.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays buttons whose interactions are recorded.
 */
@AndroidEntryPoint
class ButtonsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.create).setOnClickListener {
            println("btn1")
        }

        view.findViewById<Button>(R.id.viewAll).setOnClickListener {
            println("btn2")
        }

        view.findViewById<Button>(R.id.all_topics).setOnClickListener {
            println("navigate")
        }

        view.findViewById<Button>(R.id.to_chrono).setOnClickListener {
            println("navigate")
        }

        view.findViewById<Button>(R.id.delete_topics).setOnClickListener {
            println("remove")
        }

    }
}
