package liuliu.custom.method.http;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import liuliu.custom.model.HTTPResponse;

public class HttpUtility {
    public final static int BUFFER_SIZE = 4048 * 10;
    public static String API_HOST = "";
    public static String API_PATH = "Services/Social.html";

    public static void setHost(String host) {
        API_HOST = getUrl(host);
    }

    public static String getUrl(String host) {
        String url = "";
        if (host.indexOf("http://") != -1)
            url = String.format("%s/%s", host, API_PATH);
        else
            url = String.format("http://%s/%s", host, API_PATH);

        return url;
    }

    public static DefaultHttpClient httpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
        params.setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.TRUE);
        params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8"); // 默认为ISO-8859-1
        params.setParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET, "UTF-8"); // 默认为US-ASCII
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);

        return httpClient;
    }

    public static DefaultHttpClient httpClient(int timeout) {
        DefaultHttpClient httpClient = httpClient();
        if (timeout > 0) {
            httpClient.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
            httpClient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, timeout);
        }
        return httpClient;
    }

    private HttpUtility() {
    }

    public static HTTPResponse httpGet(String url, int timeout) {

        HTTPResponse resp = new HTTPResponse();
        try {

            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient(timeout).execute(get);
            StatusLine status = response.getStatusLine();
            resp.code = status.getStatusCode();
            resp.content = getResponseContent(response);

            return resp;

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        return resp;
    }

    public static HTTPResponse httpGet(Map<String, String> params) {

        HTTPResponse resp = new HTTPResponse();
        try {
            HttpGet get = new HttpGet(API_HOST);

            String p = "";
            for (Iterator<String> iter = params.keySet().iterator(); iter
                    .hasNext(); ) {
                String key = iter.next();
                String value = params.get(key);
                get.addHeader(key, value);
                p += "&" + key + "=" + value;
            }
            HttpResponse response = httpClient().execute(get);
            StatusLine status = response.getStatusLine();
            resp.code = status.getStatusCode();
            resp.content = getResponseContent(response);
            resp.url = p;

            return resp;

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        return resp;
    }

    public static HTTPResponse httpPost(String url, String entity) {
        HTTPResponse resp = new HTTPResponse();
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(entity));

            HttpResponse response = httpClient().execute(post);
            StatusLine status = response.getStatusLine();
            resp.code = status.getStatusCode();
            resp.content = getResponseContent(response);

            return resp;
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
        return resp;
    }

    public static HTTPResponse httpPost(Map<String, String> params,
                                        String entity) {
        HTTPResponse resp = new HTTPResponse();
        try {
            HttpPost post = new HttpPost(API_HOST);
            post.setHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            String p = "";
            // 将参数放入Header
            for (String key : params.keySet()) {
                String value = params.get(key);
                post.addHeader(key, value);
                p += "&" + key + "=" + value;
            }
            // 正文
            post.setEntity(new StringEntity(entity));

            HttpResponse response = httpClient().execute(post);
            StatusLine status = response.getStatusLine();
            resp.code = status.getStatusCode();
            resp.content = getResponseContent(response);
            resp.url = p + "&" + entity;

            return resp;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public static HTTPResponse httpPost(String uri, Map<String, String> params,
                                        String entity) {
        HTTPResponse resp = new HTTPResponse();
        try {
            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            String p = "";
            // 将参数放入Header
            for (String key : params.keySet()) {
                String value = params.get(key);
                post.addHeader(key, value);
                p += "&" + key + "=" + value;
            }
            // 正文
            post.setEntity(new StringEntity(entity));

            HttpResponse response = httpClient().execute(post);
            StatusLine status = response.getStatusLine();
            resp.code = status.getStatusCode();
            resp.content = getResponseContent(response);
            resp.url = p + "&" + entity;

            return resp;
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
        return resp;
    }

    private static String getResponseContent(HttpResponse response) {
        InputStream is = null;
        String content = null;
        try {
            is = response.getEntity().getContent();
            content = stream2String(is);

        } catch (IOException e) {
            content = "";
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return content;

    }

    public static String stream2String(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[BUFFER_SIZE];
        int len = 0;

        try {
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
        } catch (IOException e) {

        }

        String result = null;
        try {
            result = new String(baos.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // bug??
            result = new String(baos.toByteArray());
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
            }
        }
        return result;
    }
}
