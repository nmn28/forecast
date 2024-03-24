package com.example.forecast.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.forecast.ui.custompicker.CustomPicker
import com.example.forecast.ui.custompicker.PickerItem

@Composable
fun ProfileScreen() {
    Column {


        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(60.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Jane Doe", fontWeight = FontWeight.Bold)
                    Text("jane.doe99")
                    Text("@forecast.ai")
                }

                Column {
                    Row {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        Icon(Icons.Default.Message, contentDescription = "Message")
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                        Icon(Icons.Default.PersonAdd, contentDescription = "Add Person")
                    }

                    Row {
                        Column {
                            Icon(Icons.Default.Science, contentDescription = "Flask")
                            Text("45%", fontSize = 14.sp)
                        }
                        Column {
                            Icon(Icons.Default.HourglassBottom, contentDescription = "Eyeglasses")
                            Text("36%", fontSize = 14.sp)
                        }
                        Column {
                            Icon(Icons.Default.People, contentDescription = "People")
                            Text("1,085", fontSize = 14.sp)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(Icons.Default.Place, contentDescription = "Location")
                Icon(Icons.Default.Link, contentDescription = "Link")
                Icon(Icons.Default.Cake, contentDescription = "Balloon")
            }
        }
        val items = listOf(
            PickerItem("All", Icons.Default.Language),
            PickerItem("Convictions", Icons.Default.Pets),
            PickerItem("Events", Icons.Default.CalendarMonth),
            PickerItem("Markets", Icons.Default.Storefront),
            PickerItem("Disputes", Icons.Default.Gavel),
            PickerItem("Transactions", Icons.Default.CurrencyExchange),
            PickerItem("Transactions", Icons.Default.Money)
        )
        val dropdownOptions = listOf("For You", "Following") // Define the dropdown options

        CustomPicker(
            items = items,
            dropdownOptions = dropdownOptions,
            showLabel = false,
            showIcon = true,

        )
    }

}
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}