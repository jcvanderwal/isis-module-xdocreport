package org.isisaddons.module.xdocreport.fixture.dom.templates;

import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.Resources;

/**
 * Created by jvanderwal on 18/10/2015.
 */
public enum Template {
    ORDER_ODT("OrderWithFreemarker.odt", "application/vnd.oasis.opendocument.text"),
    ORDER_DOCX("OrderWithFreemarker.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    private String fileName;
    private String mimeType;

    Template(final String fileName, final String mimeType) {
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public InputStream asInputStream() {
        try {
            return Resources.getResource(this.getClass(), getFileName()).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMimeType() {
        return mimeType;
    }
}
