import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

abstract class PluginTest extends Specification {

  @TempDir
  File testProjectDir
  File settingsFile
  File buildFile

  def setup() {
    settingsFile = new File(testProjectDir, 'settings.gradle.kts').tap { file -> file.append('rootProject.name = \'test\'') }
    buildFile = new File(testProjectDir, 'build.gradle.kts')
  }

  def runTask(String task) {
    GradleRunner.create()
          .withProjectDir(testProjectDir)
          .withArguments(task, '--stacktrace')
          .withPluginClasspath()
          .build()
  }

  def runTaskWithFailure(String task) {
    GradleRunner.create()
          .withProjectDir(testProjectDir)
          .withArguments(task, '--stacktrace')
          .withPluginClasspath()
          .buildAndFail()
  }
}
