package de.rst.core.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import de.rst.core.guice.Slf4jTypeListener;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bindListener(Matchers.any(), new Slf4jTypeListener());
    }
}
