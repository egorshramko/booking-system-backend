package io.github.egorshramko.booking.model.dictionaries;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сущность города.
 * Взята из открытого справочника DaData
 * <a href="https://github.com/hflabs/city.git">...</a>
 */
@Entity
@Data
@NoArgsConstructor
public class City {

    @Id
    @SequenceGenerator(name = "city_id_gen", sequenceName = "city_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_id_gen")
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();

    //Адрес одной строкой
    private String address;

    //Почтовый индекс
    private Integer postalCode;

    //Страна
    private String country;

    //Федеральный округ
    private String federalDistrict;

    //Тип региона
    private String regionType;

    //Регион
    private String region;

    //Тип района
    private String areaType;

    //Район
    private String area;

    //Тип города
    private String cityType;

    //Город
    private String city;

    //Тип населенного пункта
    private String settlementType;

    //Населенный пункт
    private String settlement;

    //Код КЛАДР
    private String kladrId;

    //Код ФИАС
    private String fiasId;

    //Уровень по ФИАС
    private Integer fiasLevel;

    //Признак центра региона или района
    private Integer capitalMarker;

    //Код ОКАТО
    private String okato;

    //Код ОКТМО
    private String oktmo;

    //Код ИНФС
    private String taxOffice;

    //Часовой пояс
    private String timezone;

    //Широта
    private BigDecimal geoLat;

    //Долгота
    private BigDecimal geoLon;

    //Население
    private Integer population;

    //Год основания
    private Integer foundationYear;

}
