package cloudcode.pedidos.imageUtils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;


public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile file) throws IOException{

        Path uploadPath = Path.of(uploadDir);

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe){
            throw new IOException("Could not save image file:" + fileName, ioe);
        }


    }


}
