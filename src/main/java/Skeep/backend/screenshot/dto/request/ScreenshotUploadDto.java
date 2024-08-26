package Skeep.backend.screenshot.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ScreenshotUploadDto implements Serializable {
        List<MultipartFile> file;
}
