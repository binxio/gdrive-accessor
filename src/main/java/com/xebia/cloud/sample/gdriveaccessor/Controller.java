package com.xebia.cloud.sample.gdriveaccessor;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.ImmutableSet;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
  private Drive service;

  @Value("${gdrive.applicationName}") private String applicationName;

  @GetMapping("/")
  public List<File> root() throws IOException {
    FileList result = this.service.files()
                          .list()
                          .setPageSize(10)
                          .setSupportsAllDrives(true)
                          .setIncludeItemsFromAllDrives(true)
                          .setFields("nextPageToken, files(id, name)")
                          .execute();

    return result.getFiles();
  }

  @PostConstruct
  public void init() throws IOException, GeneralSecurityException {
    GoogleCredentials credentials =
        GoogleCredentials.getApplicationDefault().createScoped(
            ImmutableSet.of("https://www.googleapis.com/auth/drive"));

    this.service = new Drive
                       .Builder(GoogleNetHttpTransport.newTrustedTransport(),
                                GsonFactory.getDefaultInstance(),
                                new HttpCredentialsAdapter(credentials))
                       .setApplicationName(this.applicationName)
                       .build();
  }
}
