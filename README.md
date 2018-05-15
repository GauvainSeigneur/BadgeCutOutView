# BadgeCutOutView
A badge view which include a punched (transparent) textView

## State
Not stable yet.

## Todo

## Set up
### 1.Gradle 
```gradle
implementation 'gauvain.seigneur:badge-cut-out-view:0.5'
```
### 2.Maven
```maven
<dependency>
  <groupId>gauvain.seigneur</groupId>
  <artifactId>badge-cut-out-view</artifactId>
  <version>0.5</version>
  <type>pom</type>
</dependency>
```
## Usage
### 1.xml layout 

### 1.Options 
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
