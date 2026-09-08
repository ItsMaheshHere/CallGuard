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


