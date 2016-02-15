package com.roamy.web.resource;

import com.roamy.dao.api.CitableRepository;
import com.roamy.dao.api.VendorRepository;
import com.roamy.domain.Vendor;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/vendors")
@Api("vendor")
public class VendorResource extends CitableResource<Vendor, Long> {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    protected CitableRepository<Vendor, Long> getCitableRepository() {
        return vendorRepository;
    }

    @Override
    protected void validateForCreate(Vendor entity) {

    }

    @Override
    protected void enrichForGet(Vendor entity) {

    }

    @Override
    protected void enrichForCreate(Vendor entity) {

    }

    @Override
    protected void afterEntityCreated(Vendor entity) {

    }

    @Override
    protected void addLinks(Vendor entity) {

    }
}
