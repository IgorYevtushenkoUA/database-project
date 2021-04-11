package com.project.database.additional;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
public class Pag {

    public Sort setSort(String sortBy, boolean sortDesc) {
        sortBy = setSortBy(sortBy);
        return sortDesc
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    public String setSortBy(String sortBy) {

        switch (sortBy) {
            case "surname":
                return "studentSurname";
            case "completeMark":
                return "completeMark";
            case "course":
                return "course";
            default:
                return "studentSurname";
        }
    }

}
