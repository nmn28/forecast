package com.example.forecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WbSunny
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDrawerState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.forecast.ui.ai.aiScreen
import com.example.forecast.ui.custompicker.CustomPicker
import com.example.forecast.ui.custompicker.PickerItem
import com.example.forecast.ui.explore.ExploreScreen
import com.example.forecast.ui.finances.FinanceViewModel
import com.example.forecast.ui.finances.FinancesScreen
import com.example.forecast.ui.finances.wallet.CreditCard
import com.example.forecast.ui.finances.wallet.SelectedCreditCardScreen
import com.example.forecast.ui.finances.wallet.WalletScreen
import com.example.forecast.ui.finances.wallet.sampleTransactions
import com.example.forecast.ui.notifications.NotificationsScreen
import com.example.forecast.ui.profile.ProfileScreen
import com.example.forecast.ui.sidemenu.SideMenuScreen
import com.example.forecast.ui.watchlists.WatchlistsScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

enum class Screens {
    AI,
    CREDIT_CARD,
    EXPLORE,
    FINANCES,
    NOTIFICATIONS,
    PROFILE,
    WALLET,
    WATCH_LISTS;
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
  //  val screens = listOf("watchlists", "explore", "finances", "ai", "notifications", "profile")
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val financeViewModel: FinanceViewModel = viewModel()

// Define screenTitles map
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
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
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
                    title = { Text(text =
                       navController.currentBackStackEntryAsState().value?.destination?.route?.let {
                           when (it) {
                               Screens.WATCH_LISTS.name -> "Watchlists"
                               Screens.EXPLORE.name -> "Explore"
                               Screens.FINANCES.name -> "Finances"
                               Screens.AI.name -> "ai"
                               Screens.NOTIFICATIONS.name -> "Notifications"
                               Screens.PROFILE.name -> "Profile"
                               else ->  ""
                           }
                       } ?: "")
                    },
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
                    startDestination = Screens.WATCH_LISTS.name,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(Screens.WATCH_LISTS.name) { WatchlistsScreen() }
                    composable(Screens.EXPLORE.name) { ExploreScreen() }
                    composable(Screens.FINANCES.name) { FinancesScreen(navController) }
                    composable(Screens.AI.name) { aiScreen() }
                    composable(Screens.NOTIFICATIONS.name) { NotificationsScreen() }
                    composable(Screens.PROFILE.name) { ProfileScreen() }
                    composable(Screens.WALLET.name) { WalletScreen(viewModel = financeViewModel, navController = navController) }
                    composable("${Screens.CREDIT_CARD.name}?creditCardJson={creditCardJson}",
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
        NavigationItem(Screens.WATCH_LISTS.name, Icons.AutoMirrored.Filled.ViewList),
        NavigationItem(Screens.EXPLORE.name, Icons.Default.Explore),
        NavigationItem(Screens.FINANCES.name, Icons.Default.AccountBalanceWallet),
        NavigationItem(Screens.AI.name, Icons.Default.Computer),
        NavigationItem(Screens.NOTIFICATIONS.name, Icons.Default.Notifications),
        NavigationItem(Screens.PROFILE.name, Icons.Default.Person)
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
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