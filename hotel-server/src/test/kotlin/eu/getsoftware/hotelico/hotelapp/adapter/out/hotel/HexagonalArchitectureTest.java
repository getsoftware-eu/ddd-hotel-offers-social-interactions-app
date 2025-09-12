package eu.getsoftware.hotelico;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "eu.getsoftware.hotelico", importOptions = {ImportOption.DoNotIncludeTests.class})
public class HexagonalArchitectureTest {

    private static final String DOMAIN = "eu.getsoftware.hotelico.application.domain..";
    private static final String APPLICATION = "eu.getsoftware.hotelico.application..";
    private static final String PORTS = "eu.getsoftware.hotelico.application.port..";
    private static final String ADAPTERS = "eu.getsoftware.hotelico.adapters..";

    @ArchTest
    static final ArchRule application_should_not_depend_on_adapters =
            noClasses()
                    .that()
                    .resideInAPackage(APPLICATION)
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage(ADAPTERS);

    @ArchTest
    public static final ArchRule hexagonal_architecture = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Domain").definedBy("..application.domain..", "..domain..")
            .layer("Application").definedBy("..application..")
            .layer("Ports").definedBy("..application.port..", "..application.ports..", "..port..", "..ports..")
            .layer("Adapters").definedBy("..infrastructure..", "..infrastructure.adapter..", "..adapter..", "..adapters..")

            .whereLayer("Domain").mayOnlyAccessLayers("Domain", "Ports")
            .whereLayer("Application").mayOnlyAccessLayers("Domain", "Ports")
            .whereLayer("Ports").mayOnlyAccessLayers("Domain")
            .whereLayer("Adapters").mayOnlyAccessLayers("Ports", "Application", "Domain");

    @ArchTest
    public static final ArchRule domain_layer_should_not_have_dependencies_on_other_layers =
            noClasses()
                    .that()
                    .resideInAPackage(DOMAIN)
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(ADAPTERS)
                    .as("The Domain layer should not have dependencies on other layers.");

}