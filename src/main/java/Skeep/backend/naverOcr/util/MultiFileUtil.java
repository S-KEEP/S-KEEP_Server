package Skeep.backend.naverOcr.util;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.naverOcr.exception.NaverOcrErrorCode;
import org.springframework.web.multipart.MultipartFile;

public class MultiFileUtil {

    public static String determineImageFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            if (contentType.equals("image/jpeg") || contentType.equals("image/jpg"))
                return "jpg";
            else if (contentType.equals("image/png"))
                return "png";
            else
                throw BaseException.type(NaverOcrErrorCode.UNSUPPORTED_FILE_FORMAT);
        } else
            throw BaseException.type(NaverOcrErrorCode.UNDETERMINED_FILE);
    }
}
