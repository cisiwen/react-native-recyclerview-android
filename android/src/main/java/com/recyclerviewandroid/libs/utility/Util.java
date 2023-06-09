package com.recyclerviewandroid.libs.utility;

import android.webkit.MimeTypeMap;

public class Util {
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String getExtension(String mimeType) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        return extension;
    }
}
