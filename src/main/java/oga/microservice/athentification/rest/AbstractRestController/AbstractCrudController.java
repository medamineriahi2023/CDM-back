package oga.microservice.athentification.rest.AbstractRestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.abstracts.ICrudService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
public abstract class AbstractCrudController<T extends AbstractEntity, TDTO extends AbstractDTO> {
    @Autowired
    protected AbstractEntityFactory<T, TDTO> _factory;

    @Autowired
    MessageSource messageSource;

    @Autowired
    protected ICrudService<T, TDTO> _service;

    private Class<T> entityClass;

    public AbstractCrudController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    /**
     * ***********************************************************************************************\
     * * Create operations **
     * \**********************************************************************************************
     */
    @CrossOrigin(origins = "*")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> create(@RequestBody TDTO dto,
                                                      @RequestParam(value = "fields", required = false) String fields) throws Exception {
        T entity = _service.create(dto);

        MappingJacksonValue mapping = _entityToMappingJacksonValue(entity, fields);

        return new ResponseEntity<>(mapping, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "*")

    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> create(@RequestBody List<TDTO> dtos,
                                                      @RequestParam(value = "fields", required = false) String fields) throws Exception {
        List<T> entities = _service.createAllDtos(dtos);

        MappingJacksonValue mapping = _entitiesToMappingJacksonValues(entities, fields);

        return new ResponseEntity<>(mapping, HttpStatus.CREATED);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<MappingJacksonValue> get(@PathVariable Long id,
                                                   @RequestParam(value = "fields", required = false) String fields) throws Exception {
        Optional<T> optional = _service.findById(id);

        if (optional.isPresent()) {
            T entity = optional.get();

            MappingJacksonValue mapping = _entityToMappingJacksonValue(entity, fields);

            return new ResponseEntity<>(mapping, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException(messageSource.getMessage("entity.not.found", null, new Locale("fr")), ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>());
        }
    }


    /***************************************************************************************
     ********************************** Update operations **********************************
     ***************************************************************************************/
    @CrossOrigin(origins = "*")

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> update(@RequestBody TDTO dto,
                                                      @RequestParam(value = "fields", required = false) String fields) throws Exception {

        Long entityId = dto.getId();
        Optional<T> entityOptional = _service.findById(entityId);

        if (entityOptional.isPresent()) {
            T entity = _service.update(dto);

            MappingJacksonValue mapping = _entityToMappingJacksonValue(entity, fields);

            return new ResponseEntity<>(mapping, HttpStatus.OK);
        } else {
            final T entity = _factory.dtoToEntity(dto);

            throw new EntityNotFoundException("user not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>());
        }
    }
    @CrossOrigin(origins = "*")

    @PutMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> update(@RequestBody List<TDTO> dtos,
                                                      @RequestParam(value = "fields", required = false) String fields) throws Exception {

        boolean allIdsFound = true;
        for (TDTO dto : dtos) {
            Optional<T> optional = _service.findById(dto.getId());

            if (!optional.isPresent()) {
                allIdsFound = false;
            }
        }

        if (allIdsFound) {
            List<T> entities = _service.updateAllDtos(dtos);

            MappingJacksonValue mapping = _entitiesToMappingJacksonValues(entities, fields);

            return new ResponseEntity<>(mapping, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*")

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> updatePartial(@RequestBody TDTO dto,
                                                             @RequestParam(value = "updateFields", required = false) String updateFields,
                                                             @RequestParam(value = "fields", required = false) String fields) throws Exception {

        Long entityId = dto.getId();
        Optional<T> entityOptional = _service.findById(entityId);

        if (entityOptional.isPresent()) {
            T entity = _service.updatePartial(dto, updateFields);

            MappingJacksonValue mapping = _entityToMappingJacksonValue(entity, fields);

            return new ResponseEntity<>(mapping, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("user not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>());
        }
    }
    @CrossOrigin(origins = "*")

    @PatchMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MappingJacksonValue> updatePartial(@RequestBody List<TDTO> dtos,
                                                             @RequestParam(value = "updateFields", required = false) String updateFields,
                                                             @RequestParam(value = "fields", required = false) String fields) throws Exception {

        boolean allIdsFound = true;
        for (TDTO dto : dtos) {
            Optional<T> optional = _service.findById(dto.getId());

            if (!optional.isPresent()) {
                allIdsFound = false;
            }
        }

        if (allIdsFound) {
            List<T> entities = _service.updatePartialAllDtos(dtos, updateFields);

            MappingJacksonValue mapping = _entitiesToMappingJacksonValues(entities, fields);

            return new ResponseEntity<>(mapping, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")

    @GetMapping()
    public ResponseEntity<MappingJacksonValue> getAll() throws Exception {
        List<T> entities =  _service.findAll();
        MappingJacksonValue mapping = _entitiesToMappingJacksonValues(entities, null);
        return new ResponseEntity<>(mapping, HttpStatus.OK);
    }

    /***************************************************************************************
     * Delete operations *
     * ************************************************************************************/
    @CrossOrigin(origins = "*")

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<T> entity = _service.findById(id);

        if (entity.isPresent()) {
            _service.delete(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    protected MappingJacksonValue _entitiesToMappingJacksonValues(List<T> entities, String fieldsParam) throws Exception {
        List<TDTO> results =
                entities.stream().map(entity -> _factory.entityToDto(entity, fieldsParam)).collect(Collectors.toList());

        MappingJacksonValue mapping = new MappingJacksonValue(results);
        if (results.size() > 0) {
            final TDTO instance = results.get(0);

            mapping = _dtoToMappingJacksonValue(results, instance.getClass().getSimpleName(), fieldsParam);
        }

        return mapping;
    }

    protected PropertyFilter _filterFromParam(String filterParam) throws Exception {
        if (filterParam == null) {
            return SimpleBeanPropertyFilter.serializeAll();
        }

        List<String> fields = new ArrayList<>(Arrays.asList(filterParam.split(",")));


        Set<String> fieldsToInclude = new HashSet<>();
        Set<String> fieldsToExclude = new HashSet<>();
        Set<String> fieldsChild = new HashSet<>();

        for (String field : fields) {
            String propertyName = field.replaceAll("[+-]", "");

            String[] multiProps = propertyName.split("\\.");
            boolean isExclude = field.startsWith("-");

            if (multiProps.length > 1) {
                propertyName = multiProps[0];
                fieldsChild.add(propertyName);
            } else if (isExclude) {
                fieldsToExclude.add(propertyName);
            } else {
                fieldsToInclude.add(propertyName);
            }
        }

        if (!fieldsToInclude.isEmpty()) {
            fieldsToInclude.addAll(fieldsChild);
        }

        if (!fieldsToInclude.isEmpty() && !fieldsToExclude.isEmpty()) {
            throw new Exception("Can't use both inclusive and exclusive property filtering on API call");
        }

        PropertyFilter result;
        if (!fieldsToExclude.isEmpty()) {
            result = SimpleBeanPropertyFilter.serializeAllExcept(fieldsToExclude);
        } else if (!fieldsToInclude.isEmpty()) {
            result = SimpleBeanPropertyFilter.filterOutAllExcept(fieldsToInclude);
        } else {
            result = SimpleBeanPropertyFilter.serializeAll();
        }

        return result;
    }

    protected MappingJacksonValue _entityToMappingJacksonValue(T entity, String fieldsParam) throws Exception {
        TDTO dto = _factory.entityToDto(entity, fieldsParam);

        return _dtoToMappingJacksonValue(dto, dto.getClass().getSimpleName(), fieldsParam);
    }

    protected MappingJacksonValue _dtoToMappingJacksonValue(Object result, String dtoClass, String fieldsParams) throws Exception {
        MappingJacksonValue mapping = new MappingJacksonValue(result);
        PropertyFilter filter = _filterFromParam(fieldsParams);
        FilterProvider provider = new SimpleFilterProvider().addFilter(dtoClass, filter).setFailOnUnknownId(false);
        mapping.setFilters(provider);
        return mapping;
    }

    protected <U> MappingJacksonValue _dtosToMappingJacksonValue(List<U> dtos, String fieldsParam) throws Exception {
        if (dtos.size() > 0) {
            final U instance = dtos.get(0);

            return _dtoToMappingJacksonValue(dtos, instance.getClass().getSimpleName(), fieldsParam);
        } else {
            return new MappingJacksonValue(dtos);
        }
    }
}
