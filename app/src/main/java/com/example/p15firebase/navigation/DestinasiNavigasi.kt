package com.example.p15firebase.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHome: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

object DestinasiInsert: DestinasiNavigasi {
    override val route = "insert"
    override val titleRes = "Insert"
}

object DestinasiDetail: DestinasiNavigasi {
    override val route: String = "detail"
    override val titleRes: String = "Detail Mahasiswa"
    const val nimArg = "nim"
    val routeWithArgs = "$route/{$nimArg}"
}