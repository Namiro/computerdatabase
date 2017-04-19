package com.excilys.burleon.computerdatabase.view.model.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.view.model.CompanyDTO;

public class CompanyMapper {

    /**
     * To map a Company from the view to a Company for the services.
     *
     * @param companyDTO
     *            The DTO
     * @return The company for the service
     */
    public static Company toCompany(final CompanyDTO companyDTO) {
        if (companyDTO.id.equals("")) {
            companyDTO.id = "0";
        }
        return new Company.CompanyBuilder().name(companyDTO.name).id(Long.parseLong(companyDTO.id)).build();
    }

    /**
     * To map a list of company DTO.
     *
     * @param companyDTOs
     *            A list of company DTO
     * @return A list of company for service
     */
    public static List<Company> toCompany(final List<CompanyDTO> companyDTOs) {
        final List<Company> companys = new ArrayList<>();
        companyDTOs.forEach(companyDTO -> companys.add(CompanyMapper.toCompany(companyDTO)));
        return companys;
    }

    /**
     * To map a Company from the view to a Company for the services.
     *
     * @param company
     *            The company for the service
     * @return The DTO
     */
    public static CompanyDTO toCompanyDTO(final Company company) {
        final CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.id = company.getId() + "";
        companyDTO.name = company.getName();
        return companyDTO;
    }

    /**
     * To map a list of company.
     *
     * @param companys
     *            A list of company for service
     * @return A list of company DTO
     */
    public static List<CompanyDTO> toCompanyDTO(final List<Company> companys) {
        final List<CompanyDTO> companyDTOs = new ArrayList<>();
        companys.forEach(company -> companyDTOs.add(CompanyMapper.toCompanyDTO(company)));
        return companyDTOs;
    }
}
