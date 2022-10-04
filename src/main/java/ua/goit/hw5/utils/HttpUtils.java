package ua.goit.hw5.utils;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpUtils {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private final String URLHOST;

    public HttpUtils(String url) {
        URLHOST = url;
    }

    public HttpResponse<String> get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URLHOST + url))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public HttpResponse<String> post(String url, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URLHOST + url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response;
        try {
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public HttpResponse<String> postForm(String url, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URLHOST + url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response;
        try {
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public int postFileJavaHTTPClient(String url, Path fileName) throws IOException {
        File file = new File(fileName.toUri());

        MimeMultipartData mimeMultipartData = MimeMultipartData.newBuilder()
                .withCharset(StandardCharsets.UTF_8)
                .addFile("file2", file.toPath(), Files.probeContentType(file.toPath()))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", mimeMultipartData.getContentType())
                .POST(mimeMultipartData.getBodyPublisher())
                .uri(URI.create(URLHOST + url))
                .build();

        try {
            var response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (InterruptedException e) {
            // magic number
            return 0;
        }

    }

    public int postFileJavaUrlConnection(String url, Path filename) throws IOException {
        String charset = "UTF-8";
        String param = "value";
        File binaryFile = new File(filename.toUri());
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.

        HttpURLConnection connection = (HttpURLConnection) new URL(URLHOST + url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true)
        ) {
            // Send normal param.
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=").append(charset).append(CRLF);
            writer.append(CRLF).append(param).append(CRLF).flush();

            // Send binary file.
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"").append(binaryFile.getName()).append("\"").append(CRLF);
            writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(binaryFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            // End of multipart/form-data.
            writer.append("--").append(boundary).append("--").append(CRLF).flush();
        }

// Request is lazily fired whenever you need to obtain information about response.
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode); // Should be 200
        return responseCode;
    }

    public int postFileApacheHTTPClient(String url, Path filename) throws IOException {
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
            HttpPost httppost = new HttpPost(URLHOST + url);


            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("file", new File(filename.toUri()), ContentType.parse(Files.probeContentType(filename)), filename.getFileName().toString())
                    .build();


            httppost.setEntity(reqEntity);

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                return response.getStatusLine().getStatusCode();
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    System.out.println("Response content length: " +    resEntity.getContentLength());
//                }
//                EntityUtils.consume(resEntity);

            }
        }
    }

    public int postFileJanovVersion(String url, Path path) throws IOException, InterruptedException {
        final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofFile(path);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .header("Content-type", Files.probeContentType(path))
                .uri(URI.create(URLHOST + url))
                .build();
        final HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
        return send.statusCode();
    }

    public HttpResponse<String> put(String url, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URLHOST + url))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response;
        try {
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public HttpResponse<String> delete(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URLHOST + url))
                .DELETE()
                .build();
        HttpResponse<String> response;
        try {
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
