package com.example.p15firebase.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object InsertDestination: DestinasiNavigasi {
    override val route = "insert"
    override val titleRes = "Insert"
}

object UpdateDestination: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update"
}