package ua.goit.hw5.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpUtilsTest {
    private static final String URL_FOR_SEND_IMAGE = "/pet/3/uploadImage";
    private static final Path filePath = Paths.get("petImage.jpg");
    private static final HttpUtils HTTP_UTILS = new HttpUtils("https://petstore.swagger.io/v2");


    @org.junit.jupiter.api.Test
    void postFileJavaUrlConnection() {
        //given
        int statusCode;
        //when
        try {
            statusCode = HTTP_UTILS.postFileJavaUrlConnection(URL_FOR_SEND_IMAGE, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //then
        assertEquals(200, statusCode);
    }

    @org.junit.jupiter.api.Test
    void postFileApacheHTTPClient() {
        //given
        int statusCode;
        //when
        try {
            statusCode = HTTP_UTILS.postFileApacheHTTPClient(URL_FOR_SEND_IMAGE, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //then
        assertEquals(200, statusCode);
    }


    @Test
    void postFileNesvitVersion() {
        //given
        int statusCode;
        //when
        try {
            statusCode = HTTP_UTILS.postFileNesvitVersion(URL_FOR_SEND_IMAGE, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //then
        assertEquals(200, statusCode);
    }
}
