# Keep all Compose-related code
-keep class androidx.compose.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.navigation.** { *; }

# Keep your application class
-keep class com.ma.streamview.android.MainActivity { *; }

# Keep data classes and Parcelable classes if used
-keep class * implements android.os.Parcelable { *; }
-keep class * extends kotlin.Metadata { *; }

# Keep annotations
-keepattributes *Annotation*

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class dagger.internal.** { *; }
-dontwarn javax.annotation.**
-dontwarn dagger.hilt.internal.**
-dontwarn dagger.hilt.android.internal.**

# Ktor
-keep class io.ktor.** { *; }
-keep class kotlinx.** { *; }
-keep class kotlin.** { *; }
-dontwarn io.ktor.**
-dontwarn kotlinx.**
-dontwarn kotlin.**

# ExoPlayer
-keep class com.google.android.exoplayer2.** { *; }
-dontwarn com.google.android.exoplayer2.**

# Add rules from missing_rules.txt
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticMDCBinder
