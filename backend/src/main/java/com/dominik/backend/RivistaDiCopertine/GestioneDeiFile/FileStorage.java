package com.dominik.backend.RivistaDiCopertine.GestioneDeiFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorage {
    private final Path rootLocation;

    //funkcja pomocnicza
    public long nextFreeId(){
        try {
            long max = 0;
            for(Path p : Files.walk(this.rootLocation, 1).map(this.rootLocation::relativize).toList()){
                String name = p.getFileName().toString();
                if(!name.endsWith(".png"))
                    continue;

                name = name.split("\\.")[0];
                try{
                    long l = Long.parseLong(name);
                    if(l>max)
                        max = l;
                } catch (NumberFormatException e) {
                    continue;
                }

            }
            return max+1;
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    };
    public FileStorage(String path){
        rootLocation = Path.of(path);
        new File(path).mkdirs();
    }

    Path getLocation(Long id){
        return this.rootLocation.resolve(id+".png")
                .normalize().toAbsolutePath();
    }
    public void store(MultipartFile file,Long id){
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = getLocation(id);
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    };
    Resource fallbackImage(){
        Path path = this.rootLocation.resolve("fallback.png");

        return loadResource(path);
    }

    public Resource loadAsResource(Long id){
        Resource r = loadResource(getLocation(id));
        if(r==null){
            r = fallbackImage();
            if (r==null)
                throw new StorageException("missing fallback.png");
        }
        return r;
    };

    public boolean exists(Long id){
        try{
            Resource resource = new UrlResource(getLocation(id).toUri());
            return resource.exists();
        }catch (MalformedURLException e) {
            throw new StorageException("kinda bullshit czy cos",e);
        }

    }

    private Resource loadResource(Path file){
        try {
            Resource resource = new UrlResource(file.toUri());
            if(!resource.exists()){
                return null;
            }
            if (resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException(
                        "Could not read file: " + file);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + file, e);
        }
    }

    public void deleteAll(){//nie dotykać
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    };
}
