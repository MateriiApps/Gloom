
<div align="center">

  <img src=".github/images/logo.png" alt="Gloom logo" width="200px" />
  
  # Gloom

  
  Material You Github client
  
  ---
  [![Debug build status](https://img.shields.io/github/actions/workflow/status/MateriiApps/Gloom/android.yml?label=Debug%20Build&logo=github&style=for-the-badge&branch=main)](https://nightly.link/MateriiApps/Gloom/workflows/android/main/gloom-debug.zip)
  [![Stars](https://img.shields.io/github/stars/MateriiApps/Gloom?logo=github&style=for-the-badge)](https://github.com/MateriiApps/Gloom/stargazers)
  [![GitHub downloads](https://img.shields.io/discord/885879572447522817?logo=discord&logoColor=white&style=for-the-badge)](https://discord.gg/3y6vbneMsW)
  
  <br>
  
  ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/MateriiApps/Gloom?logo=github&logoColor=%23fff&style=for-the-badge)
  ![GitHub top language](https://img.shields.io/github/languages/top/MateriiApps/Gloom?style=for-the-badge)

|                                                                                                                                                                                                                                                       |                                                                                                                                                                                                                                                       |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <picture><source media="(prefers-color-scheme: dark)" srcset=".github/images/Home.png" /><source media="(prefers-color-scheme: light)" srcset=".github/images/Home-Light.png" /><img src=".github/images/Home.png" width="200px"/></picture>          | <picture><source media="(prefers-color-scheme: dark)" srcset=".github/images/Explore.png" /><source media="(prefers-color-scheme: light)" srcset=".github/images/Explore-Light.png" /><img src=".github/images/Explore.png" width="200px"/></picture> |
| <picture><source media="(prefers-color-scheme: dark)" srcset=".github/images/Profile.png" /><source media="(prefers-color-scheme: light)" srcset=".github/images/Profile-Light.png" /><img src=".github/images/Profile.png" width="200px"/></picture> | <picture><source media="(prefers-color-scheme: dark)" srcset=".github/images/Repo.png" /><source media="(prefers-color-scheme: light)" srcset=".github/images/Repo-Light.png" /><img src=".github/images/Repo.png" width="200px"/></picture>          |
</div>

## Roadmap

You can view the project roadmap [here](https://github.com/orgs/MateriiApps/projects/2)

Installation
---
 1. Download the [latest actions build](https://nightly.link/MateriiApps/Gloom/workflows/android/main/artifact.zip), if on PC then transfer the file over to your phone
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
    - `git clone https://github.com/MateriiApps/Gloom.git && cd Gloom`
2. Build the project
    - Linux: `chmod +x gradlew && ./gradlew assembleDebug`
    - Windows: `./gradlew assembleDebug`
3. Install on device
    - [Enable usb debugging](https://developer.android.com/studio/debug/dev-options) and plug in your phone
    - Run `adb install app/build/outputs/apk/debug/app-debug.apk`

## Contributing

This is an open-source project, you can do so without any programming.

Here are a few things you can do:

- [Test and report issues](https://github.com/MateriiApps/Gloom/issues/new/choose)
- [Translate the app into your language](https://crowdin.com/project/gloom)
    
License
---
Gloom is licensed under the GNU General Public License v3.0

[![License: GPLv2](https://img.shields.io/badge/License-GPL%20v3-blue.svg?style=for-the-badge)](https://github.com/MateriiApps/Gloom/blob/main/LICENSE)
