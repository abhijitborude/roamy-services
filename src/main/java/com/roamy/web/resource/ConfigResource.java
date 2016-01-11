package com.roamy.web.resource;

import com.roamy.config.ConfigProperties;
import com.roamy.dto.RestResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 1/10/2016.
 */
@RestController
@RequestMapping("/config")
public class ConfigResource {

    @Autowired
    private ConfigProperties configProperties;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ConfigProperties findAll() {
        return configProperties;
    }
}
