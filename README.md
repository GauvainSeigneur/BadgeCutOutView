# BadgeCutOutView
A badge view which include a punched (transparent) textView

## State
Not stable yet.

## Todo
* Shadow elevation
* Refactoring text positioning

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
        android:paddingLeft="8dp"
        android:paddingTop="0dp"
        android:paddingRight="8dp"
        android:paddingBottom="0dp"
        app:cornerRadius="8dp"
        app:textSize="18sp"
        app:centerBadgeText="false"
        app:badgeStroke="4dp"
        app:strokeColor="#80F2F2F2"
        app:backgroundColor="#4DF2F2F2"
        app:includeBadgePadding="true"
        android:text="LABEL"/>
```
### 2.Options 
| Attribute Name| Default value | Description  |
| ------------- | ------------- | -------------|
| backgroundColor|#F2F2F2|Background color of the view|
| cornerRadius|0dp|Set corner radius of the view|
| badgeStroke|0dp|Set badge stroke dimension|
| strokeColor|backgroundColor|Define a color for stroke |
| includeBadgePadding|true|Default padding set around textView(8dp)|
| centerBadgeText|false|Center text inside the view|
| text|null|Set text inside badge|
| textSize|12sp|Define textSize (sp)|
| fontFamily|null|Define a font for the text with default Android Studio method|

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