package es.upm.prof.grupoaa.detector_test_smells_maven_plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


import br.ufba.jnose.core.Config;
import br.ufba.jnose.core.JNoseCore;
import br.ufba.jnose.dto.TestSmell;
import br.ufba.jnose.dto.TestClass;

import java.io.File;
import java.util.List;

//fase test
@Mojo(name = "tsDetect", defaultPhase = org.apache.maven.plugins.annotations.LifecyclePhase.TEST)
public class DetectTestSmellsMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.basedir}", readonly = true, required = true)
    private File projectDir;

    public void execute() throws MojoExecutionException {
        try {
        	Config config = new Config() {
                public Boolean assertionRoulette() {return TestSmellDetectorConfig.assertionRoulette;}
                public Boolean conditionalTestLogic() {
                    return TestSmellDetectorConfig.conditionalTestLogic;
                }
                public Boolean constructorInitialization() {
                    return TestSmellDetectorConfig.constructorInitialization;
                }
                public Boolean defaultTest() {
                    return TestSmellDetectorConfig.defaultTest;
                }
                public Boolean dependentTest() {
                    return TestSmellDetectorConfig.dependentTest;
                }
                public Boolean duplicateAssert() {
                    return TestSmellDetectorConfig.duplicateAssert;
                }
                public Boolean eagerTest() {
                    return TestSmellDetectorConfig.eagerTest;
                }
                public Boolean emptyTest() {
                    return TestSmellDetectorConfig.emptyTest;
                }
                public Boolean exceptionCatchingThrowing() {
                    return TestSmellDetectorConfig.exceptionCatchingThrowing;
                }
                public Boolean generalFixture() {return TestSmellDetectorConfig.generalFixture;}
                public Boolean mysteryGuest() {
                    return TestSmellDetectorConfig.mysteryGuest;
                }
                public Boolean printStatement() {
                    return TestSmellDetectorConfig.printStatement;
                }
                public Boolean redundantAssertion() {
                    return TestSmellDetectorConfig.redundantAssertion;
                }
                public Boolean sensitiveEquality() {
                    return TestSmellDetectorConfig.sensitiveEquality;
                }
                public Boolean verboseTest() {
                    return TestSmellDetectorConfig.verboseTest;
                }
                public Boolean sleepyTest() {
                    return TestSmellDetectorConfig.sleepyTest;
                }
                public Boolean lazyTest() {
                    return TestSmellDetectorConfig.lazyTest;
                }
                public Boolean unknownTest() {
                    return TestSmellDetectorConfig.unknownTest;
                }
                public Boolean ignoredTest() {
                    return TestSmellDetectorConfig.ignoredTest;
                }
                public Boolean resourceOptimism() {
                    return TestSmellDetectorConfig.resourceOptimism;
                }
                public Boolean magicNumberTest() {return TestSmellDetectorConfig.magicNumberTest;}

                @Override
                public Integer maxStatements() {
                    return 30;
                }
            };

            JNoseCore jnose = new JNoseCore(config, 3);

            List<TestClass> testClasses = jnose.getFilesTest(projectDir.getAbsolutePath());

            for (TestClass testClass : testClasses) {
                getLog().info("Clase de test: " + testClass.getName());
                jnose.getTestSmells(testClass);
                List<TestSmell> testSmellList = testClass.getListTestSmell();
                for (TestSmell testSmell : testSmellList) {
                    getLog().info(testSmell.toString());
                }
            }

        } catch (Exception e) {
            throw new MojoExecutionException("Error al detectar test smells", e);
        }
    }
}
