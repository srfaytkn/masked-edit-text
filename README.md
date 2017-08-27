[![](https://jitpack.io/v/srfaytkn/masked-edit-text.svg)](https://jitpack.io/#srfaytkn/masked-edit-text)

# Setup
## Dependency
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.srfaytkn:masked-edit-text:1.0.2'
}
```
## Layout
```xml
 <com.srfaytkn.android.lib.MaskedEditText
   android:id="@+id/input_card_number"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:hint="@string/card_number"
   android:imeOptions="actionDone"
   android:inputType="number"
   android:maxLines="1"
   app:allowedChars="1234567890"
   app:mask="#### #### #### ####" />
```

# License
```
Copyright 2017 Şerafettin Aytekin

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
