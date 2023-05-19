package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.models.Employee;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultBaseEntityServiceTest {

    @InjectMocks
    DefaultBaseEntityService underTest;

    @Mock
    GenericEntityRepository genericEntityRepository;

    @Mock
    Map<Long, Class<? extends BaseEntity>> typeRegistry;

    @Test
    public void when_EntityExists_Then_ReturnEntity() throws ModelNotFoundException {
        // Arrange
        Long typeId = 123456L;
        Long id = 1L;

        doReturn(Employee.class).when(typeRegistry).get(typeId);
        when(genericEntityRepository.findById(Employee.class, id)).thenReturn(mock(Employee.class));

        // Act
        BaseEntity baseEntity = underTest.getBaseEntity(typeId, id);

        // Assert
        assertNotNull(baseEntity);
        verify(genericEntityRepository).findById(Employee.class, id);
    }

    @Test
    public void when_EntityNotExists_Then_ThrowClassNotFoundException() {
        // Arrange
        Long typeId = 123456L;
        Long id = 1L;

        // Act & Assert
        assertThrows(ModelNotFoundException.class, () -> underTest.getBaseEntity(typeId, id));
    }

    @Test
    public void when_ClassExists_Then_ReturnClass() throws ClassNotFoundException {
        // Arrange
        Long typeId = 123456L;
        Long id = 1L;

        doReturn(Employee.class).when(typeRegistry).get(typeId);

        // Act
        Class<? extends BaseEntity> classByTypeId = underTest.getClassByTypeId(typeId);

        // Assert
        assertNotNull(classByTypeId);
    }

    @Test
    public void when_ClassNotExists_Then_ThrowClassNotFoundException() {
        // Arrange
        Long typeId = 123456L;
        // Act & Assert
        assertThrows(ClassNotFoundException.class, () -> underTest.getClassByTypeId(typeId));
    }

}