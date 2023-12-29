package oga.microservice.athentification.dtos.abstracts;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;
import oga.microservice.athentification.utils.FilterUtils;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public abstract class AbstractDTO {
    protected GregorianCalendar creationDate;
    protected GregorianCalendar updateDate;
    protected Long id;

    protected AbstractDTO(AbstractEntity entity) {
        this.setId(entity.getId());
        this.setCreationDate(entity.getCreationDate());
        this.setUpdateDate(entity.getUpdateDate());
    }

    protected AbstractDTO(AbstractEntity entity, String fields) {
        Map<String, Set<String>> filters = _filterFromField(fields);

        this.setId(entity.getId());

        if (_isFetched(Fields.creationDate, filters)) {
            this.setCreationDate(entity.getCreationDate());
        }

        if (_isFetched(Fields.updateDate, filters)) {
            this.setUpdateDate(entity.getUpdateDate());
        }
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate != null) {
            GregorianCalendar result = new GregorianCalendar();
            result.setTime(creationDate);

            this.creationDate = result;
        }
    }

    public void setUpdateDate(Date updateDate) {
        if (updateDate != null) {
            GregorianCalendar result = new GregorianCalendar();
            result.setTime(updateDate);

            this.updateDate = result;
        }
    }

    protected Map<String, Set<String>> _filterFromField(String filterParam) {
        return FilterUtils.filterFromField(filterParam);
    }

    protected String _fieldsFromChildFields(String child, String filterParam) {
        return FilterUtils.fieldsFromChildFields(child, filterParam);
    }

    protected boolean _isFetched(String property, Map<String, Set<String>> filters) {
        return FilterUtils.isFetched(property, filters);
    }
}
