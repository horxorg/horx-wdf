package org.horx.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Http客户端工具类。
 * @since 1.0
 */
public final class HttpClientUtils {

    private HttpClientUtils() {}

    /**
     * 下载。
     * @param url
     * @param destination
     * @throws IOException
     */
    public static void download(String url, File destination) throws IOException {
        download(url, (response) -> FileUtils.copyInputStreamToFile(response.getInputStream(), destination), HttpMethod.GET);
    }

    /**
     * 下载。
     * @param url
     * @param responseHandler
     * @throws IOException
     */
    public static void download(String url, DownloadResponseHandler responseHandler) throws IOException {
        download(url, responseHandler, HttpMethod.GET);
    }

    /**
     * 下载。
     * @param url
     * @param responseHandler
     * @param httpMethod
     * @throws IOException
     */
    public static void download(String url, DownloadResponseHandler responseHandler, HttpMethod httpMethod) throws IOException {
        CloseableHttpClient client = null;
        CloseableHttpResponse httpResponse = null;
        try {
            client = HttpClientBuilder.create().build();
            HttpUriRequest httpUriRequest = genHttpUriRequest(url, httpMethod);
            httpResponse = client.execute(httpUriRequest);
            DownloadResponse downloadResponse = new DownloadResponse(httpResponse);
            responseHandler.handle(downloadResponse);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } finally {
                if (client != null) {
                    client.close();
                }
            }
        }
    }

    private static HttpUriRequest genHttpUriRequest(String url, HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return new HttpGet(url);
            case POST:
                return new HttpPost(url);
            case HEAD:
                return new HttpHead(url);
            case PUT:
                return new HttpPut(url);
            case PATCH:
                return new HttpPatch(url);
            case DELETE:
                return new HttpDelete(url);
            case OPTIONS:
                return new HttpOptions(url);
            case TRACE:
                return new HttpTrace(url);
        }
        return new HttpGet(url);
    }

    static class DownloadResponse {
        private HttpResponse httpResponse;

        public DownloadResponse(HttpResponse httpResponse) {
            this.httpResponse = httpResponse;
        }

        public HttpResponse getHttpResponse() {
            return httpResponse;
        }

        public InputStream getInputStream() throws IOException {
            HttpEntity entity = httpResponse.getEntity();
            return entity.getContent();
        }

        public String getFileName() {
            Header contentHeader = httpResponse.getFirstHeader("Content-Disposition");
            String fileName = null;
            if (contentHeader != null) {
                HeaderElement[] values = contentHeader.getElements();
                if (values.length == 1) {
                    NameValuePair param = values[0].getParameterByName("filename");
                    if (param != null) {
                        fileName = param.getValue();
                    }
                }
            }
            return fileName;
        }
    }

    static interface DownloadResponseHandler {
        void handle(DownloadResponse downloadResponse) throws IOException;
    }

}
