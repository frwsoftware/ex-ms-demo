package com.frwsoftware.exmsdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class ResourceService {
    @Autowired
    private ResourceLoader resourceLoader;

    public String readFileToString(String path) {
        //ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        return asString(resource);
    }

    public void example() throws IOException {
        Resource classPathResource = resourceLoader.getResource("classpath:file.txt");
        Resource urlResource = resourceLoader.getResource("http://example.com/file.txt");
        Resource fileSystemResource = resourceLoader.getResource("file:/path/to/file.txt");
        Resource servletContextResource = resourceLoader.getResource("/WEB-INF/file.txt");
        File file = classPathResource.getFile();
        String content = new String(Files.readAllBytes(file.toPath()));
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}


