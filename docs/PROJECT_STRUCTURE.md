## Project Directory Structure

1. **`app/`**   The main module directory containing code and resources of application.

2. **`manifests/`**   It Contains **`AndroidManifest.xml`** file.
   `AndroidManifest.xml`  This file declare essential app information to the andorid os. It include requred permissions , app components.

3. **`kotlin+java/`** : Contains All Kotlin and Java source code .
   `main/` : App source code.
   `androidTest/` Contains instruments tests.
   `test/` : local unit test on JVM for faster execution without needing a device .

4. **`res/`** : The resources directory for non-code assests .
   `drawable/` : stores files (PNG/JPEG) , vector graphics (SVG/XML) and custom shapes
   `values/` : Conatains XML files that define simple data elements 
                         (Color.xml, String.xml, themes.xml)

5. **`keepRules/`** : Contains configuration files.
                           This files tell the build system which specific parts of code must not be shrunk , obfuscated or deleted when compiling release version . 

6. **`Gradle Scripts/`** : These scripts define app's dependencies .
                                       How to compile project into final apk .


