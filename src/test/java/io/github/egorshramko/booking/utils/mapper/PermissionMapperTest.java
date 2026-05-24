package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.model.security.ObjectType;
import io.github.egorshramko.booking.model.security.Permission;
import io.github.egorshramko.booking.model.security.PermissionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PermissionMapperTest {

    private final PermissionMapper mapper = Mappers.getMapper(PermissionMapper.class);

    @Test
    public void toDto_whenEntityContainsAllFields_thenConvertToCorrectDto() {

        //given
        Permission inputEntity = Permission.builder()
                .id(1L)
                .actual(true)
                .object(ObjectType.USER)
                .type(PermissionType.CREATE)
                .build();

        PermissionDto expectedDto = new PermissionDto("CREATE_USER");

        //when
        PermissionDto actualDto = mapper.toDto(inputEntity);

        //then
        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);

    }

    @Test
    public void toEntity_whenDtoContainsName_thenConvertToCorrectEntity() {
        //given
        PermissionDto inputDto = new PermissionDto("CREATE_USER");

        Permission expectedEntity = Permission.builder()
                .actual(true)
                .type(PermissionType.CREATE)
                .object(ObjectType.USER)
                .build();

        //when
        Permission actualEntity = mapper.toEntity(inputDto);

        //then
        assertNotNull(actualEntity);
        assertEquals(expectedEntity, actualEntity);
    }

}
