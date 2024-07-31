**beta preview**

[Watch the video](https://github.com/user-attachments/assets/433cc77a-5ac3-4c50-855f-f499588234e6)

StreamView
This app is designed to provide an exceptional streaming experience with a robust set of features. StreamView leverages modern Android development tools and libraries to ensure high performance, smooth animations, and a responsive UI. Below you'll find an overview of the app's key features and the technologies it uses.

Features
1. HLS Media Playback with ExoPlayer
StreamView uses ExoPlayer to play HLS (HTTP Live Streaming) media. ExoPlayer is a powerful media player library for Android that provides a highly customizable experience. The player is integrated with Jetpack Compose and is designed to be lifecycle-aware, ensuring smooth playback even during configuration changes. The playback state is saved and restored seamlessly, providing a continuous viewing experience.

2. Shared Code and Business Logic
StreamView employs Kotlin Multiplatform to share code across different platforms, making it easier to maintain and develop. The app utilizes:

Ktor Client: For handling network requests efficiently.
SQLDelight: For managing local database storage with type-safe SQL queries.
Key-Value Storage: A general key-value pair storage system is used to store simple data, making the app lightweight and fast.
3. Custom Animations
StreamView features custom animations to enhance user experience. One of the standout animations is the parallax scrolling behavior. This creates a dynamic and visually appealing effect as users scroll through the content.

4. Custom Scrolling Behavior and Slider
The app includes custom scrolling behaviors tailored to provide a unique user experience. Additionally, StreamView incorporates a custom slider built with Jetpack Compose, allowing for a more intuitive and interactive interface.




Technologies Used
ExoPlayer: For media playback, ensuring high performance and reliability.
Jetpack Compose: For building the UI with a modern, declarative approach.
Kotlin Multiplatform: To share code and business logic across different platforms.
Ktor Client: For making network requests.
SQLDelight: For database management.
Custom Animations: Implemented using Jetpack Compose for a smooth and engaging user experience.
