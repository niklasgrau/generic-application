package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.converters.impl.DefaultGenericEntityConverter;
import de.fhswf.genericapplication.dto.GenericEntityDto;
import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.Employee;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.AssociationStateService;
import de.fhswf.genericapplication.utils.GenericEntityClassUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultGenericEntityServiceTest {

    @InjectMocks
    DefaultGenericEntityService underTest;

    @Mock
    GenericEntityRepository genericEntityRepository;

    @Mock
    GenericEntityClassUtils genericEntityClassUtils;

    @Mock
    DefaultGenericEntityConverter genericEntityConverter;

    @Mock
    DefaultBaseEntityService baseEntityService;

    @Mock
    AssociationStateService associationStateService;

    @Test
    public void when_GetExistingEntityWithoutProjection_Then_ReturnEntity() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        long id = 1L;
        GenericEntityDto genericEntityMock = mock(GenericEntityDto.class);
        Employee employeeMock = mock(Employee.class);

        doReturn(Employee.class).when(baseEntityService).getClassByTypeId(typeId);
        doReturn(employeeMock).when(genericEntityRepository).findById(Employee.class, id);
        doReturn(genericEntityMock).when(genericEntityConverter).convert(employeeMock);

        // Act
        GenericEntityDto genericEntity = underTest.get(typeId, id, Collections.emptyList());

        // Assert
        assertNotNull(genericEntity);
        assertEquals(genericEntity, genericEntityMock);
    }

    @Test
    public void when_GetExistingEntityWithProjection_Then_ReturnEntity() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        long id = 1L;
        GenericEntityDto genericEntityMock = mock(GenericEntityDto.class);
        Map<String, String> properties = new HashMap<>();
        properties.put("param1", "Hello");
        properties.put("param2", "World");
        doReturn(properties).when(genericEntityMock).getProperties();

        Employee employeeMock = mock(Employee.class);

        doReturn(Employee.class).when(baseEntityService).getClassByTypeId(typeId);
        doReturn(employeeMock).when(genericEntityRepository).findById(Employee.class, id);
        doReturn(genericEntityMock).when(genericEntityConverter).convert(employeeMock);

        // Act
        GenericEntityDto genericEntity = underTest.get(typeId, id, List.of("param1"));

        // Assert
        assertNotNull(genericEntity);
        assertEquals(genericEntity, genericEntityMock);
        assertTrue(genericEntity.getProperties().containsKey("param1"));
        assertFalse(genericEntity.getProperties().containsKey("param2"));
    }

    @Test
    public void when_GetNotExistingEntity_Then_ThrowModelNotFoundException() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        long id = 1L;
        doThrow(ClassNotFoundException.class).when(baseEntityService).getClassByTypeId(typeId);

        // Act & Assert
        assertThrows(ModelNotFoundException.class, () -> underTest.get(typeId, id, Collections.emptyList()));
        verifyNoInteractions(genericEntityRepository);
    }

    @Test
    public void when_GetListOfEntities_Then_ReturnList() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        Employee employee1Mock = mock(Employee.class);
        Employee employee2Mock = mock(Employee.class);
        GenericEntityDto genericEntity1Mock = mock(GenericEntityDto.class);
        GenericEntityDto genericEntity2Mock = mock(GenericEntityDto.class);

        doReturn(Employee.class).when(baseEntityService).getClassByTypeId(typeId);
        PageImpl<Employee> pages = new PageImpl<>(Arrays.asList(employee1Mock, employee2Mock), Pageable.ofSize(2), 2);
        doReturn(pages).when(genericEntityRepository).findAll(Employee.class, null, null);
        doReturn(genericEntity1Mock).when(genericEntityConverter).convert(employee1Mock);
        doReturn(genericEntity2Mock).when(genericEntityConverter).convert(employee2Mock);

        // Act
        Page<GenericEntityDto> list = underTest.list(typeId, Collections.emptyList(), null, null);

        // Assert
        assertNotNull(list);
        assertEquals(2, list.getTotalElements());
        assertTrue(list.stream().anyMatch(genericEntityDto -> genericEntityDto.equals(genericEntity1Mock)));
        assertTrue(list.stream().anyMatch(genericEntityDto -> genericEntityDto.equals(genericEntity2Mock)));
    }

    @Test
    public void when_GetListOfProjectedEntities_Then_ReturnProjectedList() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;

        Map<String, String> properties = new HashMap<>();
        properties.put("param1", "Hello");
        properties.put("param2", "World");

        Employee employee1Mock = mock(Employee.class);
        Employee employee2Mock = mock(Employee.class);
        GenericEntityDto genericEntity1Mock = mock(GenericEntityDto.class);
        GenericEntityDto genericEntity2Mock = mock(GenericEntityDto.class);

        doReturn(properties).when(genericEntity1Mock).getProperties();
        doReturn(properties).when(genericEntity2Mock).getProperties();

        doReturn(Employee.class).when(baseEntityService).getClassByTypeId(typeId);
        PageImpl<Employee> pages = new PageImpl<>(Arrays.asList(employee1Mock, employee2Mock), Pageable.ofSize(2), 2);
        doReturn(pages).when(genericEntityRepository).findAll(Employee.class, null, null);
        doReturn(genericEntity1Mock).when(genericEntityConverter).convert(employee1Mock);
        doReturn(genericEntity2Mock).when(genericEntityConverter).convert(employee2Mock);

        // Act
        Page<GenericEntityDto> list = underTest.list(typeId, List.of("param1"), null, null);

        // Assert
        assertNotNull(list);
        assertEquals(2, list.getTotalElements());
        assertTrue(list.stream().anyMatch(genericEntityDto -> genericEntityDto.equals(genericEntity1Mock)));
        assertTrue(list.stream().anyMatch(genericEntityDto -> genericEntityDto.equals(genericEntity2Mock)));

        for (GenericEntityDto genericEntityDto : list) {
            assertTrue(genericEntityDto.getProperties().containsKey("param1"));
            assertFalse(genericEntityDto.getProperties().containsKey("param2"));
        }

    }

    @Test
    public void when_GetListOfUnknownEntity_Then_ThrowModelNotFoundException() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        doThrow(ClassNotFoundException.class).when(baseEntityService).getClassByTypeId(typeId);

        // Act & Assert
        assertThrows(ModelNotFoundException.class, () -> underTest.list(typeId, Collections.emptyList(), null, null));
        verifyNoInteractions(genericEntityRepository);
    }

    @Test
    public void when_CreatingEntityWithoutAssociations_Then_CreateEntity() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        GenericEntityDto genericEntityMock = mock(GenericEntityDto.class);
        Employee employeeMock = mock(Employee.class);

        doReturn(typeId).when(genericEntityMock).getTypeId();
        doReturn(Employee.class).when(baseEntityService).getClassByTypeId(typeId);
        doReturn(employeeMock).when(genericEntityConverter).convert(genericEntityMock, Employee.class);
        doReturn(employeeMock).when(genericEntityRepository).create(employeeMock);
        doReturn(genericEntityMock).when(genericEntityConverter).convert(employeeMock);
        doReturn(employeeMock).when(genericEntityRepository).update(employeeMock);

        // Act
        GenericEntityDto genericEntityDto = underTest.create(genericEntityMock, Collections.emptyMap());

        // Assert
        assertNotNull(genericEntityDto);
        assertEquals(genericEntityMock, genericEntityDto);
        verify(baseEntityService).getClassByTypeId(typeId);
        verify(genericEntityConverter).convert(genericEntityMock, Employee.class);
        verify(genericEntityRepository).create(employeeMock);
        verify(genericEntityRepository).update(employeeMock);
        verify(genericEntityConverter).convert(employeeMock);
    }

    @Test
    public void when_DeleteExistingEntity_Then_DeleteEntity() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        long id = 1L;
        Employee employeeMock = mock(Employee.class);

        doReturn(Employee.class).when(baseEntityService).getClassByTypeId(typeId);
        doReturn(employeeMock).when(genericEntityRepository).findById(Employee.class, id);

        // Act
        underTest.delete(typeId, id);

        // Assert
        verify(baseEntityService).getClassByTypeId(typeId);
        verify(genericEntityRepository).findById(Employee.class, id);
        verify(genericEntityRepository).delete(employeeMock);
    }


    @Test
    public void when_DeleteNotExistingEntity_Then_ThrowModelNotFoundException() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        long id = 1L;
        doThrow(ClassNotFoundException.class).when(baseEntityService).getClassByTypeId(typeId);

        // Act & Assert
        assertThrows(ModelNotFoundException.class, () -> underTest.delete(typeId, id));
        verifyNoInteractions(genericEntityRepository);
    }
}