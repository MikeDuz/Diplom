package skillbox.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String imageTreatment (MultipartFile file) throws Exception;
}
