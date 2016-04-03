package com.roamy.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by Abhijit on 2/6/2016.
 */
@Service("templateTranslator")
public class TemplateTranslator {

    @PostConstruct
    private void init() {
        Velocity.init();
    }

    public String translate(String template, Map<String, Object> values) {

        VelocityContext context = new VelocityContext();
        values.forEach((key, value) -> context.put(key, value));

        StringWriter out = new StringWriter();
        Velocity.evaluate(context, out, "velocity", template);

        return out.toString();
    }
}
