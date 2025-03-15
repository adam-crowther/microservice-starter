package com.acroteq.test.container;

import static com.acroteq.test.extension.DockerNetworkSingleton.getNetworkInstance;
import static com.acroteq.test.extension.DockerNetworkSingleton.getNetworkName;

import com.acroteq.test.extension.HostNameSetter;
import com.acroteq.test.extension.OutputFrameLogger;
import dasniko.testcontainers.keycloak.KeycloakContainer;

public class KeycloakIamContainer extends KeycloakContainer {

  private static final String KEYCLOAK_DOCKER_IMAGE = "keycloak/keycloak:26.1.3";

  private static final String KEYCLOAK_CONTAINER_NAME = "Keycloak";
  private static final int KEYCLOAK_PORT = 8080;
  private static final String ENV_KC_HOSTNAME_STRICT = "KC_HOSTNAME_STRICT";

  public static KeycloakIamContainer startKeycloakContainer() {
    final KeycloakIamContainer keycloakContainer = new KeycloakIamContainer();
    keycloakContainer.start();
    return keycloakContainer;
  }

  public KeycloakIamContainer() {
    this(KEYCLOAK_DOCKER_IMAGE);
  }

  @SuppressWarnings({ "resource", "PMD.ConstructorCallsOverridableMethod" })
  private KeycloakIamContainer(final String dockerImageName) {
    super(dockerImageName);

    final OutputFrameLogger logConsumer = new OutputFrameLogger(KEYCLOAK_CONTAINER_NAME);
    final HostNameSetter hostNameSetter = new HostNameSetter(KEYCLOAK_CONTAINER_NAME);

    withRealmImportFile("acroteq-realm.json");
    withLogConsumer(logConsumer);
    withCreateContainerCmdModifier(hostNameSetter);
    withExposedPorts(KEYCLOAK_PORT, 9000);
    withAdminUsername("admin");
    withAdminPassword("admin");
    withNetwork(getNetworkInstance());
    withNetworkAliases(getNetworkName());
    withEnv(ENV_KC_HOSTNAME_STRICT, "false");

    withVerboseOutput();
  }
}
