package de.fhswf.genericapplication.services;

import de.fhswf.genericapplication.models.Metadata;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Optional;

/**
 * Service for Metadata related operations.
 *
 * @author Link, Kevin
 */
public interface MetadataService {
    /**
     * Creates a new Metadata entity based of the given {@code xmlDocument}.
     *
     * @param xmlData Content of the XML representing the Metadata
     * @return Returns the new Metadata
     */
    Metadata createMetadata(byte[] xmlData)
            throws ParserConfigurationException, IOException, TransformerException, SAXException;

    /**
     * Returns the Metadata as XML-String, if available.
     *
     * @param version Unique version to find the Metadata to delete
     * @return Optional of the Metadata XML-String.
     */
    Optional<String> getMetadataByVersion(Long version) throws IOException, JAXBException;

    /**
     * Returns the newest Metadata as XML-String, if available.
     *
     * @return Optional of the newest Metadata XML-String.
     */
    Optional<String> getNewestMetadata()
            throws ParserConfigurationException, IOException, SAXException, TransformerException, JAXBException;

    /**
     * Deletes the Metadata related to the given version.
     *
     * @param version Unique version to find the Metadata to delete
     */
    void deleteMetadata(Long version);

    /**
     * Checks if the Metadata, related to the given {@code version}, is equal to the newest available Metadata.
     *
     * @param version Unique version to compare with.
     * @return {@code True} if the version related Metadata is the newest or {@code false} if not
     */
    boolean isNewest(Long version);
}
