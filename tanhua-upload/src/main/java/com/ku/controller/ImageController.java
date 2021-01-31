package com.ku.controller;

import com.ku.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController
@CrossOrigin
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private PictureService pictureService;

    @GetMapping(value = "/getImage")
    public void getImage(@RequestParam String objectId, HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            InputStream inputStream = pictureService.getImage(objectId);
            if (inputStream == null){
                return;
            }
            BufferedImage image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();

            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

}
