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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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


    public int postFileJavaUrlConnection(String url, Path path) throws IOException {
        String charset = "UTF-8";
        File binaryFile = new File(path.toUri());
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.

        HttpURLConnection connection = (HttpURLConnection) new URL(URLHOST + url).openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true)
        ) {

            // Send binary file.
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data;name=\"file\";" + "filename=\"" + path.getFileName() + "\"").append(CRLF).append(CRLF).flush();

            Files.copy(binaryFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            // End of multipart/form-data.
            writer.append("--").append(boundary).append("--").append(CRLF).flush();

            // Request is lazily fired whenever you need to obtain information about response.
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode); // Should be 200
            return responseCode;
        }


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


    public int postFileNesvitVersion(String url, Path path) throws IOException {
        //final String PET = "https://petstore.swagger.io/v2/pet/";
        final String nextLine = "\r\n";
        final String twoHyphens = "--";
        final String boundary = Long.toString(System.currentTimeMillis());

        File uploadFile = new File(path.toUri());
        //String url = PET + id + "/uploadImage";
        URL uri = new URL(URLHOST + url);

        HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");


        OutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Connection", "keep-alive");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.connect();

            outputStream = new DataOutputStream(httpURLConnection.getOutputStream());

            String header = twoHyphens + boundary + nextLine;

            header += "Content-Disposition: form-data;name=\"file\";" + "filename=\"" + path.getFileName() + "\"" + nextLine + nextLine;

            outputStream.write(header.getBytes());

            inputStream = new FileInputStream(uploadFile);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }

            outputStream.write(nextLine.getBytes());

            String footer = nextLine + twoHyphens + boundary + twoHyphens + nextLine;
            outputStream.write(footer.getBytes());
            outputStream.flush();
            return httpURLConnection.getResponseCode();
//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
////                System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage());
//                BufferedReader in =
//                        new BufferedReader(
//                                new InputStreamReader(httpURLConnection.getInputStream()));
//                StringBuffer response = new StringBuffer();
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                System.out.println(response);
//            } else {
//                System.err.println("Загрузка не удалась");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 0;
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
