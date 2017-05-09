package com.excilys.burleon.computerdatabase.webservice.restcontroller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderCompanyEnum;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.webservice.dtomodel.mapper.CompanyMapper;

@RestController
@RequestMapping("company")
public class CompanyRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRest.class);

    @Autowired
    ICompanyService companyService;

    @Autowired
    private IPageService<Company> companyPageService;

    /**
     * Allow to delete a company.
     *
     * @param id
     *            The id of company that you want remove
     * @return The company that was removed
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "{id:[\\d]+}")
    public ResponseEntity<?> deleteCompany(@PathVariable(required = false, name = "id") final Long id) {
        CompanyRest.LOGGER.trace("GET /deleteCompany \t id : " + id);
        final Optional<Company> companyOpt = this.companyService.get(Company.class, id);
        if (companyOpt.isPresent()) {
            if (this.companyService.remove(companyOpt.get())) {
                return new ResponseEntity<>(CompanyMapper.toDto(companyOpt.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Impossible to remove the company", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("No company found with the id : " + id, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * To get a list of companies. This list is paginate, can be reduce in
     * function of a filter word and can be ordered on different fields.
     *
     * @param filterWord
     *            The filter word
     * @param orderBy
     *            The order by
     *            <ul>
     *            <li>company.name (Default value)</li>
     *            </ul>
     *            If the value is wrong it will use the default value
     * @param recordsByPage
     *            The number of records by page. Min is 1, Max is 200 (Default
     *            value : 20)
     * @param pageNumber
     *            The page number in function of the previous fields
     * @return The list of companies that matches with the request
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getCompanies(
            @RequestParam(required = false, name = "filterWord", defaultValue = "") final String filterWord,
            @RequestParam(required = false, name = "orderBy", defaultValue = "company.name") final String orderBy,
            @RequestParam(required = false, name = "recordsByPage",
                    defaultValue = "20") final Integer recordsByPage,
            @RequestParam(required = true, name = "pageNumber") final Integer pageNumber) {

        CompanyRest.LOGGER.trace("GET /getCompanies \t filterWord : " + filterWord + "\t orderBy : " + orderBy
                + "\t recordsByPage : " + recordsByPage + "\t pageNumber : " + pageNumber);

        if (recordsByPage < 1 || recordsByPage > 200) {
            return new ResponseEntity<>("recordsByPage must be between 1 and 200", HttpStatus.BAD_REQUEST);
        }

        try {
            this.companyPageService.setOrderBy(OrderCompanyEnum.fromName(orderBy));
        } catch (final IllegalArgumentException e) {
            return new ResponseEntity<>("order by : " + orderBy + " is not a valide value",
                    HttpStatus.BAD_REQUEST);
        }

        this.companyPageService.setFilterWord(filterWord);
        this.companyPageService.setRecordsByPage(recordsByPage);
        this.companyPageService.setModelService(this.companyService);
        final List<Company> computers = this.companyPageService.page(Company.class, pageNumber);

        return new ResponseEntity<>(CompanyMapper.toDto(computers), HttpStatus.OK);
    }

    /**
     * Allow to get a company by its id.
     *
     * @param id
     *            The id
     * @return The company that matched with the id or a 404 Error
     */
    @RequestMapping(method = RequestMethod.GET, value = "{id:[\\d]+}")
    public ResponseEntity<?> getCompanyById(@PathVariable(required = false, name = "id") final Long id) {
        CompanyRest.LOGGER.trace("GET /getCompanyById \t id : " + id);
        final Optional<Company> companyOpt = this.companyService.get(Company.class, id);
        if (companyOpt.isPresent()) {
            return new ResponseEntity<>(CompanyMapper.toDto(companyOpt.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No company found with the id : " + id, HttpStatus.NOT_FOUND);
        }
    }
}
