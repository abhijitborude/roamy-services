package com.roamy.web.resource;

import com.roamy.config.ConfigProperties;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 1/10/2016.
 */
@RestController
@RequestMapping("/config")
@Api("config")
public class ConfigResource {

    @Autowired
    private ConfigProperties configProperties;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get config properties", notes = "Fetches config properties that drive the behavior of " +
                            "various functionalities in the app.")
    public ConfigProperties findAll() {
        return configProperties;
    }
}
