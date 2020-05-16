package io.fabric8.demo;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.fabric8.kubernetes.assertions.Assertions.assertThat;

public class PodAssertionTest {
    @Test
    public void testPod() {
        // Given
        Pod pod = getPodObject();

        // When + Then
        assertThat(pod).metadata().name().isEqualTo("testpod");
        assertThat(pod).metadata().labels().containsKey("app");
        assertThat(pod).metadata().labels().containsKey("role");
        assertThat(pod).spec().containers().hasSize(1);
        assertThat(pod).spec().containers().item(0).name().isEqualTo("website");
        assertThat(pod).spec().containers().item(0).image().isEqualTo("nginx:1.7.9");
        assertThat(pod).spec().containers().item(0).ports().item(0).hasContainerPort(80);
    }

    private Pod getPodObject() {
        Map<String, String> podLabels = new HashMap<>();
        podLabels.put("app", "website");
        podLabels.put("role", "frontend");

        return new PodBuilder()
                .withNewMetadata()
                  .withName("testpod")
                  .withLabels(podLabels)
                .endMetadata()
                .withNewSpec()
                  .addNewContainer()
                     .withName("website")
                     .withImage("nginx:1.7.9")
                     .addNewPort().withContainerPort(80).endPort()
                  .endContainer()
                .endSpec()
                .build();
    }
}
