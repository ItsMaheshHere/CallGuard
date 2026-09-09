## Project Directory Structure

### Android Studio file structure view

1. **`app/`**   The main module directory containing code and resources of application.

2. **`manifests/`**   It Contains **`AndroidManifest.xml`** file.
   `AndroidManifest.xml`  This file declare essential app information to the andorid os. It include required permissions , app components.

3. **`kotlin+java/`** : Contains All Kotlin and Java source code .
   `main/` : App source code.
   `androidTest/` Contains instruments tests.
   `test/` : local unit test on JVM for faster execution without needing a device .

4. **`res/`** : The resources directory for non-code assests .
   `drawable/` : stores files (PNG/JPEG) , vector graphics (SVG/XML) and custom shapes
   `values/` : Conatains XML files that define simple data elements (Color.xml, String.xml, themes.xml)

5. **`keepRules/`** : Contains configuration files. This tell the build system which specific parts of code must not be shrunk , obfuscated or deleted when compiling release version . 

6. **`Gradle Scripts/`** : These scripts define app's dependencies . How to compile project into final apk .

### Project Folder View

1. **`app/`** : It contains actual application.

2. **`build/`** : Gradle build result.

3. **`.gradle/`** : Gradle's working/cache data

4. **`.idea/`** : Android Studio Project configuration.

5. **`.kotlin/`** : Kotlin tooling's generated/local data.

6. **`gradle/`** : Gradle Wrapper configuration.
   files that help the project use the correct Gradle tooling.

7. **`setting.gradle.kts`** : It helps gradle understand structure of project.

8. **`gradle.properties`** : configuration property used by gradle .

9. **`gradlew`** : Gradle wrapper script for unix like system.

10. **`gradlew.bat`** : For windows .

11. **`local.properties`** : Local machine specific configuration . Like : SDK path 
