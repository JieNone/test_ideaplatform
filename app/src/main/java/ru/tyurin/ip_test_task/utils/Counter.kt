package ru.tyurin.ip_test_task.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity

@Composable
fun Counter(
    gadget: GadgetEntity,
    onIncrement: (GadgetEntity) -> Unit,
    onDecrement: (GadgetEntity) -> Unit
) {
    var amount by remember { mutableIntStateOf(gadget.amount) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = gadget.name, fontSize = 20.sp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Box {
                Button(onClick = {
                    if (amount > 0) {
                        amount -= 1
                        onDecrement(gadget.copy(amount = amount))
                    }
                }) {
                    Text("-")
                }
            }

            Text(text = amount.toString(), fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))

            Button(onClick = {
                amount += 1
                onIncrement(gadget.copy(amount = amount))
            }) {
                Text("+")
            }
        }
    }
}
