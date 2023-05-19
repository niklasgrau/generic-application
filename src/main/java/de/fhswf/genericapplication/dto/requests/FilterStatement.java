package de.fhswf.genericapplication.dto.requests;

import java.util.List;

/**
 * The DTO for a filter statement. It contains a list of {@link FilterBlock}.
 * Each filter block consists of a filter {@link FilterBlockOperatorET} and a list of {@link FilterMember}.
 *
 * @author Niklas Grau
 */
public class FilterStatement {
    private List<FilterBlock> blocks;

    public List<FilterBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<FilterBlock> blocks) {
        this.blocks = blocks;
    }
}
