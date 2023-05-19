package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.dto.FileContext;
import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.models.UploadFile;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.BaseEntityService;
import de.fhswf.genericapplication.services.FileService;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * Default implementation of {@link FileService} for operations on files.
 *
 * @author Kevin Link
 */
@Service
public class DefaultFileService implements FileService {

    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private BaseEntityService baseEntityService;

    @Override
    public String getFileUrl(Long typeId, Long id, String propertyName) {
        return String.format("/api/storage?typeId=%S&id=%S&propertyName=%s", typeId, id, propertyName);
    }

    @Override
    public UploadFile getFile(FileContext context) throws ModelNotFoundException {
        BaseEntity baseEntity = baseEntityService.getBaseEntity(context.typeId(), context.id());
        PropertyAccessor entityPropertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(baseEntity);
        return (UploadFile) entityPropertyAccessor.getPropertyValue(context.propertyName());
    }

    @Override
    public void uploadFile(FileContext context, MultipartFile file) throws ModelNotFoundException, IOException {
        BaseEntity baseEntity = baseEntityService.getBaseEntity(context.typeId(), context.id());

        PropertyAccessor entityPropertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(baseEntity);
        entityPropertyAccessor.setPropertyValue(context.propertyName(), populateUploadFile(file));

        genericEntityRepository.update(baseEntity);
    }

    @Override
    public void deleteFile(FileContext context) throws ModelNotFoundException {
        BaseEntity baseEntity = baseEntityService.getBaseEntity(context.typeId(), context.id());
        PropertyAccessor entityPropertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(baseEntity);
        entityPropertyAccessor.setPropertyValue(context.propertyName(), null);

        genericEntityRepository.update(baseEntity);
    }

    /**
     * Populates the values of a given {@link MultipartFile} to an {@link UploadFile} object.
     *
     * @param file Source file
     * @return Populated {@link UploadFile}
     * @throws IOException if the {@link MultipartFile} can't be read
     */
    private UploadFile populateUploadFile(MultipartFile file) throws IOException {
        return new UploadFile(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())), file.getContentType(), file.getBytes());
    }
}
