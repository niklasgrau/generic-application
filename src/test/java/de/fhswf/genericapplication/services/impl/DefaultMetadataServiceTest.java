package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.models.Metadata;
import de.fhswf.genericapplication.repositories.MetadataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultMetadataServiceTest {

    @InjectMocks
    DefaultMetadataService underTest;

    @Mock
    MetadataRepository metadataRepository;

    @Test
    public void when_MetadataExists_Then_ReturnMetadata() throws JAXBException, IOException {
        // Arrange
        Metadata metadata = mock(Metadata.class);
        when(metadataRepository.findFirstByOrderByCreatedAtDesc()).thenReturn(Optional.of(metadata));

        // Act
        Optional<String> newestMetadata = underTest.getNewestMetadata();

        // Assert
        assertTrue(newestMetadata.isPresent());
        assertEquals("{}", newestMetadata.get());
        verify(metadataRepository).findFirstByOrderByCreatedAtDesc();
    }

    @Test
    public void when_NoMetadataExists_Then_ReturnEmpty() throws JAXBException, IOException {
        // Arrange
        when(metadataRepository.findFirstByOrderByCreatedAtDesc()).thenReturn(Optional.empty());

        // Act
        Optional<String> newestMetadata = underTest.getNewestMetadata();

        // Assert
        assertTrue(newestMetadata.isEmpty());
        verify(metadataRepository).findFirstByOrderByCreatedAtDesc();
    }

    @Test
    public void when_IsNewest_Then_ReturnTrue() {
        // Arrange
        Long version = 123456L;
        when(metadataRepository.findFirstByOrderByCreatedAtDesc()).thenReturn(Optional.empty());
        when(metadataRepository.findByVersion(version)).thenReturn(Optional.empty());

        // Act
        boolean newest = underTest.isNewest(version);

        // Assert
        assertTrue(newest);
        verify(metadataRepository).findFirstByOrderByCreatedAtDesc();
        verify(metadataRepository).findByVersion(version);
    }

    @Test
    public void when_IsNotNewest_Then_ReturnFalse() {
        // Arrange
        Long version = 123456L;
        Metadata metadata = mock(Metadata.class);
        when(metadataRepository.findFirstByOrderByCreatedAtDesc()).thenReturn(Optional.of(metadata));
        when(metadataRepository.findByVersion(version)).thenReturn(Optional.empty());

        // Act
        boolean newest = underTest.isNewest(version);

        // Assert
        assertFalse(newest);
        verify(metadataRepository).findFirstByOrderByCreatedAtDesc();
        verify(metadataRepository).findByVersion(version);
    }

    @Test
    public void when_DocumentGiven_Then_SaveMetadata() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        // Arrange
        Metadata metadata = mock(Metadata.class);
        when(metadata.getVersion()).thenReturn(1L);
        when(metadataRepository.save(any())).thenReturn(metadata);
        String xml = "<core:application version=\"1\"></core:application>";

        // Act
        Metadata createdMetadata = underTest.createMetadata(xml.getBytes(StandardCharsets.UTF_8));

        // Assert
        assertEquals(1, createdMetadata.getVersion());
        verify(metadataRepository).save(any(Metadata.class));
    }

    @Test
    public void when_versionGiven_Then_DeleteMetadata() {
        // Arrange
        Long version = 123456L;

        // Act
        underTest.deleteMetadata(version);

        // Assert
        verify(metadataRepository).deleteByVersion(version);
    }

}