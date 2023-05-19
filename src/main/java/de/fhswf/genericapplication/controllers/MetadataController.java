package de.fhswf.genericapplication.controllers;

import de.fhswf.genericapplication.exceptions.ConfigurationException;
import de.fhswf.genericapplication.exceptions.MetadataException;
import de.fhswf.genericapplication.services.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Optional;

/**
 * Controller to provide rest api for metadata.
 *
 * @author Kevin Link
 */
@RestController
@RequestMapping("/api/metadata")
public class MetadataController {
    @Autowired
    private MetadataService metadataService;

    @PostMapping(consumes = {"application/octet-stream", "application/xml"})
    public ResponseEntity<Long> create(@RequestBody byte[] xmlBody) {
        try {
            metadataService.createMetadata(xmlBody);
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException("XML parser configuration was wrong.", e);
        } catch (IOException | TransformerException | SAXException e) {
            throw new MetadataException("Metadata could not be parsed or transformed.", e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<String> getNewestMeta() {
        try {
            Optional<String> metadata = metadataService.getNewestMetadata();
            if (metadata.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(metadata.get());
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException("XML parser configuration was wrong.", e);
        } catch (IOException | SAXException | TransformerException e) {
            throw new MetadataException("Metadata could not be parsed or transformed.", e);
        } catch (JAXBException e) {
            throw new MetadataException("Failed to parse Metadata with JAXB.", e);
        }
    }

    @GetMapping(value = {"/{version}"}, produces = "application/json")
    public ResponseEntity<String> get(@PathVariable Long version) {
        try {
            Optional<String> metadata = metadataService.getMetadataByVersion(version);
            if (metadata.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(metadata.get());
        } catch (IOException e) {
            throw new MetadataException("Metadata could not be parsed or transformed.", e);
        } catch (JAXBException e) {
            throw new MetadataException("Failed to parse Metadata with JAXB.", e);
        }
    }

    @DeleteMapping("/{version}")
    public ResponseEntity<?> delete(@PathVariable Long version) {
        metadataService.deleteMetadata(version);
        return ResponseEntity.noContent().build();
    }
}
