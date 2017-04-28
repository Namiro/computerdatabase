package com.excilys.burleon.computerdatabase.webapp.dtomodel.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.service.util.Utility;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.CompanyDTO;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.ComputerDTO;

public class ComputerMapper {

    /**
     * To map a Computer from the view to a Computer for the services.
     *
     * @param computerDTO
     *            The DTO
     * @return The computer for the service
     */
    public static Computer toComputer(final ComputerDTO computerDTO) {
        if (computerDTO.id.equals("")) {
            computerDTO.id = "0";
        }
        if (computerDTO.company != null && computerDTO.company.id.equals("")) {
            computerDTO.company.id = "0";
        }

        final Computer computer = new Computer.ComputerBuilder().name(computerDTO.name)
                .id(Long.parseLong(computerDTO.id))
                .introduced(Utility.convertStringDateToLocalDateTime(computerDTO.introduced))
                .discontinued(Utility.convertStringDateToLocalDateTime(computerDTO.discontinued)).build();

        if (computerDTO.company != null) {
            computer.setCompany(new Company.CompanyBuilder().id(Long.parseLong(computerDTO.company.id))
                    .name(computerDTO.company.name).build());
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
    public static ComputerDTO toComputerDTO(final Computer computer) {
        final ComputerDTO computerDTO = new ComputerDTO();
        final CompanyDTO companyDTO = new CompanyDTO();
        computerDTO.id = computer.getId() + "";
        computerDTO.name = computer.getName();
        computerDTO.introduced = Utility.convertToStringDate(computer.getIntroduced());
        computerDTO.discontinued = Utility.convertToStringDate(computer.getDiscontinued());
        if (computer.getCompany() != null) {
            computerDTO.company = companyDTO;
            companyDTO.id = computer.getCompany().getId() + "";
            companyDTO.name = computer.getCompany().getName();
        }
        return computerDTO;
    }

    /**
     * To map a list of computer.
     *
     * @param computers
     *            A list of computer for service
     * @return A list of computer DTO
     */
    public static List<ComputerDTO> toComputerDTO(final List<Computer> computers) {
        final List<ComputerDTO> computerDTOs = new ArrayList<>();
        computers.forEach(computer -> computerDTOs.add(ComputerMapper.toComputerDTO(computer)));
        return computerDTOs;
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
        computerDTOs.forEach(computerDTO -> computers.add(ComputerMapper.toComputer(computerDTO)));
        return computers;
    }
}
