package com.excilys.burleon.computerdatabase.webapp.dtomodel.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.CompanyDto;

public class CompanyMapper {

    /**
     * To map a Company from the view to a Company for the services.
     *
     * @param companyDto
     *            The DTO
     * @return The company for the service
     */
    public static Company toCompany(final CompanyDto companyDto) {
        if (companyDto.id.equals("")) {
            companyDto.id = "0";
        }
        return new Company.CompanyBuilder().name(companyDto.name).id(Long.parseLong(companyDto.id)).build();
    }

    /**
     * To map a list of company DTO.
     *
     * @param companyDtos
     *            A list of company DTO
     * @return A list of company for service
     */
    public static List<Company> toCompany(final List<CompanyDto> companyDtos) {
        final List<Company> companys = new ArrayList<>();
        companyDtos.forEach(companyDTO -> companys.add(CompanyMapper.toCompany(companyDTO)));
        return companys;
    }

    /**
     * To map a Company from the view to a Company for the services.
     *
     * @param company
     *            The company for the service
     * @return The DTO
     */
    public static CompanyDto toDto(final Company company) {
        final CompanyDto companyDto = new CompanyDto();
        companyDto.id = company.getId() + "";
        companyDto.name = company.getName();
        return companyDto;
    }

    /**
     * To map a list of company.
     *
     * @param companys
     *            A list of company for service
     * @return A list of company DTO
     */
    public static List<CompanyDto> toDto(final List<Company> companys) {
        final List<CompanyDto> companyDtos = new ArrayList<>();
        companys.forEach(company -> companyDtos.add(CompanyMapper.toDto(company)));
        return companyDtos;
    }
}
