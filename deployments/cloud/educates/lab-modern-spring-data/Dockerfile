# This Dockerfile is used where you need to create a custom workshop base
# image which incorporates additional application binaries or tools. Select
# the appropriate Educates workshop base image in the `FROM` line.

FROM ghcr.io/vmware-tanzu-labs/educates-base-environment:2.0

# Include here any steps to build your custom workshop base image. See the
# documentation on best practices for building a custom workshop base image.

# ...

# Uncomment the following if you wish to also copy workshop content files
# into the custom workshop base image, rather than workshop content files
# being published using a separate OCI image artefact or other mechanism.

COPY --chown=1001:0 ./workshop/data-services /home/eduk8s/data-services
# RUN mv /home/eduk8s/workshop /opt/workshop
# RUN fix-permissions /home/eduk8s
