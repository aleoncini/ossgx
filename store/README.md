# store project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

The module stores data into a Mongo DB instance

## Prepare the application - deploy Mongo DB instance

The application requires a mongo db instance, you can deploy such db locally or as a remote service, even a mix of both, for example you can have an instance running locally on your workstation to setup a local environment for development and another instance running as a service on a kubernetes platform or even more as an ATLAS instance running directly on Cloud for a test/production environment.
Following some suggestion to run it locally and on Openshift.

**deploy locally**
You can install and run a community version of mongo DB on your laptop, instructions depend on the underlying SO.
It's also possible to start it as a container, if you are using *podman* this is the command:
```
podman run --name mongodb -d -p 27017:27017 --rm mongo:3.6
```
it's a volatile deployment but for development can be sufficient.

**deploy on Openshift**
Create a project and add an application, choose *From Dockerfile* TAB, 

## Running the application in dev mode

[note] the java application looks for an environment variable called *MONGOCONNSTRING*

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `store-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/store-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/store-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.