# pipu-api-chat-directory
Lookup service for all persistent client-server connections used by `pipu-api-chat`.

### Dev mode
```shell script
quarkus dev
```

### Build for JVM mode
```shell script
quarkus build
```

### Build for native mode
```shell script
quarkus build --native --no-tests -Dquarkus.native.container-build=true
```
Note:
Native uses `UPX` for compression. A higher value means slower build time but lower disk size.
```properties
# compression level can be set as from 1-10
quarkus.native.compression.level=10
```

run via: `./build/pipu-api-chat-directory-1.0.0-SNAPSHOT-runner`

### Build container
```shell script
docker build -f src/main/docker/Dockerfile.native-micro -t jupiterhub/pipu-api-chat-directory:1.0.0 .
```