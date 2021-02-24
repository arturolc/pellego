-dontobfuscate

-dontwarn org.geometerplus.**
-dontwarn org.apache.**
-dontwarn yuku.ambilwarna.**
-dontwarn org.spongycastle.**
-dontwarn com.github.axet.**

-assumenosideeffects class android.util.Log {
  public static *** v(...);
  public static *** d(...);
  public static *** i(...);
  public static *** w(...);
  public static *** e(...);
}
