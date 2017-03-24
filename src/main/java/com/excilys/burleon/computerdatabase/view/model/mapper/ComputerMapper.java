package com.excilys.burleon.computerdatabase.view.model.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.tool.Utility;
import com.excilys.burleon.computerdatabase.view.model.CompanyDTO;
import com.excilys.burleon.computerdatabase.view.model.ComputerDTO;

public enum ComputerMapper {

    INSTANCE;

    /**
     * Default constructor.
     */
    ComputerMapper() {

    }

    /**
     * To map a Computer from the view to a Computer for the services.
     *
     * @param computerDTO
     *            The DTO
     * @return The computer for the service
     */
    public Computer toComputer(final ComputerDTO computerDTO) {
        if (computerDTO.id.equals("")) {
            computerDTO.id = "0";
        }
        if (computerDTO.company.id.equals("")) {
            computerDTO.company.id = "0";
        }
        return new Computer.ComputerBuilder().name(computerDTO.name).id(Long.parseLong(computerDTO.id))
                .introduced(Utility.convertStringDateToLocalDateTime(computerDTO.introduced))
                .discontinued(Utility.convertStringDateToLocalDateTime(computerDTO.discontinued))
                .company(new Company.CompanyBuilder().id(Long.parseLong(computerDTO.company.id))
                        .name(computerDTO.company.name).build())
                .build();
    }

    /**
     * To map a list of computer DTO.
     *
     * @param computerDTOs
     *            A list of computer DTO
     * @return A list of computer for service
     */
    public List<Computer> toComputer(final List<ComputerDTO> computerDTOs) {
        final List<Computer> computers = new ArrayList<>();
        computerDTOs.forEach(computerDTO -> computers.add(ComputerMapper.INSTANCE.toComputer(computerDTO)));
        return computers;
    }

    /**
     * To map a Computer from the view to a Computer for the services.
     *
     * @param computer
     *            The computer for the service
     * @return The DTO
     */
    public ComputerDTO toComputerDTO(final Computer computer) {
        final ComputerDTO computerDTO = new ComputerDTO();
        final CompanyDTO companyDTO = new CompanyDTO();
        computerDTO.id = computer.getId() + "";
        computerDTO.name = computer.getName();
        computerDTO.introduced = Utility.convertToStringDate(computer.getIntroduced());
        computerDTO.discontinued = Utility.convertToStringDate(computer.getDiscontinued());
        computerDTO.company = companyDTO;
        companyDTO.id = computer.getCompany().getId() + "";
        companyDTO.name = computer.getCompany().getName();
        return computerDTO;
    }

    /**
     * To map a list of computer.
     *
     * @param computers
     *            A list of computer for service
     * @return A list of computer DTO
     */
    public List<ComputerDTO> toComputerDTO(final List<Computer> computers) {
        final List<ComputerDTO> computerDTOs = new ArrayList<>();
        computers.forEach(computer -> computerDTOs.add(ComputerMapper.INSTANCE.toComputerDTO(computer)));
        return computerDTOs;
    }
}