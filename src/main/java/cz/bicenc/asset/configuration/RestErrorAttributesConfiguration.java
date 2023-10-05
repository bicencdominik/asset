package cz.bicenc.asset.configuration;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;


@Component
public class RestErrorAttributesConfiguration extends DefaultErrorAttributes {


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

        errorAttributes.remove("status");
        errorAttributes.remove("message");
        errorAttributes.remove("path");
        errorAttributes.remove("error");
        errorAttributes.remove("timestamp");
        errorAttributes.remove("trace");

        Throwable error = super.getError(webRequest);

        if (Objects.nonNull(error)) {
            // TODO nase vlastni parametry, preklady, ...
            errorAttributes.put("ErrorMessage", error.getMessage());
        }

        return errorAttributes;
    }
}

