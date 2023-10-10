package cloudcode.pedidos.imageUtils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile file) throws IOException {

        Path uploadPath = Path.of(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file:" + fileName, ioe);
        }


    }

    public static void deleteFile(String uploadDirAnterior, String imagem) {
        try {
            String filePath = uploadDirAnterior + File.separator + imagem;
            File file = new File(filePath);

            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    System.out.println("Arquivo excluído com sucesso: " + filePath);
                } else {
                    System.err.println("Não foi possível excluir o arquivo: " + filePath);
                }
            } else {
                System.err.println("Arquivo não encontrado: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir o arquivo: " + e.getMessage());
        }
    }
}
