package in.roamy.dao;

import in.roamy.TestApplication;
import in.roamy.domain.City;
import in.roamy.domain.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by Abhijit on 10/8/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class CityRepositoryTest {

    private static final Log log = LogFactory.getLog(CityRepositoryTest.class);

    private static final Long CITY_ID_MUMBAI = 1L;
    private static final Long CITY_ID_PUNE = 2L;

    @Autowired
    CityRepository cityRepository;

    @Before
    public void setup() {
        City city = new City();
        city.setName("Delhi");
        city.setStatus(Status.Active);
        city.setCreatedBy("test");
        city.setCreatedOn(new Date());
        city.setLastModifiedBy("test");
        city.setLastModifiedOn(new Date());

        cityRepository.save(city);
    }

    @Test
    public void findCityByIdTest() {
        City city = cityRepository.findOne(CITY_ID_MUMBAI);
        Assert.assertNotNull("There should be a city with id: " + CITY_ID_MUMBAI, city);
        Assert.assertEquals("City name should be Mumbai", "Mumbai", city.getName());
    }

    @Test
    public void findAllCityTest() {
        Iterable<City> cities = cityRepository.findAll();
        log.info(cities);
        Assert.assertNotNull("There should be at least one city", cities);
    }

}
