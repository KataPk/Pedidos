package cloudcode.pedidos.imageUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Component
public class FileUploadUtil {

    public ResponseEntity<String> upload(File file) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("key", "");

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(file));


            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            String serverUrl = "https://api.e-z.host/files";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

            HttpStatusCode statusCode = response.getStatusCode();
            String responseBody = response.getBody();
            HttpHeaders responseHeaders = response.getHeaders();


            return response;
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Erro:" + e);
        }
    }

    public String getImageUrl(ResponseEntity<String> response) {
        try {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            String imageUrl = jsonNode.get("imageUrl").asText();
            String prefix = "*******";
            String newPrefix = "********";

            return imageUrl.replaceFirst(prefix, newPrefix);

        } catch (Exception e) {
            // Lidar com erros de análise JSON
            return "Erro:" + e;
        }
    }


    public File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

}
