package com.firooze.digital.navigation

import androidx.navigation.NavController

class NavActions(private val navController: NavController) {

    fun navigateToNewsDetail(id: String) {
        navController.navigate("${NavigationItem.Detail.route}?$ID=$id")
    }

    fun navigateUpFromDetailToHome() {
        navController.navigateUp()
    }

}