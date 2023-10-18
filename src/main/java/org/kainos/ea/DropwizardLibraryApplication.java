package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.resources.BookController;
import org.kainos.ea.resources.LoanController;
import org.kainos.ea.resources.MemberController;

public class DropwizardLibraryApplication extends Application<DropwizardLibraryConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropwizardLibraryApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropwizardLibrary";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardLibraryConfiguration> bootstrap) {
        // TODO: application initialization
        bootstrap.addBundle(new SwaggerBundle<DropwizardLibraryConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(DropwizardLibraryConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final DropwizardLibraryConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new BookController());
        environment.jersey().register(new MemberController());
        environment.jersey().register(new LoanController());
    }

}
