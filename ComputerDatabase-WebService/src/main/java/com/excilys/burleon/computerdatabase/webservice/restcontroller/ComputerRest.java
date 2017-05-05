package com.excilys.burleon.computerdatabase.webservice.restcontroller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.webservice.dtomodel.ComputerDto;
import com.excilys.burleon.computerdatabase.webservice.dtomodel.mapper.ComputerMapper;

@RestController
@RequestMapping("computer")
public class ComputerRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerRest.class);

    @Autowired
    private IComputerService computerService;

    @Autowired
    private IPageService<Computer> computerPageService;

    /**
     * Allow to create a computer.
     *
     * @param computerDto
     *            The computer
     * @return The computer that was created
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createComputer(@RequestBody final ComputerDto computerDto) {
        ComputerRest.LOGGER.trace("POST /createComputer \t computerDto : " + computerDto);

        if (StringUtils.isNotBlank(computerDto.id)) {
            return new ResponseEntity<>("Your object has an id. So you should use PUT method",
                    HttpStatus.BAD_REQUEST);
        }

        final Optional<Computer> computerOpt;
        try {
            computerOpt = this.computerService.save(ComputerMapper.toComputer(computerDto));
        } catch (final ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (computerOpt.isPresent()) {
            return new ResponseEntity<>(ComputerMapper.toDto(computerOpt.get()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Impossible to return the computer updated",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Allow to delete a computer.
     *
     * @param id
     *            The id of computer that you want remove
     * @return The computer that was removed
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "{id:[\\d]+}")
    public ResponseEntity<?> deleteComputer(@PathVariable(required = false, name = "id") final Long id) {
        ComputerRest.LOGGER.trace("GET /deleteComputer \t id : " + id);
        final Optional<Computer> computerOpt = this.computerService.get(Computer.class, id);
        if (computerOpt.isPresent()) {
            if (this.computerService.remove(computerOpt.get())) {
                return new ResponseEntity<>(ComputerMapper.toDto(computerOpt.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Impossible to remove the computer", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("No computer found with the id : " + id, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Allow to get a computer by its id.
     *
     * @param id
     *            The id
     * @return The computer that matched with the id or a 404 Error
     */
    @RequestMapping(method = RequestMethod.GET, value = "{id:[\\d]+}")
    public ResponseEntity<?> getComputerById(@PathVariable(required = false, name = "id") final Long id) {
        ComputerRest.LOGGER.trace("GET /getComputerById \t id : " + id);
        final Optional<Computer> computerOpt = this.computerService.get(Computer.class, id);
        if (computerOpt.isPresent()) {
            return new ResponseEntity<>(ComputerMapper.toDto(computerOpt.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No computer found with the id : " + id, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * To get a list of computers. This list is paginate, can be reduce in
     * function of a filter word and can be ordered on different fields.
     *
     * @param filterWord
     *            The filter word
     * @param orderBy
     *            The order by
     *            <ul>
     *            <li>computer.name (Default value)</li>
     *            <li>computer.introduced</li>
     *            <li>computer.discontinued</li>
     *            <li>company.name</li>
     *            </ul>
     *            If the value is wrong it will use the default value
     * @param recordsByPage
     *            The number of records by page. Min is 1, Max is 200 (Default
     *            value : 20)
     * @param pageNumber
     *            The page number in function of the previous fields
     * @return The list of computer that matches with the request
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getComputers(
            @RequestParam(required = false, name = "filterWord", defaultValue = "") final String filterWord,
            @RequestParam(required = false, name = "orderBy", defaultValue = "computer.name") final String orderBy,
            @RequestParam(required = false, name = "recordsByPage",
                    defaultValue = "20") final Integer recordsByPage,
            @RequestParam(required = true, name = "pageNumber") final Integer pageNumber) {

        ComputerRest.LOGGER.trace("GET /getComputers \t filterWord : " + filterWord + "\t orderBy : " + orderBy
                + "\t recordsByPage : " + recordsByPage + "\t pageNumber : " + pageNumber);

        if (recordsByPage < 1 || recordsByPage > 200) {
            return new ResponseEntity<>("recordsByPage must be between 1 and 200", HttpStatus.BAD_REQUEST);
        }

        try {
            this.computerPageService.setOrderBy(OrderComputerEnum.fromName(orderBy));
        } catch (final IllegalArgumentException e) {
            return new ResponseEntity<>("order by : " + orderBy + " is not a valide value",
                    HttpStatus.BAD_REQUEST);
        }

        this.computerPageService.setFilterWord(filterWord);
        this.computerPageService.setRecordsByPage(recordsByPage);
        this.computerPageService.setModelService(this.computerService);
        final List<Computer> computers = this.computerPageService.page(Computer.class, pageNumber);

        return new ResponseEntity<>(ComputerMapper.toDto(computers), HttpStatus.OK);
    }

    /**
     * Allow to update a computer.
     *
     * @param computerDto
     *            The computer
     * @return The computer that was update
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateComputer(@RequestBody final ComputerDto computerDto) {
        ComputerRest.LOGGER.trace("PUT /updateComputer \t computerDto : " + computerDto);

        if (StringUtils.isBlank(computerDto.id)) {
            return new ResponseEntity<>("Your object hasn't an id. So you should use POST method",
                    HttpStatus.BAD_REQUEST);
        }

        final Optional<Computer> computerOpt;
        try {
            computerOpt = this.computerService.save(ComputerMapper.toComputer(computerDto));
        } catch (final ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (computerOpt.isPresent()) {
            return new ResponseEntity<>(ComputerMapper.toDto(computerOpt.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Impossible to return the computer updated",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
