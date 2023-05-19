package de.fhswf.genericapplication.configs;

import de.fhswf.genericapplication.filter.data.DataTypeET;
import de.fhswf.genericapplication.filter.predicates.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Config to provide a map of filters to filter by predicates.
 *
 * @author Niklas Grau, Kevin Link
 */
@Configuration
public class FilterTypePredicateConfig {
    @Bean
    public Map<DataTypeET, FilterPredicate> filterPredicateMap() {
        return Map.of(
                DataTypeET.DATE, new DateFilterPredicate(),
                DataTypeET.NUMBER, new NumberFilterPredicate(),
                DataTypeET.STRING, new StringFilterPredicate(),
                DataTypeET.ENUM, new EnumFilterPredicate(),
                DataTypeET.BOOLEAN, new BooleanFilterPredicate(),
                DataTypeET.COLLECTION, new CollectionFilterPredicate(),
                DataTypeET.ENTITY, new BaseEntityFilterPredicate()
        );
    }
}
