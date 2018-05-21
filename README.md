# BadgeCutOutView
A badge view which include a punched (transparent) textView

## State
Not stable yet.

## Todo
* Define shadow bounds according to Material Design 
* Allow user to manage more deeper alpha of the elevation

## Set up
### 1.Gradle 
```
dependencies {
    implementation 'gauvain.seigneur:badge-cut-out-view:0.6'
}
```
### 2.Maven
```
<dependency>
  <groupId>gauvain.seigneur</groupId>
  <artifactId>badge-cut-out-view</artifactId>
  <version>0.6</version>
  <type>pom</type>
</dependency>
```
## Usage
### 1.xml layout 
```xml
<gauvain.seigneur.badgecutout.BadgeCutOutView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    app:cornerRadius="16dp"
    app:textSize="12sp"
    app:centerBadgeText="true"
    app:badgeStroke="2dp"
    app:strokeColor="@color/colorPrimary"
    app:backgroundColor="@color/colorAccent"
    app:includeBadgePadding="true"
    android:padding="4dp"
    android:elevation="16dp"
    app:elevationAlpha="0.75"
    app:shadowColor="@color/colorPrimary"
    app:shadowScale="2"
    android:text="LABEL"/>
```
### 2.Options 
| Attribute Name| Default value | Description  |
| ------------- | ------------- | -------------|
| app:backgroundColor|#F2F2F2|Background color of the view|
| app:cornerRadius|0dp|Set corner radius of the view|
| app:badgeStroke|0dp|Set badge stroke dimension|
| app:strokeColor|backgroundColor|Define a color for stroke |
| app:includeBadgePadding|true|Default padding set around textView(8dp)|
| app:centerBadgeText|true|Center text inside the view|
| android:text|null|Set text inside badge|
| app:textSize|12sp|Define textSize (sp)|
| android:fontFamily|null|Define a font for the text with default Android Studio method|
| android:elevation|0dp|Define elevation of the view and shadow in accordance|
| app:shadowAlpha|1f|Define alpha of the shadow|
| app:shadowColor|#FF000000|Define color of the shadow|
| app:shadowScale|1f|Define the size of shadow (increase value will increase the size of the shadow)|

## App that use this library
If you're using this library in your app If you're using this library in your app please let me know via email, pull requests or issues.

## Licence
```
Copyright 2018 Gauvain Seigneur

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```