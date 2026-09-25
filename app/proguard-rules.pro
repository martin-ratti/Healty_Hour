# Healty Hour ProGuard Rules

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }

# Keep Room entities
-keep class com.healthyhour.app.data.local.db.** { *; }

# Keep Vico chart classes
-keep class com.patrykandpatrick.vico.** { *; }
