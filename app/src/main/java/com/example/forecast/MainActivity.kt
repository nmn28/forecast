package com.example.forecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.example.forecast.ui.explore.ExploreScreen
import com.example.forecast.ui.finances.FinancesScreen
import com.example.forecast.ui.notifications.NotificationsScreen
import com.example.forecast.ui.profile.ProfileScreen
import com.example.forecast.ui.watchlists.WatchlistsScreen
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.forecast.ui.ai.aiScreen
import com.example.forecast.ui.custompicker.CustomPicker
import com.example.forecast.ui.custompicker.PickerItem
import com.example.forecast.ui.finances.FinanceViewModel
import com.example.forecast.ui.finances.wallet.CreditCard
import com.example.forecast.ui.finances.wallet.SelectedCreditCardScreen
import com.example.forecast.ui.finances.wallet.WalletScreen
import com.example.forecast.ui.finances.wallet.sampleTransactions
import com.example.forecast.ui.sidemenu.SideMenuScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val screens = listOf("watchlists", "explore", "finances", "ai", "notifications", "profile")
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val financeViewModel: FinanceViewModel = viewModel()

// Define screenTitles map
    val screenTitles = mapOf(
        "watchlists" to "Watchlists",
        "explore" to "Explore",
        "finances" to "Finances",
        "ai" to "ai",
        "notifications" to "Notifications",
        "profile" to "Profile"
    )
    // Bottom sheet state with skipHalfExpanded
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp) // Set the height of the bottom sheet
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        // Left icon
                        Icon(Icons.Default.RestoreFromTrash, contentDescription = "Trash")

                        Spacer(modifier = Modifier.weight(1f)) // This spacer will take up all available space

                        // Center icon
                        Icon(Icons.Default.WbSunny, contentDescription = "Sun")

                        Spacer(modifier = Modifier.weight(1f)) // This spacer will also take up all available space

                        // Right icon
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                    Divider()
                    val items = listOf(
                        PickerItem("Predictions", Icons.Default.Pets),
                        PickerItem("Events", Icons.Default.CalendarMonth),
                        PickerItem("Markets", Icons.Default.Storefront),
                        PickerItem("Transactions", Icons.Default.CurrencyExchange),
                        PickerItem("Transactions", Icons.Default.FoodBank)
                    )
                    val dropdownOptions = listOf("For You", "Following") // Define the dropdown options

                    CustomPicker(
                        items = items,
                        dropdownOptions = dropdownOptions,
                        showLabel = false,
                        showIcon = true,

                    )
                    Text("Bottom Sheet Title", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Here is some content inside the bottom sheet.")
                    // Add more components as needed
                    Button(onClick = { /* Handle button click */ }) {
                        Text("Button inside Bottom Sheet")
                    }
                    // You can add List, TextField, etc.
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = screenTitles[navController.currentBackStackEntryAsState().value?.destination?.route] ?: "") },
                    actions = {
                        IconButton(onClick = { /* Handle search click */ }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = { BottomNavigationBar(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        sheetState.show()
                    }
                }) {
                    Icon(Icons.Filled.WbSunny, contentDescription = "Open Bottom Sheet")
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = false,
            drawerContent = {
                // Here, SideMenuScreen's ExpandableNavigationRail is being used as the content of the drawer.
                SideMenuScreen().ExpandableNavigationRail()
            },
            content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = screens.first(),
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(screens[0]) { WatchlistsScreen() }
                    composable(screens[1]) { ExploreScreen() }
                    composable(screens[2]) { FinancesScreen(navController) }
                    composable(screens[3]) { aiScreen() }
                    composable(screens[4]) { NotificationsScreen() }
                    composable(screens[5]) { ProfileScreen() }
                    composable("wallet") {
                        WalletScreen(viewModel = financeViewModel, navController = navController)
                    }
                    composable(
                        "${Destinations.SelectedCreditCardScreen}/{creditCardJson}",
                        arguments = listOf(navArgument("creditCardJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val creditCardJson =
                            backStackEntry.arguments?.getString("creditCardJson") ?: ""
                        // Convert JSON string back to CreditCard object
                        val creditCard = Gson().fromJson(creditCardJson, CreditCard::class.java)
                        SelectedCreditCardScreen(creditCard, sampleTransactions) {
                            // Define onDismiss logic here
                        }
                    }


                }
            }
        )
    }
}

object Destinations {
    const val WalletScreen = "wallet"
    const val SelectedCreditCardScreen = "selectedCreditCard"
}

@Composable
fun DrawerContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Drawer Item 1", modifier = Modifier.padding(16.dp))
        Text("Drawer Item 2", modifier = Modifier.padding(16.dp))
        // Add more drawer items here
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem("watchlists", Icons.Default.ViewList),
        NavigationItem("explore", Icons.Default.Explore),
        NavigationItem("finances", Icons.Default.AccountBalanceWallet),
        NavigationItem("ai", Icons.Default.Computer),
        NavigationItem("notifications", Icons.Default.Notifications),
        NavigationItem("profile", Icons.Default.Person)
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // This makes sure the back button takes you to the initial screen
                        popUpTo(navController.graph.startDestinationId)
                        // Avoid multiple copies of the same screen
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

data class NavigationItem(val route: String, val icon: ImageVector, val title: String = "")

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}