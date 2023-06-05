package com.acroteq.ticketing.test.e2e.extension;

import com.acroteq.ticketing.test.container.KafkaSslContainer;
import com.acroteq.ticketing.test.container.ZookeeperContainer;
import lombok.Getter;

import java.util.List;

@Getter
public class KafkaClusterContainers {

  private final ZookeeperContainer zookeeperContainer = new ZookeeperContainer();

  private final KafkaSslContainer kafkaSslContainer1 = new KafkaSslContainer(1, 3);
  private final KafkaSslContainer kafkaSslContainer2 = new KafkaSslContainer(2, 3);
  private final KafkaSslContainer kafkaSslContainer3 = new KafkaSslContainer(3, 3);

  void start() {
    zookeeperContainer.start();
    final String zookeeperUrl = zookeeperContainer.getInternalConnectUrl();

    kafkaSslContainer1.withExternalZookeeper(zookeeperUrl)
                      .dependsOn(zookeeperContainer)
                      .start();

    kafkaSslContainer2.withExternalZookeeper(zookeeperUrl)
                      .dependsOn(zookeeperContainer)
                      .start();

    kafkaSslContainer3.withExternalZookeeper(zookeeperUrl)
                      .dependsOn(zookeeperContainer)
                      .start();
  }

  void stop() {
    kafkaSslContainer1.stop();
    kafkaSslContainer2.stop();
    kafkaSslContainer3.stop();
    zookeeperContainer.stop();
  }

  public List<KafkaSslContainer> getKafkaContainers() {
    return List.of(kafkaSslContainer1, kafkaSslContainer2, kafkaSslContainer3);
  }
}
