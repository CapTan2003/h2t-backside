package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.dto.filter.ToeicFilterDTO;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.InvalidArgumentException;
import com.englishweb.h2t_backside.model.test.Toeic;
import com.englishweb.h2t_backside.repository.specifications.ToeicSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public class ToeicPagination {

    public static <T extends Toeic, R extends JpaRepository<T, Long> & JpaSpecificationExecutor<T>>
    Page<T> searchWithFiltersGeneric(
            int page, int size, String sortFields, ToeicFilterDTO filter,
            R repository, Class<T> entityClass) {

        if (page < 0) {
            throw new InvalidArgumentException("Page index must not be less than 0.", page, ErrorApiCodeContent.PAGE_INDEX_INVALID);
        }

        if (size <= 0) {
            throw new InvalidArgumentException("Page size must be greater than 0.", size, ErrorApiCodeContent.PAGE_SIZE_INVALID);
        }

        Specification<T> specification = Specification.where(null);

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            specification = specification.and((Specification<T>) ToeicSpecification.findByName(filter.getTitle()));
        }

        List<Sort.Order> orders = ParseData.parseStringToSortOrderList(sortFields);

        if (!ValidationData.isValidFieldInSortList(entityClass, orders)) {
            throw new InvalidArgumentException("Invalid sort field.", sortFields, ErrorApiCodeContent.SORT_FIELD_INVALID);
        }

        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(specification, pageable);
    }
}
