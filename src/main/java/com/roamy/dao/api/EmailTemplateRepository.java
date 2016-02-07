package com.roamy.dao.api;

import com.roamy.domain.EmailTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 2/3/2016.
 */
@Repository
public interface EmailTemplateRepository extends CitableRepository<EmailTemplate, Long> {

    EmailTemplate findByCode(String code);

}
