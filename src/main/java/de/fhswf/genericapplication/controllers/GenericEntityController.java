package de.fhswf.genericapplication.controllers;

import de.fhswf.genericapplication.dto.GenericEntityDto;
import de.fhswf.genericapplication.dto.requests.CreateGenericEntityRequest;
import de.fhswf.genericapplication.dto.requests.GetGenericEntityRequest;
import de.fhswf.genericapplication.dto.requests.UpdateGenericEntityRequest;
import de.fhswf.genericapplication.services.impl.DefaultGenericEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to provide rest api for generic entities.
 *
 * @author Niklas Grau
 */
@RestController
@RequestMapping("/api/generic")
public class GenericEntityController {
    private static final int PAGE_SIZE = 10;

    @Autowired
    DefaultGenericEntityService genericEntityService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateGenericEntityRequest request) {
        GenericEntityDto resultDto =
                this.genericEntityService.create(request.getEntity(), request.getAssociationStates());

        return ResponseEntity.ok(resultDto);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id, @RequestBody GetGenericEntityRequest request) {
        GenericEntityDto resultDto =
                this.genericEntityService.get(request.getTypeId(), id, request.getPropertySelection());

        return ResponseEntity.ok(resultDto);
    }

    @PostMapping(value = "/list")
    public ResponseEntity<?> list(@RequestBody GetGenericEntityRequest request) {
        Pageable pagination = PageRequest.of(
                request.getPage() > 0 ? request.getPage() - 1 : request.getPage(),
                request.getPageSize() > 0 ? request.getPageSize() : PAGE_SIZE,
                this.getSortionByRequest(request)
        );

        Page<GenericEntityDto> paginatedResult = this.genericEntityService.list(
                request.getTypeId(),
                request.getPropertySelection(),
                pagination,
                request.getFilter()
        );

        return ResponseEntity.ok(paginatedResult);
    }

    private Sort getSortionByRequest(GetGenericEntityRequest request) {
        Sort sortion = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
        if (request.getSort() != null) {
            sortion = Sort.by(
                    request.getSort().getItems().stream().map(sortItem -> {
                        Sort.Direction direction =
                                sortItem.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
                        return new Sort.Order(direction, sortItem.getPropertyName());
                    }).toList()
            );
        }

        return sortion;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateGenericEntityRequest request
    ) {
        GenericEntityDto resultDto =
                this.genericEntityService.update(id, request.getEntity(), request.getAssociationStates());

        return ResponseEntity.ok(resultDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestParam Long typeId) {
        this.genericEntityService.delete(typeId, id);
        return ResponseEntity.noContent().build();
    }
}
