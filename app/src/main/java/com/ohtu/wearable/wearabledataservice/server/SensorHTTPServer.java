package com.ohtu.wearable.wearabledataservice.server;

import android.util.Log;

/**
 * HTTP server, passes valid HTTP requests to FeedsController and returns HTTP response received from FeedsController
 */
 public class SensorHTTPServer extends NanoHTTPD {

    private FeedsController feedsController;

    public SensorHTTPServer(FeedsController feedsController)
    {
        super(8080);
        this.feedsController = feedsController;
    }

    /**
     * if uri is /feeds* forwards request to FeedsController otherwise returns "NOT FOUND" response
     *
     * @param session The HTTP session
     * @return NanoHTTPD.Response
     */
    @Override
    public Response serve(IHTTPSession session) {
        Log.w("websrvr", session.getHeaders().toString());

        String parsedUri = "";
        String uri = session.getUri();

        String method = session.getMethod().toString();
        Log.w("websrvr", "method: " + method);

        if (uri.length() >= 6) {
            parsedUri = uri.substring(0,6);
        } else {
            return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found");
        }

        if (parsedUri.equals("/feeds")) {
            String feedsUri = "";
            if (parsedUri.length()>=6){
                feedsUri = uri.substring(6);
            }
            //retrieve answer from FeedsController
            NanoHTTPD.Response response = feedsController.getResponse(feedsUri, method);
            //allow cross-site requests
            response.addHeader("Access-Control-Allow-Origin","*");
            return response;
        } else {
            return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found");
        }
    }
}