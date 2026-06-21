package com.holiware.world1.view

sealed class Screens(val route: String) {
    object MainScreen : Screens("Main")
    object DetailScreen : Screens( "Detail")
}