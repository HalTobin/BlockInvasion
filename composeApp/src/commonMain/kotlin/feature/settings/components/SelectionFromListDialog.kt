package feature.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.close_dialog_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.BlockInvasionTheme

@Composable
fun SelectionFromListDialog(
    title: String,
    items: List<Triple<String, String, (@Composable () -> Unit)?>>, // Triple<key, title, icon>
    currentKey: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) = Dialog(onDismissRequest = onDismiss) {
    Surface(shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Column {
            Box(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                Text(text = title,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineSmall)
                IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(Res.string.close_dialog_description))
                }
            }
            LazyColumn(modifier = Modifier.padding(top = 8.dp).fillMaxWidth()) {
                itemsIndexed(items) { index, item ->
                    Column {
                        Row(modifier = Modifier.height(48.dp)
                            .clickable { onSelect(item.first) },
                            verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(16.dp))
                            item.third?.let { content ->
                                content()
                                Spacer(Modifier.width(16.dp))
                            }
                            Text(text = item.second,
                                modifier = Modifier.weight(1f),
                                fontWeight = if (currentKey == item.first) FontWeight.SemiBold else FontWeight.Normal)
                            RadioButton(modifier = Modifier.padding(horizontal = 20.dp),
                                selected = (currentKey == item.first),
                                onClick = null)
                        }
                        if (items.lastIndex != index) HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SelectionFromListDialogPreview() = BlockInvasionTheme {
    SelectionFromListDialog(title = "Select an item",
        items = List(3) { n -> Triple("key_$n", "Item n°$n", null) },
        currentKey = "key_1",
        onSelect = {},
        onDismiss = {})
}