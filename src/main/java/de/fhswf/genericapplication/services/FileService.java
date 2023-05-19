package de.fhswf.genericapplication.services;

import de.fhswf.genericapplication.dto.FileContext;
import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileService to handle files in the generic entity context.
 *
 * @author Link, Kevin
 */
public interface FileService {
    /**
     * Returns a URL, based on the given parameters, to retrieve the corresponding file data.
     *
     * @param typeId       Type of entity
     * @param id           ID of the entity
     * @param propertyName Name of the property holding the file data
     * @return URL to request the corresponding file
     */
    String getFileUrl(Long typeId, Long id, String propertyName);

    /**
     * Returns an {@link UploadFile} object, based on the properties of the given {@link FileContext}.
     *
     * @param context Context, containing all info to resolve the matching {@link UploadFile} object
     * @return {@link UploadFile} object from {@link FileContext}
     * @throws ModelNotFoundException if the {@link FileContext} can not be resolved to an existing entity class
     */
    UploadFile getFile(FileContext context) throws ModelNotFoundException;

    /**
     * Uploads a {@link MultipartFile} to an entity, resolved by the given {@link  FileContext}.
     *
     * @param context Context, containing all info to resolve the matching {@link UploadFile} object
     * @param file    File to be uploaded
     * @throws ModelNotFoundException if the {@link FileContext} can not be resolved to an existing entity class
     * @throws IOException            if the {@link MultipartFile} can not be read
     */
    void uploadFile(FileContext context, MultipartFile file) throws ModelNotFoundException, IOException;

    /**
     * Deletes the file represented by the {@link FileContext}.
     *
     * @param context Context, containting all info to resolve the matching {@link UploadFile} object
     * @throws ModelNotFoundException if the {@link FileContext} can not be resolved to an existing entity class
     */
    void deleteFile(FileContext context) throws ModelNotFoundException;
}
