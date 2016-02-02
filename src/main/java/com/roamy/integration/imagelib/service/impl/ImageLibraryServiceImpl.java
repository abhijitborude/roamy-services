package com.roamy.integration.imagelib.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.roamy.integration.imagelib.dto.ImageLibraryIdentifier;
import com.roamy.integration.imagelib.service.api.ImageLibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Abhijit on 12/28/2015.
 */
@Service("imageLibraryService")
public class ImageLibraryServiceImpl implements ImageLibraryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageLibraryServiceImpl.class);

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ImageLibraryIdentifier uploadImage(byte[] imageContent) throws IOException {
        LOGGER.info("uploading image to cloudinary");

        Map result = cloudinary.uploader().upload(imageContent, ObjectUtils.asMap("resource_type", "auto"));

        ImageLibraryIdentifier imageLibraryIdentifier = null;
        if (result != null) {
            imageLibraryIdentifier = new ImageLibraryIdentifier();
            imageLibraryIdentifier.setId((String) result.get("public_id"));
            imageLibraryIdentifier.setUrl((String) result.get("url"));
        }

        LOGGER.info("image upload complete: {}", imageLibraryIdentifier);

        return imageLibraryIdentifier;
    }
}
