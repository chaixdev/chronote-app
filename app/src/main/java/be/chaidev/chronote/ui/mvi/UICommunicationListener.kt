package be.chaidev.chronote.ui.mvi

interface UICommunicationListener {

    fun hideCategoriesMenu()

    fun displayMainProgressBar(isLoading: Boolean)

    fun hideToolbar()

    fun showToolbar()

    fun hideStatusBar()

    fun showStatusBar()

    fun expandAppBar()

}