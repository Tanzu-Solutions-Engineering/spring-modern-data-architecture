/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.creational;

import com.rabbitmq.stream.EnvironmentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitStreamEnvCreateDirectorTest {

    @Mock
    private Environment springEnv;
    @Mock
    private EnvironmentBuilder rabbitEnvBuilder;

    private String vCapServices = """
                            {
                                "p.rabbitmq":[
                                {"label":"p.rabbitmq","provider":null,"plan":"single-node","name":"retail-rabbitmq","tags":["rabbitmq"],
                                "instance_guid":"26a342cd-a5f6-4a88-9118-db9dc48ff2dd","instance_name":"retail-rabbitmq","binding_guid":"6fd409ac-50b8-4afe-9095-d8f6bba4d445",
                                "binding_name":null,
                                "credentials":{"dashboard_url":"https://rmq-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.sys.int.tas-labs.com","hostname":"q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh","hostnames":["q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh"],"http_api_uri":"https://6fd409ac-50b8-4afe-9095-d8f6bba4d445:F1wBGH7OmL3tSSB_6R3tQr0h@rmq-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.sys.int.tas-labs.com/api/","http_api_uris":["https://6fd409ac-50b8-4afe-9095-d8f6bba4d445:F1wBGH7OmL3tSSB_6R3tQr0h@rmq-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.sys.int.tas-labs.com/api/"],"password":"F1wBGH7OmL3tSSB_6R3tQr0h","protocols":{"amqp":{"host":"q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh","hosts":["q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh"],"password":"F1wBGH7OmL3tSSB_6R3tQr0h","port":5672,"ssl":false,"uri":"amqp://6fd409ac-50b8-4afe-9095-d8f6bba4d445:F1wBGH7OmL3tSSB_6R3tQr0h@q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh/26a342cd-a5f6-4a88-9118-db9dc48ff2dd","uris":["amqp://6fd409ac-50b8-4afe-9095-d8f6bba4d445:F1wBGH7OmL3tSSB_6R3tQr0h@q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh/26a342cd-a5f6-4a88-9118-db9dc48ff2dd"],"username":"6fd409ac-50b8-4afe-9095-d8f6bba4d445","vhost":"26a342cd-a5f6-4a88-9118-db9dc48ff2dd"}},"ssl":false,"uri":"amqp://6fd409ac-50b8-4afe-9095-d8f6bba4d445:F1wBGH7OmL3tSSB_6R3tQr0h@q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh/26a342cd-a5f6-4a88-9118-db9dc48ff2dd","uris":["amqp://6fd409ac-50b8-4afe-9095-d8f6bba4d445:F1wBGH7OmL3tSSB_6R3tQr0h@q-s0.rabbitmq-server.vmnetwork1080.service-instance-26a342cd-a5f6-4a88-9118-db9dc48ff2dd.bosh/26a342cd-a5f6-4a88-9118-db9dc48ff2dd"],"username":"6fd409ac-50b8-4afe-9095-d8f6bba4d445","vhost":"26a342cd-a5f6-4a88-9118-db9dc48ff2dd"},"syslog_drain_url":null,"volume_mounts":[]}],"p.mysql":[{"label":"p.mysql","provider":null,"plan":"db-small","name":"retail-mysql","tags":["mysql"],"instance_guid":"0f771275-d97d-41e6-b459-f23af33b4aee","instance_name":"retail-mysql","binding_guid":"9f8e0211-56db-4569-a4c6-03031b19a516","binding_name":null,"credentials":{"hostname":"0f771275-d97d-41e6-b459-f23af33b4aee.mysql.service.internal","jdbcUrl":"jdbc:mysql://0f771275-d97d-41e6-b459-f23af33b4aee.mysql.service.internal:3306/service_instance_db?user=9f8e021156db4569a4c603031b19a516&password=0e3kwqruvzubscmp&sslMode=VERIFY_IDENTITY&useSSL=true&requireSSL=true&enabledTLSProtocols=TLSv1.2&serverSslCert=/etc/ssl/certs/ca-certificates.crt","name":"service_instance_db","password":"0e3kwqruvzubscmp","port":3306,"tls":{"cert":{"ca":"-----BEGIN CERTIFICATE-----\\\\nMIIDRzCCAi+gAwIBAgIUV4yPKcTA2rM6BbDApw9vyNTEZDswDQYJKoZIhvcNAQEL\\\\nBQAwMzEfMB0GA1UEAxMWb3BzbWdyLXNlcnZpY2VzLXRscy1jYTEQMA4GA1UEChMH\\\\nUGl2b3RhbDAeFw0yMjEyMDUyMTAxNDZaFw0yNzEyMDQyMTAxNDZaMDMxHzAdBgNV\\\\nBAMTFm9wc21nci1zZXJ2aWNlcy10bHMtY2ExEDAOBgNVBAoTB1Bpdm90YWwwggEi\\\\nMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDYL33uffLVZI1cAlOXL2xYn7cV\\\\nLnLCtGpsKDwybibPcL0uwJJwAIxJWsfQSu/zJ6QsFsJZ8GC+a6bAzPPvfnIz7Yfe\\\\ndEB5dEoBplSWgFf57xnzptN40/Hs4YAR0UrjPtAojSG8JPNpwE4/Eg+07bzadkEr\\\\nAKwzqmjIkoMu5mL+Psx38X3WStPKfzpVThhEqZroJcmt+kGHS/nBnuqRHo/0ZMMh\\\\nRYmnIiiQevOCKo6XcKl/Q7qE089HyNt/5c0KSjkUfA/gqhw5PNug+BnlbX8FCYyB\\\\nknD7RxjGsan4loLoMG7coT/n1JL5AyQOzXNPla4TdBzFTVsAyT3nDqbOMugdAgMB\\\\nAAGjUzBRMB0GA1UdDgQWBBTih5eVlSe0vlO+XEECqD0YeAwhEzAfBgNVHSMEGDAW\\\\ngBTih5eVlSe0vlO+XEECqD0YeAwhEzAPBgNVHRMBAf8EBTADAQH/MA0GCSqGSIb3\\\\nDQEBCwUAA4IBAQAQeVH0p8XGgFfyPMZPbhzYrhr81OlflnBAQKZW5/oP2J9Vu34v\\\\n/WHQpCzuMVEI8crBaVT2ECcNUf1LePDnozn6qtvkmVLlVC9BsdGIYGEJsFn3Ahvb\\\\nEySd6zXL9WDdABC+IzbZRa6NEhHEGD1FBbDIFMu2vjLlpcaElsp7T+Dy4eNLdL9h\\\\n71ag6GP4ha4NL1IxaJyjiNHTj49kff4hYP4a8gqNUVuMAOt1SeBTrlEE01XOvvIx\\\\nmD9ymePltPkZiY1pqRvHm6Nlb+bETJTmeDjXByV2K15kGmosNvrjiJqj8ZHMIXFf\\\\n/vmf64enAgB4D9JG9m0X2SbPQkfoqM+XMV4Y\\\\n-----END CERTIFICATE-----\\\\n"}},"uri":"mysql://9f8e021156db4569a4c603031b19a516:0e3kwqruvzubscmp@0f771275-d97d-41e6-b459-f23af33b4aee.mysql.service.internal:3306/service_instance_db?reconnect=true","username":"9f8e021156db4569a4c603031b19a516"},"syslog_drain_url":null,"volume_mounts":[]}]
                                }
            """;
    private RabbitStreamEnvCreateDirector subject;


    @BeforeEach
    void setUp() {
        subject = new RabbitStreamEnvCreateDirector();
    }

    @DisplayName("GIVEN Spring & RabbitMQ Environment and when construct THEN set credentails ")
    @Test
    void createEnv() {

//        when(springEnv.getProperty(anyString())).thenReturn(vCapServices);
//
//        subject.construct(springEnv,rabbitEnvBuilder);
//
//        verify(rabbitEnvBuilder).host(anyString());
//        verify(rabbitEnvBuilder).username(anyString());
//        verify(rabbitEnvBuilder).password(anyString());
//        verify(rabbitEnvBuilder).virtualHost(anyString());

    }
}