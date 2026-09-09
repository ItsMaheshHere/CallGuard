**@Composible** 
It allows to create UI in android screen.

`@Composible` marks a Kotin function as a function that can build part of android UI.

```kotlin
import androidx.compose.runtime.Composable


@Composable
fun CallGuardHomeScreen() {
    Text("CallGuard")
}
```

**Column**

A column is a compose layout that arranges child composables vertically.

```kotlin
import androidx.compose.foundation.layout.Column


Column {
    Text("CallGuard")
    Text("Protection: OFF")
}
```

**Row**

`Row` is a Jetpack Compose layout that arranges its child composables horizontally(left to right)

```kotlin
import androidx.compose.foundation.layout.Row


Row {
    Text("Protection: ON")
    switch(...)
}
```

**Text**

`Text` is a Jetpack Compose UI component used to display text on the screen.

```kotlin
import androidx.compose.material3.Text


Text {
    text = "CallGuard"
}
```

**Spacer**

`Spacer` is a Jetpack Compose layout component used to create empty space between UI elements.

```kotlin
import androidx.compose.foundation.layout.Spacer


Column {
    Text("CallGuard")
    Spacer(modifier = Modifier.height(24.dp))  //Modifier.width(24.dp) for horizontal space
    Text("Protection:OFF")
}
```



**Modifier**

It is a compose tool used to change or configure the appearance, size, position, spacing, behavior and interaction of UI elements.

```kotlin
Text(
    text = "CallGuard"
    modifier = Modifier.padding(24.dp)
)
//here Modifier.padding() add padding around the Text
```



**dp**

dp stands for Density-independent pixels.

It is a unit used in Android to specify UI dimensions and spacing.

```kotlin
import androidx.compose.ui.unit.dp
```



**MaterialTheme.typography**

It provides predefined text styles from Material 3 that control things such as text size, weight and other typography properties.

```kotlin
import androidx.compose.material3.MaterialTheme

Text (
    text = "CallGuard"
    style = MaterialTheme.typography.headlineLarge   
)
```



**Switch**

`Switch` is a Material 3 UI component that allows the user to turn a setting ON or OFF.

```kotlin
import androidx.compose.material3.Switch

Switch(
    checked = isProtectionEnabled,
    onCheckedChange = { enabled ->
    isProtectionEnabled = enabled
    }
)
```



**remember**

`remember` is a Compose function used to remember a value across recompositions of a composable.

Using it UI can be recomposed when state changes.

```kotlin
import androidx.compose.runtime.remember

var isProtectionEnabled by remember {
    mutableStateOf(false)
}
```



**mutableStateOf**

It created a state value that Compose can observe.

```kotlin
import androidx.compose.runtime.mutableStateOf
```


