# FuruiMe (formerly AniHyou) - AI Assistant Notes

## Overview
This document contains information on the customizations and build environment fixes applied to this fork of AniHyou. If you are an AI assistant instructed to modify this repository, read these notes to understand what has been changed and how to build the project.

## 1. Environment & Build Instructions
- **Java Version**: The project requires **Java 17**. A bug exists with Gradle and Java 26, so `org.gradle.java.home` has been set in `gradle.properties` to point to a valid Java 17 installation.
- **WearOS Build Error**: There is currently a compilation error in the `:wearos` module due to a missing default icon reference (`refresh_24`). When building the main app, use flavor-specific commands to bypass `wearos` compilation.
- **Compilation Script**: Use the included `./build_apk.sh` script to compile the main app. 
  - The script executes `./gradlew :app:assembleFossDebug`.
  - It automatically extracts the Universal APK (`anihyou-1.5.0-foss-universal-debug.apk`) to avoid `INSTALL_FAILED_NO_MATCHING_ABIS` errors on physical devices or emulators, and installs it via ADB.

## 2. Codebase Modifications (Customizations)
### App Renaming
The application was renamed to **FuruiMe**. This change was primarily made in the string resources (`core/resources/src/main/res/values/strings.xml`).

### Score Visibility Changes
The user requested that all scores and weighted ratings be hidden from the UI, EXCEPT when the item's `MediaListStatus` is marked as `COMPLETED`.
- **Mean & Average Score blocks**: Removed completely from the MediaDetailsView (`feature/mediadetails...MediaDetailsView.kt`).
- **Score Indicators Conditional Rendering**: The following composables were modified to accept a `status: MediaListStatus? = null` parameter and will early-return `if (status != MediaListStatus.COMPLETED)`:
  - `SmallScoreIndicator` (shows global mean score percentages)
  - `BadgeScoreIndicator` (shows personal point scores with backgrounds)
  - `MinimalScoreIndicator` (shows personal points with minimal styling)
- **Callers**: The 10 invocations of these indicators across the `feature/` modules (`SeasonAnimeView`, `DiscoverMediaContent`, `CurrentListItem`, User lists, etc.) were updated to pass `status = item.mediaListEntry?.basicMediaListEntry?.status` (or equivalent) so that scores dynamically appear only for completed series.

## 3. Working Guidelines
- Always prioritize using universal APKs over specific architectures (`x86`) if ADB installation errors occur.
- Modifying UI components related to Media Lists requires an understanding of the AniList GraphQL queries used in `core/network`.
- The UI extensively uses Material 3 Compose and the MVI architecture pattern for ViewModels.
