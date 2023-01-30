package org.jupiterhub.pipu.chatdir.resource;

import io.smallrye.mutiny.Uni;
import org.jupiterhub.pipu.chatdir.record.Directory;
import org.jupiterhub.pipu.chatdir.service.DirectoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Optional;

@Path("/directory")
public class DirectoryResource {

    @Inject
    private DirectoryService directoryService;

    @GET
    public Uni<List<String>> directoryList() {
        return directoryService.keys();
    }

    @POST
    public Directory register(Directory directory) {
        directoryService.set(directory.userId(), directory);
        return directory;
    }

    @GET
    @Path("/{key}")
    public String lookup(String key) {
        Optional<Directory> directory = directoryService.get(key);
        if (directory.isPresent()) {
            return directory.get().connString();
        }

        throw new NotFoundException("Key [" + key + "] does not exist");
    }

    @PUT
    @Path("/{key}")
    public Directory register(String key, Directory directory) {
        Directory withKey = new Directory(key, directory.connString());
        directoryService.set(key, withKey);
        return withKey;
    }

    @DELETE
    @Path("/{key}")
    public Uni<Void> delete(String key) {
        return directoryService.del(key);
    }
}