package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.models.Employee;
import de.fhswf.genericapplication.models.Project;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.BaseEntityService;
import de.fhswf.genericapplication.utils.GenericEntityClassUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultAssociationStateServiceTest {

    @Spy
    @InjectMocks
    DefaultAssociationStateService underTest;

    @Mock
    GenericEntityRepository genericEntityRepository;

    @Mock
    GenericEntityClassUtils genericEntityClassUtils;

    @Mock
    BaseEntityService baseEntityService;

    @Test
    public void when_ConnectByBaseEntity_Then_ConnectManyAssociates() {
        // Arrange
        String propertyName = "managerProjects";
        Employee employeeMock = mock(Employee.class);
        Project projectMock = mock(Project.class);

        when(underTest.connect(any(),any(),any())).thenReturn(projectMock);
        when(genericEntityRepository.update(any())).thenReturn(employeeMock);
        when(genericEntityClassUtils.isCollectionProperty(employeeMock, propertyName)).thenReturn(true);

        // Act
        BaseEntity connect = underTest.connect(employeeMock, propertyName, projectMock);

        // Assert
        assertNotNull(connect);
        assertEquals(employeeMock, connect);
        verify(genericEntityRepository).update(employeeMock);
        verify(genericEntityClassUtils).isCollectionProperty(employeeMock, propertyName);
        verify(genericEntityClassUtils).addToCollectionProperty(employeeMock, propertyName, projectMock);
    }

    @Test
    public void when_ConnectByBaseEntity_Then_ConnectOneAssociate() {
        // Arrange
        String propertyName = "managerProjects";
        Employee employeeMock = mock(Employee.class);
        Project projectMock = mock(Project.class);

        when(underTest.connect(any(),any(),any())).thenReturn(projectMock);
        when(genericEntityRepository.update(any())).thenReturn(employeeMock);
        when(genericEntityClassUtils.isCollectionProperty(employeeMock, propertyName)).thenReturn(false);

        // Act
        BaseEntity connect = underTest.connect(employeeMock, propertyName, projectMock);

        // Assert
        assertNotNull(connect);
        assertEquals(employeeMock, connect);
        verify(genericEntityRepository).update(employeeMock);
        verify(genericEntityClassUtils).isCollectionProperty(employeeMock, propertyName);
        verify(genericEntityClassUtils, never()).addToCollectionProperty(employeeMock, propertyName, projectMock);
        verify(genericEntityClassUtils).setProperty(employeeMock, propertyName, projectMock);
    }

    @Test
    public void when_DisconnectByBaseEntity_Then_DisconnectManyAssociates() {
        // Arrange
        String propertyName = "managerProjects";
        Employee employeeMock = mock(Employee.class);
        Project projectMock = mock(Project.class);

        when(underTest.disconnect(any(),any(),any())).thenReturn(projectMock);
        when(genericEntityRepository.update(any())).thenReturn(employeeMock);
        when(genericEntityClassUtils.isCollectionProperty(employeeMock, propertyName)).thenReturn(true);

        // Act
        BaseEntity disconnect = underTest.disconnect(employeeMock, propertyName, projectMock);

        // Assert
        assertNotNull(disconnect);
        assertEquals(employeeMock, disconnect);
        verify(genericEntityRepository).update(employeeMock);
        verify(genericEntityClassUtils).isCollectionProperty(employeeMock, propertyName);
        verify(genericEntityClassUtils).removeFromCollectionProperty(employeeMock, propertyName, projectMock);
    }

    @Test
    public void when_DisconnectByBaseEntity_Then_DisconnectOneAssociate() {
        // Arrange
        String propertyName = "managerProjects";
        Employee employeeMock = mock(Employee.class);
        Project projectMock = mock(Project.class);

        when(underTest.disconnect(any(),any(),any())).thenReturn(projectMock);
        when(genericEntityRepository.update(any())).thenReturn(employeeMock);
        when(genericEntityClassUtils.isCollectionProperty(employeeMock, propertyName)).thenReturn(false);

        // Act
        BaseEntity disconnect = underTest.disconnect(employeeMock, propertyName, projectMock);

        // Assert
        assertNotNull(disconnect);
        assertEquals(employeeMock, disconnect);
        verify(genericEntityRepository).update(employeeMock);
        verify(genericEntityClassUtils).isCollectionProperty(employeeMock, propertyName);
        verify(genericEntityClassUtils, never()).addToCollectionProperty(employeeMock, propertyName, projectMock);
        verify(genericEntityClassUtils).setProperty(employeeMock, propertyName, null);
    }

    @Test
    public void when_EntityExists_Then_DeleteEntity() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        long id = 1L;

        doReturn(Employee.class).when(baseEntityService).getClassByTypeId(typeId);

        // Act
        underTest.delete(typeId, id);

        // Assert
        verify(genericEntityRepository).deleteById(Employee.class, id);
    }

    @Test
    public void when_EntityNotExists_Then_ThrowModelNotFoundException() throws ClassNotFoundException {
        // Arrange
        long typeId = 123456L;
        long id = 1L;

        doReturn(null).when(baseEntityService).getClassByTypeId(typeId);
        doThrow(ModelNotFoundException.class).when(genericEntityRepository).deleteById(null, id);

        // Act & Assert
        assertThrows(ModelNotFoundException.class, () -> underTest.delete(typeId, id));
    }

}