package com.roamy.integration.imagelib.service.api;

import com.roamy.integration.imagelib.dto.ImageLibraryIdentifier;

import java.io.IOException;

/**
 * Created by Abhijit on 12/28/2015.
 */
public interface ImageLibraryService {

    ImageLibraryIdentifier uploadImage(byte[] imageContent) throws IOException;
}
