package be.chaidev.chronote.ui.mvi

interface DataStateChangeListener{

    fun onDataStateChange(dataState: DataState<*>?)

    fun expandAppBar()

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean
}