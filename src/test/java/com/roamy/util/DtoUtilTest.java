package com.roamy.util;

import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.dto.CityDto;
import org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 10/12/2015.
 */
public class DtoUtilTest {

    private Logger logger = LoggerFactory.getLogger(DtoUtilTest.class);

    @Test
    public void testConvertToCity() throws Exception {
        // crate dto
        CityDto dto = new CityDto();
        dto.setName("name");
        dto.setStatus("Active");
        dto.setCreatedOn(new Date());
        dto.setCreatedBy("createdBy");
        dto.setLastModifiedOn(new Date());
        dto.setLastModifiedBy("lastModifiedBy");

        // convert it
        City city = DtoUtil.convertToCity(dto);
        logger.info("converted City domain to dto object");

        // assert all converted fields
        assertNotNull("domain object can not be NULL", city);
        assertEquals("name does not match", dto.getName(), city.getName());
        assertEquals("status does not match", dto.getStatus(), city.getStatus().name());
        assertEquals("createdOn does not match", dto.getCreatedOn(), city.getCreatedOn());
        assertEquals("createdBy does not match", dto.getCreatedBy(), city.getCreatedBy());
        assertEquals("lastModifiedOn does not match", dto.getLastModifiedOn(), city.getLastModifiedOn());
        assertEquals("lastModifiedBy does not match", dto.getLastModifiedBy(), city.getLastModifiedBy());
    }

    @Test
    public void testConvertToDto() throws Exception {
        // crate domain
        City city = new City();
        city.setId(2L);
        city.setName("name");
        city.setStatus(Status.Active);
        city.setCreatedOn(new Date());
        city.setCreatedBy("createdBy");
        city.setLastModifiedOn(new Date());
        city.setLastModifiedBy("lastModifiedBy");

        // convert it
        CityDto dto = DtoUtil.convertToDto(city);
        logger.info("converted City dto to domain object");

        // assert all converted fields
        assertNotNull("dto object can not be NULL", dto);
        assertEquals("id does not match", city.getId(), dto.getId());
        assertEquals("name does not match", city.getName(), dto.getName());
        assertEquals("status does not match", city.getStatus().name(), dto.getStatus());
        assertEquals("createdOn does not match", city.getCreatedOn(), dto.getCreatedOn());
        assertEquals("createdBy does not match", city.getCreatedBy(), dto.getCreatedBy());
        assertEquals("lastModifiedOn does not match", city.getLastModifiedOn(), dto.getLastModifiedOn());
        assertEquals("lastModifiedBy does not match", city.getLastModifiedBy(), dto.getLastModifiedBy());
    }
}