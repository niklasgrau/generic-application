package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.dto.FileContext;
import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.Offer;
import de.fhswf.genericapplication.models.UploadFile;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.BaseEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultFileServiceTest {

    @InjectMocks
    DefaultFileService underTest;

    @Mock
    GenericEntityRepository genericEntityRepository;

    @Mock
    BaseEntityService baseEntityService;

    @Test
    public void when_AllParamsGiven_Then_ReturnUrl() {
        // Arrange
        Long typeId = 123456L;
        Long id = 1L;
        String propertyName = "fileProp";

        // Act
        String fileUrl = underTest.getFileUrl(typeId, id, propertyName);

        // Assert
        assertNotNull(fileUrl);
        assertEquals("/api/storage?typeId=" + typeId + "&id=" + id + "&propertyName=" + propertyName, fileUrl);
    }

    @Test
    public void when_FileExists_Then_ReturnFile() throws ModelNotFoundException {
        // Arrange
        Long typeId = 123456L;
        Long id = 1L;
        String propertyName = "file";

        FileContext fileContext = new FileContext(typeId, id, propertyName);

        Offer offer = mock(Offer.class);
        when(offer.getFile()).thenReturn(mock(UploadFile.class));
        doReturn(offer).when(baseEntityService).getBaseEntity(typeId, id);

        // Act
        UploadFile file = underTest.getFile(fileContext);

        // Assert
        assertNotNull(file);
        verify(baseEntityService).getBaseEntity(typeId, id);
    }

    @Test
    public void when_FileGiven_Then_UploadFile() throws ModelNotFoundException, IOException {
        // Arrange
        Long typeId = 123456L;
        Long id = 1L;
        String propertyName = "file";

        FileContext fileContext = new FileContext(typeId, id, propertyName);

        Offer offer = mock(Offer.class);
        doReturn(offer).when(baseEntityService).getBaseEntity(typeId, id);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        // Act
        underTest.uploadFile(fileContext, file);

        // Assert
        verify(baseEntityService).getBaseEntity(typeId, id);
        verify(genericEntityRepository).update(offer);
    }

    @Test
    public void when_FileExist_Then_DeleteFile() throws ModelNotFoundException {
        // Arrange
        Long typeId = 123456L;
        Long id = 1L;
        String propertyName = "file";

        FileContext fileContext = new FileContext(typeId, id, propertyName);

        Offer offer = mock(Offer.class);
        doReturn(offer).when(baseEntityService).getBaseEntity(typeId, id);

        // Act
        underTest.deleteFile(fileContext);

        // Assert
        verify(baseEntityService).getBaseEntity(typeId, id);
        verify(genericEntityRepository).update(offer);
    }
}