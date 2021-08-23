package skillbox.view;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;


public class ImageResize  {

    @Value("${blog.imageMaxLenght}")
    private static int sideMaxLenght;

    public static  BufferedImage picResize(MultipartFile picture) throws  Exception{
        BufferedImage image = ImageIO.read((InputStream) picture);
        BufferedImage finishImage = null;
        int width = image.getWidth();
        int height = image.getHeight();
        if(width <= sideMaxLenght && height <= sideMaxLenght) {
            return image;
        }
        if(width >= height) {
            finishImage = Scalr.resize(image,
                    Scalr.Method.BALANCED,
                    Scalr.Mode.FIT_TO_WIDTH,
                    sideMaxLenght,
                    Scalr.OP_ANTIALIAS);
        } else {
            finishImage = Scalr.resize(image,
                    Scalr.Method.BALANCED,
                    Scalr.Mode.FIT_TO_HEIGHT,
                    sideMaxLenght,
                    Scalr.OP_ANTIALIAS);
        }
        return finishImage;
    }
}
