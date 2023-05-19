package de.fhswf.genericapplication.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class UploadFile {

    @Column
    private String name;

    @Column
    private String contentType;

    @Lob
    private byte[] data;

    public UploadFile() {}

    public UploadFile(String name, String contentType, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
