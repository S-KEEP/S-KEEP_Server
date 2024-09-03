package Skeep.backend.global.util;

import Skeep.backend.global.dto.CustomMultipartFile;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageConverter {
    public static MultipartFile convertUrlToMultipartFile(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            try (InputStream inputStream = url.openStream();
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

                BufferedImage urlImage = ImageIO.read(inputStream);
                if (urlImage == null) {
                    throw BaseException.type(GlobalErrorCode.CANNOT_CONVERT_IMAGE);
                }

                ImageIO.write(urlImage, "jpg", bos);
                byte[] byteArray = bos.toByteArray();
                return new CustomMultipartFile(byteArray, imageUrl);
            }
        } catch (IOException e) {
            throw BaseException.type(GlobalErrorCode.CANNOT_CONVERT_IMAGE);
        }
    }
}
