package com.acroteq.ticketing.test.container;

import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkInstance;
import static com.acroteq.ticketing.test.extension.DockerNetworkSingleton.getNetworkName;

import com.acroteq.ticketing.test.extension.HostNameSetter;
import com.acroteq.ticketing.test.extension.OutputFrameLogger;
import dasniko.testcontainers.keycloak.KeycloakContainer;

public class KeycloakIamContainer extends KeycloakContainer {

  private static final String KEYCLOAK_DOCKER_IMAGE = "keycloak/keycloak:21.1.1";

  private static final String KEYCLOAK_CONTAINER_NAME = "Keycloak";
  private static final String ENV_KC_HOSTNAME_STRICT_BACKCHANNEL = "KC_HOSTNAME_STRICT_BACKCHANNEL";
  private static final String ENV_KC_HOSTNAME = "KC_HOSTNAME";
  private static final String ENV_KC_HOSTNAME_PORT = "KC_HOSTNAME_PORT";

  public static KeycloakIamContainer startKeycloakContainer() {
    final KeycloakIamContainer keycloakContainer = new KeycloakIamContainer();
    keycloakContainer.start();
    return keycloakContainer;
  }

  public KeycloakIamContainer() {
    this(KEYCLOAK_DOCKER_IMAGE);
  }

  @SuppressWarnings("resource")
  private KeycloakIamContainer(final String dockerImageName) {
    super(dockerImageName);

    final OutputFrameLogger logConsumer = new OutputFrameLogger(KEYCLOAK_CONTAINER_NAME);
    final HostNameSetter hostNameSetter = new HostNameSetter(KEYCLOAK_CONTAINER_NAME);

    withRealmImportFile("acroteq-realm.json");
    withLogConsumer(logConsumer);
    withCreateContainerCmdModifier(hostNameSetter);
    withExposedPorts(8080);
    withAdminUsername("admin");
    withAdminPassword("admin");
    withNetwork(getNetworkInstance());
    withNetworkAliases(getNetworkName());
    withEnv(ENV_KC_HOSTNAME_STRICT_BACKCHANNEL, "true");
    withEnv(ENV_KC_HOSTNAME, hostNameSetter.getHostName());
    withEnv(ENV_KC_HOSTNAME_PORT, "8080");
  }
}
