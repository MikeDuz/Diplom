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
import java.io.File;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${blog.minFileSize}")
    private long minFileSize;
    @Value("${blog.maxLength}")
    private  int imageMaxLength;
    private String parentFolderName = "upload";
    private final GoogleDriveManager driveManager;
    private String[] nameArray;

    @Override
    public String imageTreatment(MultipartFile file) throws Exception {
        if (fileCheck(file)) {
            String originName = file.getOriginalFilename();
            String fileType = file.getContentType();
            BufferedImage image = ImageResize.picResize(file, imageMaxLength);
            Drive driveInstance = driveManager.getInstance();
            String imageFolderId = folderCheck(driveInstance);
            String imageFilePath = setFilePath(driveManager.uploadFile(image, imageFolderId, file.getName(), fileType));
            return imageFilePath;
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
        if (!file.getOriginalFilename().toLowerCase().matches(".*(jpg|jpeg|png).*")) {
            return false;
        }
        return true;
    }

    private String folderCheck(Drive drive) throws Exception {
        String parentFolderId = driveManager.findOrCreateFolder(null, parentFolderName, drive);
        folderNameSplit(parentFolderId);
        String firstFolderId = driveManager.findOrCreateFolder(parentFolderId, nameArray[0], drive);
        String secondFolderId = driveManager.findOrCreateFolder(firstFolderId, nameArray[1], drive);
        String thirdFolderId = driveManager.findOrCreateFolder(secondFolderId, nameArray[2], drive);
        return thirdFolderId;
    }

    private void folderNameSplit(String name) {
        int namePartLength = name.length() / 3;
        String nameFirst = name.substring(0, namePartLength);
        String nameSecond = name.substring(namePartLength + 1, namePartLength * 2);
        String nameThird = name.substring(namePartLength * 2 + 1);
        nameArray = new String[] {nameFirst, nameSecond, nameThird};
    }

    private String setFilePath(String fileId) {
        return "/" + parentFolderName +
                "/" + nameArray[0] +
                "/" + nameArray[1] +
                "/" + nameArray[2] +
                "/" + fileId;
    }

}
