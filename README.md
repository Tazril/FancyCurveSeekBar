# FancyCurveSeekBar

## Overview
Fancy Curve seekbar is an Android UI element that shows a seekbar with curve path and optional bubble text

<img src="seekbar-image.jpeg" height="200"/>

## Features
  - Robust and Easy to use Custom view
  - Modify attributes in xml such as progress, maximum progress, text(box) colors etc.

## Including in your project
Add below codes to your <b>root</b> build.gradle
```
allprojects {
   repositories {
      maven { url 'https://jitpack.io' }
   }
}
```
Add a dependency to your project's <b>module</b> build.gradle
```
dependencies {
      implementation 'com.github.tazril:FancyCurveSeekBar:1.0'
}
```
## Usage

Add FancyCurveSeekBar to your layout file
```
     <com.github.tazril.FancyCurveSeekBar
        android:id="@+id/curveSeekBar"
        app:maxValue="100"
        app:progress="10"
        app:progressBarColor="@android:color/black"
        app:textBoxColor="@android:color/blue"
        app:textColor="@android:color/white"
        app:threshold="60"
        app:thumbColor="@android:color/white"
        app:thumbStrokeColor="@android:color/black" />
```

In your Activity or Fragment , add listener to FancyCurveSeekBar.
```
      curveSeekBar.onProgressStart = { seekBar ->  

      }
      curveSeekBar.onProgressUpdate = { seekBar: SeekBar, percentage: Int, fromUser: Boolean ->

      }
      curveSeekBar.onProgressStop =  { seekBar ->

      }
```

## Developed by
* Tazril Ali - <tazrilparvez96@gmail.com>

## Licence
```
MIT License

Copyright (c) 2019 Kumar Manas

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
