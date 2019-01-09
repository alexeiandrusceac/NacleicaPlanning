package com.planning.nacleica.WebInteractionService;

public interface IWebCallBack{
    void onSuccess(WebResponse result);

    void onConnectionError(WebRequestException result);

    void onException(WebRequestException result);

    // Always run after execute request
    void onCompleted(BackgroundResponse result);

    // Include onConnectionError && onException
    void onFail(BackgroundResponse result);
}
