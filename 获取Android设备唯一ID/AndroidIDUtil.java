public static String getAndroidID() {
    String id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
    Log.e(TAG, "getAndroidID: " + id );
    return id == null ? "" : id;
}