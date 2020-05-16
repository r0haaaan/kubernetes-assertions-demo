package io.fabric8.demo;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.junit.Ignore;
import org.junit.Test;

import static io.fabric8.kubernetes.assertions.Assertions.assertThat;

@Ignore
public class AssertWithKubernetesClientTest {
    @Test
    public void testAssertWithClientOnRealK8s() {
        // Given
        final KubernetesClient client = new DefaultKubernetesClient();

        // When + Then
        assertThat(client).pods().runningStatus().filterNamespace("default").hasSize(1);
    }

    @Test
    public void testAssertWithClientOnRealK8s2() {
        // Given
        final KubernetesClient client = new DefaultKubernetesClient();

        // When + Then
        assertThat(client).pods().runningStatus().filterNamespace("default").filterName("helloworld").logs().containsText("hello-world");
    }

    @Test
    public void testAssertWithClientOnRealK8s3() {
        // Given
        final KubernetesClient client = new DefaultKubernetesClient();

        assertThat(client).namespace("default").deployment("nginx-deployment").pods().isPodReadyForPeriod(1000L, 1000L);
    }
}
