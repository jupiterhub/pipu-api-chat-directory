# pipu-api-chat-directory
Lookup service for all persistent client-server connections used by `pipu-api-chat`.

## Dependencies
* redis at `:6379`, quarkus uses dev. Only for production  

### Dev mode
```shell script
quarkus dev
```

### Build for JVM mode
```shell script
quarkus build
```

### Build for native mode
Note:
Native uses `UPX` for compression. A higher value means slower build time but lower disk size.
```properties
# compression level can be set as from 1-10
quarkus.native.compression.level=10
```
```shell script
quarkus build --native --no-tests -Dquarkus.native.container-build=true
```

run via: `./build/pipu-api-chat-directory-1.0.0-SNAPSHOT-runner`

### Build container
Build the native file first (command above) before running this command 
```shell script
docker build -f src/main/docker/Dockerfile.native-micro -t walkingjupiter/pipu-api-chat-directory:latest .
```