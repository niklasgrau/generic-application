package de.fhswf.genericapplication.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhswf.genericapplication.models.Metadata;
import de.fhswf.genericapplication.models.core.PDApplication;
import de.fhswf.genericapplication.repositories.MetadataRepository;
import de.fhswf.genericapplication.services.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Default implementation of {@link MetadataService} for CRUD operations on {@link Metadata}.
 *
 * @author Kevin Link
 */
@Service
public class DefaultMetadataService implements MetadataService {
    @Autowired
    private MetadataRepository metadataRepository;

    @Override
    public Metadata createMetadata(byte[] xmlData)
            throws ParserConfigurationException, IOException, TransformerException, SAXException {
        Document xmlDocument = getXmlDocumentFromByteArray(xmlData);
        String version = xmlDocument.getDocumentElement().getAttribute("version");
        Metadata metadata = new Metadata(
                Long.valueOf(version),
                getXmlStringFromDocument(xmlDocument).getBytes(StandardCharsets.UTF_8)
        );

        return metadataRepository.save(metadata);
    }

    @Override
    public Optional<String> getMetadataByVersion(Long version) throws IOException, JAXBException {
        Optional<Metadata> metadata = metadataRepository.findByVersion(version);
        if (metadata.isEmpty()) return Optional.empty();

        return Optional.ofNullable(getMetadataAsJsonString(metadata.get()));
    }

    @Override
    public Optional<String> getNewestMetadata() throws IOException, JAXBException {

        Optional<Metadata> metadata = metadataRepository.findFirstByOrderByCreatedAtDesc();
        if (metadata.isEmpty()) return Optional.empty();

        return Optional.ofNullable(getMetadataAsJsonString(metadata.get()));
    }

    @Override
    public void deleteMetadata(Long version) {
        metadataRepository.deleteByVersion(version);
    }

    private Document getXmlDocumentFromByteArray(byte[] xmlBytes)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xmlBytes));
    }

    @Override
    public boolean isNewest(Long version) {
        Optional<Metadata> metaFromVersion = metadataRepository.findByVersion(version);
        Optional<Metadata> newestMeta = metadataRepository.findFirstByOrderByCreatedAtDesc();
        return newestMeta.equals(metaFromVersion);
    }

    private Document getXmlDocumentFromMetadata(Metadata metadata)
            throws ParserConfigurationException, IOException, SAXException {
        return getXmlDocumentFromByteArray(metadata.getData());
    }

    private String getXmlStringFromDocument(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Writer out = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(out));
        return out.toString();
    }

    private String getMetadataAsJsonString(Metadata metadata) throws JAXBException, JsonProcessingException {
        if (metadata.getData() == null) return "{}";

        // Unmarshal XML to object structure
        JAXBContext jaxbContext = JAXBContext.newInstance(PDApplication.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        String metadataString = new String(metadata.getData());
        StringReader stringReader = new StringReader(metadataString);
        PDApplication application = (PDApplication) unmarshaller.unmarshal(stringReader);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(application);
    }
}
