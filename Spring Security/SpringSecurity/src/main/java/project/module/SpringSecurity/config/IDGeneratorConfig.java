package project.module.SpringSecurity.config;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.security.SecureRandom;

public class IDGeneratorConfig implements IdentifierGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return 10_000_000L + RANDOM.nextInt(90_000_000);
    }
}