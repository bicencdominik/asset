package cz.bicenc.asset.configuration;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorResolver implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Security is off");
    }
}
