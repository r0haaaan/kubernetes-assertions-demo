package io.fabric8.demo;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Collections;

import static io.fabric8.kubernetes.assertions.Assertions.assertThat;

public class DeploymentAssertionTest {

    @Test
    public void testDeployment() {
        // Given
        Deployment deployment = getDeploymentObj();

        // When + Then
        assertThat(deployment).metadata().name().isEqualTo("nginx-deployment");
        assertThat(deployment).metadata().labels().contains(new AbstractMap.SimpleEntry<>("app", "nginx"));
        assertThat(deployment).spec().hasReplicas(1);
        assertThat(deployment).spec().selector().matchLabels().contains(new AbstractMap.SimpleEntry<>("app", "nginx"));
        assertThat(deployment).spec().template().metadata().labels().contains(new AbstractMap.SimpleEntry<>("app", "nginx"));
        assertThat(deployment).spec().template().spec().containers().hasSize(1);
        assertThat(deployment).spec().template().spec().containers().item(0).name().isEqualTo("nginx");
        assertThat(deployment).spec().template().spec().containers().item(0).image().isEqualTo("nginx:1.7.9");
        assertThat(deployment).spec().template().spec().containers().item(0).ports().item(0).hasContainerPort(80);
    }

    private Deployment getDeploymentObj() {
       return new DeploymentBuilder()
               .withNewMetadata()
               .withName("nginx-deployment")
               .withLabels(Collections.singletonMap("app", "nginx"))
               .endMetadata()
               .withNewSpec()
               .withReplicas(1)
               .withNewSelector()
               .withMatchLabels(Collections.singletonMap("app", "nginx"))
               .endSelector()
               .withNewTemplate()
               .withNewMetadata()
               .withLabels(Collections.singletonMap("app", "nginx"))
               .endMetadata()
               .withNewSpec()
               .addNewContainer()
               .withName("nginx")
               .withImage("nginx:1.7.9")
               .addNewPort()
               .withContainerPort(80)
               .endPort()
               .endContainer()
               .endSpec()
               .endTemplate()
               .endSpec()
               .build();
    }
}
