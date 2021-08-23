package skillbox.service.impl;

import com.google.api.services.drive.Drive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skillbox.google.GoogleDriveManager;
import skillbox.service.ImageService;
import skillbox.view.ImageResize;

import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${blog.minFileSize}")
    private long minFileSize;
    private final GoogleDriveManager driveManager;

    @Override
    public String imageTreatment(MultipartFile file) throws Exception {
        if (fileCheck(file)) {
            BufferedImage image = ImageResize.picResize(file);
            Drive driveInstance = driveManager.getInstance();
        }

        return null;
    }

    private boolean fileCheck(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        if (file.getSize() <= minFileSize) {
            return false;
        }
        if (!file.getName().toLowerCase().matches(".*(jpg|jpeg|png).*")) {
            return false;
        }
        return true;
    }

}
