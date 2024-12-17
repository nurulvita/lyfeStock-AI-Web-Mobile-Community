package com.example.applyfestox.uii.navigation

import com.example.applyfestox.R

sealed class   NavigationBar(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {

    // for home
    object Beranda: NavigationBar(
        route = "beranda",
        title = "Beranda",
        icon = R.drawable.ic_bottom_home,
        icon_focused = R.drawable.ic_bottom_home_focused
    )

    // for report
    object Manajemen: NavigationBar(
        route = "manajemen",
        title = "Manajemen",
        icon = R.drawable.ic_bottom_report,
        icon_focused = R.drawable.ic_bottom_report_focused
    )

    // for report
    object Deteksi: NavigationBar(
        route = "deteksi/{userId}",
        title = "Deteksi",
        icon = R.drawable.ic_bottom_detect,
        icon_focused = R.drawable.ic_bottom_detect_focused
    )

    // for report
    object Profil: NavigationBar(
        route = "profil",
        title = "Profil",
        icon = R.drawable.ic_bottom_profile,
        icon_focused = R.drawable.ic_bottom_profile_focused
    )

}
