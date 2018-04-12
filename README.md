# frenchie
Additional classes and extensions for [Moshi](https://github.com/square/moshi)

[![Build Status](https://travis-ci.org/Commit451/frenchie.svg?branch=master)](https://travis-ci.org/Commit451/frenchie) [![](https://jitpack.io/v/Commit451/frenchie.svg)](https://jitpack.io/#Commit451/frenchie)

## Gradle Dependency
Add the jitpack url to the project:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
then, in your app `build.gradle`
```groovy
dependencies {
    compile "com.github.Commit451:frenchie:latest.version.here"
}
```

## Usage
The purpose of frenchie is to extend [Moshi](https://github.com/square/moshi) with classes and utilities that are useful, but may not make sense being included in Moshi itself.

### ObjectOrArrayAdapterFactory
Sometimes, APIs are less than great, and they will include JSON that is not so predictable, and can either be a single element, or an array of elements. For example:
```json
{
  "cats": {
    "name": "Sputnik"
  }
}
```
and also:
```json
{
  "cats": [{
    "name": "Sputnik"
  }]
}
```
This is a challenge to deal with with JSON parsing libraries. To overcome this, simply annotate your model that can have this field of either a list or object like so:
```kotlin
class CatResponse {

    @field:ObjectOrArray
    lateinit var cats: List<Cat>
}
```
and when you are creating your `Moshi` instance:
```kotlin
Moshi.Builder()
    .add(ObjectOrArrayAdapterFactory())
    .build()
```

## Name
frenchie is named after the breed of the dog that [Moshi itself was named after](https://twitter.com/MoshiFrenchie), a French bulldog

License
--------

    Copyright 2018 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.