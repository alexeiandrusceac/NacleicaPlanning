package com.planning.nacleica.WebInteractionService;

public class WebResponse {
    public WebRequest Request;
    public int ResponseCode = -999;
    public String ResponseJSON ="";

    public WebResponse(WebRequest request, int httCode, String response) {
        this.Request = request;
        this.ResponseCode = httCode;
        this.ResponseJSON = response;
    }

}