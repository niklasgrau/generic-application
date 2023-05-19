package de.fhswf.genericapplication.dto.requests;

import java.util.List;

/**
 * The DTO for read requests of an entity. The request can be used to get a single entity by its typeId and id or
 * a list of entities of typeId. You can specify which fields of an entity you want to return via propertySelection.
 * Additionally, you can filter and sort your list result. List results are paginated.
 *
 * @author Niklas Grau
 */
public class GetGenericEntityRequest extends Request {
    private Long typeId;

    private Long id;

    private List<String> propertySelection;

    private int page;

    private int pageSize;

    private FilterStatement filter;

    private SortStatement sort;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getPropertySelection() {
        return propertySelection;
    }

    public void setPropertySelection(List<String> propertySelection) {
        this.propertySelection = propertySelection;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public FilterStatement getFilter() {
        return filter;
    }

    public void setFilter(FilterStatement filter) {
        this.filter = filter;
    }

    public SortStatement getSort() {
        return sort;
    }

    public void setSort(SortStatement sort) {
        this.sort = sort;
    }
}
