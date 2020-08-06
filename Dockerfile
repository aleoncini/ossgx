FROM registry.redhat.io/ubi8/openjdk-11
COPY --chown=jboss:jboss ./README.md ./
RUN ls -lrt
