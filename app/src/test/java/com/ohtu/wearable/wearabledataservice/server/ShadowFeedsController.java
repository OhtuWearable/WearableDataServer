package com.ohtu.wearable.wearabledataservice.server;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by sjsaarin on 31.3.2015.
 */

@Implements(SensorHTTPServer.class)
public class ShadowFeedsController {


    @Implementation
    public boolean getResponse(String uri, String method){
        return true;
    }

}

/*
@Implements(Bitmap.class)
public class MyShadowBitmap {
    @RealObject private Bitmap realBitmap;
    private int bitmapQuality = -1;

    @Implementation
    public boolean compress(Bitmap.CompressFormat format, int quality, OutputStream stream) {
        bitmapQuality = quality;
        return realBitmap.compress(format, quality, stream);
    }

    public int getQuality() {
        return bitmapQuality;
    }
}
}
*/