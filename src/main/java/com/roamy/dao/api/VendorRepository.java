package com.roamy.dao.api;

import com.roamy.domain.Vendor;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 10/30/2015.
 */
@Repository
public interface VendorRepository extends CitableRepository<Vendor, Long> {
}
