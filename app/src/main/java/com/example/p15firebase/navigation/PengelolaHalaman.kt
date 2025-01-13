package com.example.p15firebase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.p15firebase.ui.view.HomeScreen
import com.example.p15firebase.ui.view.InsertMhsView
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.p15firebase.ui.view.DetailMhsView

@Composable
fun PengelolaHalaman (
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost (
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(route = DestinasiHome.route) {
            HomeScreen (
                navigateToltemEntry = { navController.navigate(DestinasiInsert.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }
        
        composable(route = DestinasiInsert.route) {
            InsertMhsView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.navigate(DestinasiHome.route) }
            )
        }
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.nimArg) {
                    type = NavType.StringType
                }
            )
        ) {
            val nim = it.arguments?.getString(DestinasiDetail.nimArg)
            nim?.let { nim ->
                DetailMhsView(
                    onBack = {
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}