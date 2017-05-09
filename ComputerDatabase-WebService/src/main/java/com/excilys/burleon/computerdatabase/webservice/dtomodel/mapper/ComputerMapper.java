package com.excilys.burleon.computerdatabase.webservice.dtomodel.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.service.util.Utility;
import com.excilys.burleon.computerdatabase.webservice.dtomodel.CompanyDto;
import com.excilys.burleon.computerdatabase.webservice.dtomodel.ComputerDto;

public class ComputerMapper {

    /**
     * To map a Computer from the view to a Computer for the services.
     *
     * @param computerDto
     *            The Dto
     * @return The computer for the service
     */
    public static Computer toComputer(final ComputerDto computerDto) {
        if (computerDto.id.equals("")) {
            computerDto.id = "0";
        }
        if (computerDto.company != null && computerDto.company.id.equals("")) {
            computerDto.company.id = "0";
        }

        final Computer computer = new Computer.ComputerBuilder().name(computerDto.name)
                .id(Long.parseLong(computerDto.id))
                .introduced(Utility.convertStringDateToLocalDateTime(computerDto.introduced))
                .discontinued(Utility.convertStringDateToLocalDateTime(computerDto.discontinued)).build();

        if (computerDto.company != null) {
            computer.setCompany(new Company.CompanyBuilder().id(Long.parseLong(computerDto.company.id))
                    .name(computerDto.company.name).build());
        }

        return computer;
    }

    /**
     * To map a Computer from the view to a Computer for the services.
     *
     * @param computer
     *            The computer for the service
     * @return The DTO
     */
    public static ComputerDto toDto(final Computer computer) {
        final ComputerDto computerDto = new ComputerDto();
        final CompanyDto companyDTO = new CompanyDto();
        computerDto.id = computer.getId() + "";
        computerDto.name = computer.getName();
        computerDto.introduced = Utility.convertToStringDate(computer.getIntroduced());
        computerDto.discontinued = Utility.convertToStringDate(computer.getDiscontinued());
        if (computer.getCompany() != null) {
            computerDto.company = companyDTO;
            companyDTO.id = computer.getCompany().getId() + "";
            companyDTO.name = computer.getCompany().getName();
        }
        return computerDto;
    }

    /**
     * To map a list of computer.
     *
     * @param computers
     *            A list of computer for service
     * @return A list of computer DTO
     */
    public static List<ComputerDto> toDto(final List<Computer> computers) {
        final List<ComputerDto> computerDtos = new ArrayList<>();
        computers.forEach(computer -> computerDtos.add(ComputerMapper.toDto(computer)));
        return computerDtos;
    }

    /**
     * To map a list of computer DTO.
     *
     * @param computerDtos
     *            A list of computer DTO
     * @return A list of computer for service
     */
    public List<Computer> toComputer(final List<ComputerDto> computerDtos) {
        final List<Computer> computers = new ArrayList<>();
        computerDtos.forEach(computerDto -> computers.add(ComputerMapper.toComputer(computerDto)));
        return computers;
    }
}
