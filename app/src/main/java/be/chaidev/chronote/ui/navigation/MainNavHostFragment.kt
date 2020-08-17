package be.chaidev.chronote.ui.navigation

import android.content.Context
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import be.chaidev.chronote.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MainNavHostFragment : NavHostFragment() {


    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(Constants.TAG, "navhostfragment onAttach()")
        childFragmentManager.fragmentFactory = fragmentFactory
    }
}