package com.planning.nacleica.WebInteractionService;

public class WebRequest {
    public HttpMethods Method = HttpMethods.GET;
    public WebServiceActions Action;
    public String Params = "";
    public int TimeOut = 20000;
    public boolean ContainsUrl = false;

    private WebRequest() {

    }
    public WebRequest(WebServiceActions action, HttpMethods method, String params) {
        this.Action = action;
        this.Method = method;
        this.Params = params;

    }
    public WebRequest(WebServiceActions action, HttpMethods method, String params, boolean containsUrl, int timeOut) {
        this.Action = action;
        this.Method = method;
        this.Params = params;
        this.TimeOut = timeOut;
        this.ContainsUrl = containsUrl;
    }
}
