package io.fabric8.demo;

import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import org.junit.Test;

import static io.fabric8.kubernetes.assertions.Assertions.assertThat;

import java.util.Collections;

public class ServiceAssertionTest {
    @Test
    public void testService() {
        // Given
        Service service = getServiceObj();

        // When + Then
        assertThat(service).metadata().name().isEqualTo("my-service");
        assertThat(service).spec().selector().isNotEmpty();
        assertThat(service).spec().selector().containsKey("app");
        assertThat(service).spec().ports().hasSize(1);
        assertThat(service).spec().ports().item(0).hasPort(80);
    }

    private Service getServiceObj() {
        return new ServiceBuilder()
                .withNewMetadata().withName("my-service").endMetadata()
                .withNewSpec()
                  .withSelector(Collections.singletonMap("app", "MyApp"))
                  .addNewPort()
                     .withProtocol("TCP")
                     .withPort(80)
                     .withTargetPort(new IntOrString("9376"))
                  .endPort()
                .endSpec()
                .build();
    }
}
