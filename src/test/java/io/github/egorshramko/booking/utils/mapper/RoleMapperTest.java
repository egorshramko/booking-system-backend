package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.model.security.ObjectType;
import io.github.egorshramko.booking.model.security.Permission;
import io.github.egorshramko.booking.model.security.PermissionType;
import io.github.egorshramko.booking.model.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleMapperTest {

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Test
    public void toEntity_whenDtoContainsPermissions_thenConvertToEntity() {
        //given
        PermissionDto permissionDto1 = new PermissionDto("CREATE_USER");
        PermissionDto permissionDto2 = new PermissionDto("UPDATE_USER");

        RoleDto inputRoleDto = new RoleDto(1L, "SOME_ROLE", Set.of(
                permissionDto1, permissionDto2
        ));

        Permission permissionEntity1 = Permission.builder()
                .actual(true)
                .type(PermissionType.CREATE)
                .object(ObjectType.USER)
                .build();
        Permission permissionEntity2 = Permission.builder()
                .actual(true)
                .type(PermissionType.UPDATE)
                .object(ObjectType.USER)
                .build();

        Role expectedRoleEntity = Role.builder()
                .id(1L)
                .name("SOME_ROLE")
                .permissions(Set.of(
                        permissionEntity1, permissionEntity2
                ))
                .build();

        //when
        when(permissionMapper.toEntity(permissionDto1)).thenReturn(permissionEntity1);
        when(permissionMapper.toEntity(permissionDto2)).thenReturn(permissionEntity2);

        Role actualRoleEntity = roleMapper.toEntity(inputRoleDto);

        //then
        assertNotNull(actualRoleEntity);
        assertEquals(expectedRoleEntity.getId(), actualRoleEntity.getId());
        assertEquals(expectedRoleEntity.getName(), actualRoleEntity.getName());
        assertEquals(expectedRoleEntity.getPermissions(), actualRoleEntity.getPermissions());
    }

    @Test
    public void toDto_whenEntityContainsPermissions_thenReturnCorrectDto() {

        //given
        Permission permission1 = Permission.builder()
                .id(1L)
                .actual(true)
                .type(PermissionType.CREATE)
                .object(ObjectType.USER)
                .build();
        Permission permission2 = Permission.builder()
                .id(2L)
                .actual(true)
                .type(PermissionType.UPDATE)
                .object(ObjectType.USER)
                .build();

        Role inputRoleEntity = Role.builder()
                .id(1L)
                .actual(true)
                .name("SOME_ROLE")
                .permissions(Set.of(permission1, permission2))
                .build();

        PermissionDto permissionDto1 = new PermissionDto("CREATE_USER");
        PermissionDto permissionDto2 = new PermissionDto("UPDATE_USER");

        RoleDto expectedRoleDto = new RoleDto(1L, "SOME_ROLE",
                Set.of(permissionDto1, permissionDto2));

        //when
        when(permissionMapper.toDto(permission1)).thenReturn(permissionDto1);
        when(permissionMapper.toDto(permission2)).thenReturn(permissionDto2);

        RoleDto actualRoleDto = roleMapper.toDto(inputRoleEntity);

        //then
        assertNotNull(actualRoleDto);
        assertEquals(expectedRoleDto.id(), actualRoleDto.id());
        assertEquals(expectedRoleDto.name(), actualRoleDto.name());
        assertEquals(expectedRoleDto.permissions(), actualRoleDto.permissions());

    }


}
