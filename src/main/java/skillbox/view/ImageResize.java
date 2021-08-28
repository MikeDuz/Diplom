package skillbox.view;


import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class ImageResize  {



    public static  BufferedImage picResize(MultipartFile picture, int sideMaxLength) throws  Exception{
        BufferedImage image = ImageIO.read(picture.getInputStream());
        Scalr.Mode mode = Scalr.Mode.AUTOMATIC;
        BufferedImage finishImage;
        int width = image.getWidth();
        int height = image.getHeight();
        if(width <= sideMaxLength && height <= sideMaxLength) {
            return image;
        }
        if(width >= height) {
            mode = Scalr.Mode.FIT_TO_WIDTH;
        } else {
            mode = Scalr.Mode.FIT_TO_HEIGHT;
        }
        finishImage = Scalr.resize(image,
                Scalr.Method.BALANCED,
                mode,
                sideMaxLength,
                Scalr.OP_ANTIALIAS);
        BufferedImage convertedImage = new BufferedImage(finishImage.getWidth(), finishImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        convertedImage.getGraphics().drawImage(finishImage,0, 0, null);
        return convertedImage;
    }
}
