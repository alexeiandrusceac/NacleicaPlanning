package com.planning.nacleica.WebInteractionService;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.planning.nacleica.CheckNetworkConnection.ConnectionNetworkCheck;
import com.planning.nacleica.IInternetCheckCallback;
import com.planning.nacleica.authactions.Worker;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebInteractionService {
    private static final String BASE_URL = "";
    public static Map<WebServiceActions, String> ActionMap = new HashMap<WebServiceActions, String>();
    private static List<WebInteractionServiceAsyncTask> listInteractions = new ArrayList<WebInteractionServiceAsyncTask>();

    public WebInteractionService() {
        ActionMap.put(WebServiceActions.AUTHENTIFICATION, "");
        ActionMap.put(WebServiceActions.GETADMINMAKETTASKS, "");
        ActionMap.put(WebServiceActions.GETNEWADMINTASKS, "");
        ActionMap.put(WebServiceActions.GETNEWWORKERTASKS, "");
        ActionMap.put(WebServiceActions.GETPROGRESSWORKERTASKS, "");
        ActionMap.put(WebServiceActions.GETDONEWORKERTASKS, "");
        ActionMap.put(WebServiceActions.GETWORKERS, "");
        ActionMap.put(WebServiceActions.GETWORKER, "");
        ActionMap.put(WebServiceActions.ADDWORKERS, "CreateWorker");
        ActionMap.put(WebServiceActions.UPDATEWORKER, "");
        ActionMap.put(WebServiceActions.ASSIGNTASK, "");
        ActionMap.put(WebServiceActions.UPDATETASK, "");
        ActionMap.put(WebServiceActions.GETSTATUSTASKSWORKERS, "");
        ActionMap.put(WebServiceActions.CREATENEWTASK, "");
    }

    ///*******Actions to server*****///
    public WebInteractionService authorization(final WebCallBack callBack) {
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.AUTHENTIFICATION, HttpMethods.GET, "")).execute(new WebRequest(WebServiceActions.AUTHENTIFICATION, HttpMethods.GET, ""));
        return this;
    }

    public WebInteractionService getWorkers(final WebCallBack callBack) {
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETWORKERS, HttpMethods.GET, "")).execute(new WebRequest(WebServiceActions.GETWORKERS, HttpMethods.GET, ""));
        return this;
    }

    public WebInteractionService getWorker(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETWORKER, HttpMethods.GET, params)).execute(new WebRequest(WebServiceActions.GETWORKER, HttpMethods.GET, params));
        return this;
    }

    public WebInteractionService getAdminMaketTasks(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETADMINMAKETTASKS, HttpMethods.GET, params)).execute(new WebRequest(WebServiceActions.GETADMINMAKETTASKS, HttpMethods.GET, params));
        return this;

    }

    public WebInteractionService getAdminNewTasks(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETNEWADMINTASKS, HttpMethods.GET, params)).execute(new WebRequest(WebServiceActions.GETNEWADMINTASKS, HttpMethods.GET, params));
        return this;
    }

    public WebInteractionService getDoneWorkerTasks(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETDONEWORKERTASKS, HttpMethods.GET, params)).execute(new WebRequest(WebServiceActions.GETDONEWORKERTASKS, HttpMethods.GET, params));
        return this;
    }

    public WebInteractionService getProgressWorkerTasks(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETPROGRESSWORKERTASKS, HttpMethods.GET, params)).execute(new WebRequest(WebServiceActions.GETPROGRESSWORKERTASKS, HttpMethods.GET, params));
        return this;
    }

    public WebInteractionService getNewWorkerTasks(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETNEWWORKERTASKS, HttpMethods.GET, params)).execute(new WebRequest(WebServiceActions.GETNEWWORKERTASKS, HttpMethods.GET, params));
        return this;
    }

    public WebInteractionService getStatusWorkers(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.GETSTATUSTASKSWORKERS, HttpMethods.GET, params)).execute(new WebRequest(WebServiceActions.GETSTATUSTASKSWORKERS, HttpMethods.GET, params));
        return this;
    }

    public WebInteractionService postTask(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.CREATENEWTASK, HttpMethods.POST, params)).execute(new WebRequest(WebServiceActions.CREATENEWTASK, HttpMethods.POST, params));
        return this;
    }

    public WebInteractionService createWorker(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.ADDWORKERS, HttpMethods.POST, params)).execute(new WebRequest(WebServiceActions.ADDWORKERS, HttpMethods.POST, params));
        return this;
    }

    public WebInteractionService updateTask(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.UPDATETASK, HttpMethods.POST, params)).execute(new WebRequest(WebServiceActions.UPDATETASK, HttpMethods.POST, params));
        return this;
    }

    public WebInteractionService updateWorker(final WebCallBack callBack, int index) {
        String params = "index=" + index;
        new WebInteractionServiceAsyncTask(callBack, new WebRequest(WebServiceActions.UPDATEWORKER, HttpMethods.POST, params)).execute(new WebRequest(WebServiceActions.UPDATEWORKER, HttpMethods.POST, params));
        return this;
    }


    ///*******Actions to server*****///
    private class WebInteractionServiceAsyncTask extends AsyncTask<WebRequest, Void, BackgroundResponse> {
        private IWebCallBack Callback = null;
        private WebRequest _webApiRequest;


        public WebInteractionServiceAsyncTask(IWebCallBack callback, WebRequest webApiRequest) {
            this.Callback = callback;
            this._webApiRequest = webApiRequest;

            for (int i = 0; i < listInteractions.size(); i++) {
                WebInteractionServiceAsyncTask task = listInteractions.get(i);
                if (task._webApiRequest.Action == webApiRequest.Action) {
                    task.cancel(true);
                    listInteractions.remove(i);

                    break;
                }
            }
            listInteractions.add(this);
        }

        @Override
        protected BackgroundResponse doInBackground(WebRequest... webRequests) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            BackgroundResponse response = new BackgroundResponse();

            try {


                if (webRequests == null || webRequests.length == 0) {
                    throw new Exception("No request included!");
                }
                WebRequest war = webRequests[0];
                if (!ActionMap.containsKey(war.Action)) {
                    throw new Exception("No API action found!");
                }

                response.Request = war;

                String path = ActionMap.get(war.Action);
                long time = System.currentTimeMillis();
                Uri builtURI = Uri.parse(((!war.ContainsUrl) ? BASE_URL : "") + path + "?_" + String.valueOf(time) + ((war.Method == HttpMethods.GET && !war.Params.isEmpty()) ? "&" + war.Params : ""));
                URL requestURL = new URL(builtURI.toString());
                System.setProperty("http.keepAlive", "false");
                urlConnection = (HttpURLConnection) requestURL.openConnection();
                urlConnection.setConnectTimeout(war.TimeOut);
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestProperty("Client-Id", "MobileKit.Android");
                urlConnection.setRequestMethod(war.Method.toString());
                /*if (SessionKey != null)
                    urlConnection.setRequestProperty("Authorization", "Basic " + SessionKey);
*/
                if (war.Method == HttpMethods.POST) {
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    wr.writeBytes(war.Params);
                    wr.flush();
                    wr.close();
                } else {
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                }

                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                if (builder.length() == 0) {
                    return null;
                }

                response.ResponseBody = builder.toString();
                response.ResponseHttpCode = urlConnection.getResponseCode();


            } catch (ConnectException cex) {
                response.Exception = cex;
                cex.printStackTrace();
            } catch (Exception ex) {
                response.Exception = ex;
                ex.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response;
        }

        protected void onPostExecute(BackgroundResponse result) {
            Log.i("", "RESULT = " + result);
            listInteractions.remove(this);

            if (this.Callback != null) {
                this.Callback.onCompleted(result);

                if (result.ResponseHttpCode == 200) {

                    try {
                        Gson gson = new Gson();
                        switch (result.Request.Action) {
                            case AUTHENTIFICATION:
                                //LoginResponse response = gson.fromJson(result.ResponseBody, LoginResponse.class);
                                //setSessionKey(response.SessionToken);
                                break;
                        }
                        this.Callback.onSuccess(new WebResponse(result.Request, result.ResponseHttpCode, result.ResponseBody));

                    } catch (Exception e) {
                        this.Callback.onException(new WebRequestException(e));
                        e.printStackTrace();
                    }

                } else  {
                    new ConnectionNetworkCheck(internet -> {
                        if(!internet) {
                            this.Callback.onFail(result);
                            this.Callback.onConnectionError(new WebRequestException(result, WebRequestException.ConnectionErrorTypeEnum.NO_INTERNET_CONNECTION));
                        }
                        else if (result.ResponseHttpCode == -900) {
                            try {
                                this.Callback.onFail(result);
                                this.Callback.onConnectionError(new WebRequestException(result));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                this.Callback.onFail(result);
                                this.Callback.onException(new WebRequestException(result));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });
                }
            }
        }
    }
    public WebInteractionService CheckInternetConnection(final IInternetCheckCallback callback) {
        new InternetCheck(callback);
        return this;
    }

    static class InternetCheck extends AsyncTask<Void,Void,Boolean> {
        private final String[] mdixdnss = { "212.0.200.1", "212.0.200.2" };
        private IInternetCheckCallback mConsumer;

        public  InternetCheck(IInternetCheckCallback consumer) { mConsumer = consumer; execute(); }

        @Override protected Boolean doInBackground(Void... voids) {
            boolean cn = false;
            for(int i = 0; i< mdixdnss.length; i++) {
                try {
                    String dns = mdixdnss[i];
                    Socket sock = new Socket();
                    sock.connect(new InetSocketAddress(dns, 53), 1500);
                    sock.close();
                    cn = true;
                } catch (IOException e) {
                    cn = false;
                }
                if(cn) break;
            }

            return cn;
        }

        @Override protected void onPostExecute(Boolean internet) { mConsumer.accept(internet); }
    }

}
