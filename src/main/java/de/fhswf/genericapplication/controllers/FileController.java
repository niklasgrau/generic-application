package de.fhswf.genericapplication.controllers;

import de.fhswf.genericapplication.dto.FileContext;
import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.exceptions.ModelSavingException;
import de.fhswf.genericapplication.exceptions.PropertyNotFoundException;
import de.fhswf.genericapplication.models.UploadFile;
import de.fhswf.genericapplication.services.FileService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller to provide rest api for file attributes of generic entities.
 *
 * @author Kevin Link
 */
@Controller
@RequestMapping("/api/storage")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam Long typeId,
                                         @RequestParam Long id,
                                         @RequestParam String propertyName,
                                         @RequestParam MultipartFile file) {
        final FileContext fileContext = new FileContext(typeId, id, propertyName);

        try {
            fileService.uploadFile(fileContext, file);
        } catch (ModelNotFoundException | IOException e) {
            throw new ModelSavingException("Failed to save file to " + fileContext, e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                .body(fileService.getFileUrl(typeId, id, propertyName));
    }

    @GetMapping
    public ResponseEntity<byte[]> get(@RequestParam Long typeId,
                                      @RequestParam Long id,
                                      @RequestParam String propertyName,
                                      @RequestParam(required = false) boolean download) {
        final FileContext fileContext = new FileContext(typeId, id, propertyName);
        final UploadFile file;

        try {
            file = fileService.getFile(fileContext);
        } catch (BeansException ex) {
            throw new PropertyNotFoundException(fileContext.propertyName(), ex);
        }

        if (file == null) {
            return ResponseEntity.noContent().build();
        }

        final String content = ((download) ? "attachment" : "") + "; filename=\"" + file.getName() + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, content)
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                .body(file.getData());
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long typeId,
                                         @RequestParam Long id,
                                         @RequestParam String propertyName) {
        final FileContext fileContext = new FileContext(typeId, id, propertyName);
        fileService.deleteFile(fileContext);

        return ResponseEntity.noContent().build();
    }

}
