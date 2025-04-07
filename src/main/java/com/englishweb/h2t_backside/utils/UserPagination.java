package com.englishweb.h2t_backside.utils;

import com.englishweb.h2t_backside.dto.filter.UserFilterDTO;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.InvalidArgumentException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.interfacemodel.UserEntity;
import com.englishweb.h2t_backside.repository.specifications.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public class UserPagination {

    public static <T extends UserEntity, R extends JpaRepository<T, Long> & JpaSpecificationExecutor<T>> Page<T> searchWithFiltersGeneric(
            int page, int size, String sortFields, UserFilterDTO filter,
            R repository, Class<T> entityClass) {

        if (page < 0) {
            throw new InvalidArgumentException("Page index must not be less than 0.", page, ErrorApiCodeContent.PAGE_INDEX_INVALID, SeverityEnum.LOW);
        }

        if (size <= 0) {
            throw new InvalidArgumentException("Page size must be greater than 0.", size, ErrorApiCodeContent.PAGE_SIZE_INVALID, SeverityEnum.LOW);
        }

        Specification<T> specification = Specification.where(BaseFilterSpecification.applyBaseFilters(filter));

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            specification = specification.and(UserSpecification.findByName(filter.getName()));
        }

        if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
            specification = specification.and(UserSpecification.findByEmail(filter.getEmail()));
        }

        if (filter.getRoleEnumList() != null && !filter.getRoleEnumList().isEmpty()) {
            specification = specification.and(UserSpecification.findByRoles(filter.getRoleEnumList()));
        }

        if (filter.getLevelEnum() != null) {
            specification = specification.and(UserSpecification.findByLevel(filter.getLevelEnum()));
        }

        List<Sort.Order> orders = ParseData.parseStringToSortOrderList(sortFields);

        if (!ValidationData.isValidFieldInSortList(entityClass, orders)) {
            throw new InvalidArgumentException("Invalid sort field.", sortFields, ErrorApiCodeContent.SORT_FIELD_INVALID, SeverityEnum.LOW);
        }

        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(specification, pageable);
    }

}
