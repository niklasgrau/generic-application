package de.fhswf.genericapplication.converters.impl;

import de.fhswf.genericapplication.converters.PropertyConverter;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.models.UploadFile;
import de.fhswf.genericapplication.services.BaseEntityService;
import de.fhswf.genericapplication.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter to convert a property of type {@link UploadFile} to an {@link String}
 * containing the URL to receive the given file via API request.
 *
 * @author Kevin Link
 */
@Component
public class UploadFilePropertyConverter implements PropertyConverter<UploadFile, String> {

    @Autowired
    private FileService fileService;

    @Override
    public String convert(UploadFile value, String key, BaseEntity entity) {
        return fileService.getFileUrl(BaseEntityService.getTypeIdFromBaseEntity(entity.getClass()), entity.getId(), key);
    }

    @Override
    public Class<UploadFile> supports() {
        return UploadFile.class;
    }
}
