
<div align="center">

  <img src="https://user-images.githubusercontent.com/44992537/197398356-81d33bbf-35bc-43a6-96c1-002f1b1923cf.png" alt="Gloom logo" width="200px" />
  
  # Gloom

  
  Material You Github client
  
  ---
  ![Debug build status](https://img.shields.io/github/workflow/status/MateriApps/Gloom/Build%20debug%20APK/main?label=Debug%20Build&logo=github&style=for-the-badge)
  [![Stars](https://img.shields.io/github/stars/MateriApps/Gloom?logo=github&style=for-the-badge)](https://github.com/MateriApps/Gloom/stargazers)
  [![GitHub downloads](https://img.shields.io/discord/885879572447522817?logo=discord&logoColor=white&style=for-the-badge)](https://discord.gg/3y6vbneMsW)
  
  <br>
  
  ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/MateriApps/Gloom?logo=github&logoColor=%23fff&style=for-the-badge)
  ![Line count](https://img.shields.io/tokei/lines/github/MateriApps/Gloom?logo=github&logoColor=%23fff&style=for-the-badge)
  ![GitHub top language](https://img.shields.io/github/languages/top/MateriApps/Gloom?style=for-the-badge)
</div>


## Screenshots


| Profile | Starred | Repositories | Organizations |
| --------------- | --------------- | --------------- | --------------- |
| <img src="github/images/Profile.png" width="200px"> | <img src="github/images/Starred.png" width="200px"> | <img src="github/images/Repositories.png" width="200px"> | <img src="github/images/Organizations.png" width="200px"> |

Installation
---
 1. Download the [latest actions build](https://nightly.link/MateriApps/Gloom/workflows/android/main/artifact.zip), if on PC then transfer the file over to your phone
 2. Unzip `artifact.zip`
 3. Make sure to allow install unknown apps
 4. Install the apk

Build
---

#### Prerequisites
  - [Git](https://git-scm.com/downloads)
  - [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
  - [Android SDK](https://developer.android.com/studio)

#### Instructions

1. Clone the repo
    - `git clone https://github.com/MateriApps/Gloom.git && cd Gloom`
2. Build the project
    - Linux: `chmod +x ./gradlew && gradlew assembleDebug`
    - Windows: `./gradlew assembleDebug`
3. Install on device
    - [Enable usb debugging](https://developer.android.com/studio/debug/dev-options) and plug in your phone
    - Run `adb install app/build/outputs/apk/debug/app-debug.apk`
    
## Contributing

This is an open-source project, you can do so without any programming.

Here are a few things you can do:

- [Test and report issues](https://github.com/MateriApps/Gloom/issues/new/choose)
- [Translate the app into your language](https://crowdin.com/project/gloom)
    
License
---
Gloom is licensed under the GNU General Public License v3.0

[![License: GPLv2](https://img.shields.io/badge/License-GPL%20v3-blue.svg?style=for-the-badge)](https://github.com/MateriApps/Gloom/blob/main/LICENSE)
