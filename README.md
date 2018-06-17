# ColourPicker
A simple Android colour picker selection view, based around the GridView.  Colours are generated in the GridView via a colour array reference and autofit based on ```app:cp_coloursPerRow```.

<img src="https://github.com/onemandan/ColourPicker/blob/master/ColourPicker.png" height="430px"/>

## Installation
To get MaterialTextView into your project, add the repository to your build.gradle.

#### Gradle
1. Add the JitPack repository to your projects build.gradle:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

2. Add the dependency to your modules build.gradle :
```gradle
dependencies {
  implementation 'com.github.onemandan:ColourPicker:0.0.1'
}
```

#### Maven
1. Add the JitPack repository to your build file:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

2. Add the dependency:
```xml
<dependency>
  <groupId>com.github.onemandan</groupId>
  <artifactId>ColourPicker</artifactId>
  <version>0.0.1</version>
</dependency>
```

## Usage

``` xml
<uk.co.onemandan.colourpicker.ColourPicker
  android:id="@+id/cp_colours"
  android:layout_width="0dp"
  android:layout_height="0dp"
  android:layout_marginBottom="16dp"
  app:layout_constraintTop_toTopOf="parent"
  app:layout_constraintBottom_toTopOf="@id/cv_selected"
  app:layout_constraintStart_toStartOf="parent"
  app:layout_constraintEnd_toEndOf="parent"
  app:cp_colours="@array/themeColours"
  app:cp_maxSelected="1"
  app:cp_coloursPerRow="5"/>
```

``` java
...
ColourPicker _colourPicker;
...
_colourPicker = findViewById(R.id.cp_colours);
_colourPicker.setColourSelectedListener(new ColourClickedListener() {
  @Override
  public void OnColourClicked(int colour) {
    ...
  }
});
int colour = _colourPicker.getSelectedColour();
List<Integer> colours = _colourPicker.getSelectedColours();
```

## License
```
MIT License

Copyright (c) 2018 Daniel Hart

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
