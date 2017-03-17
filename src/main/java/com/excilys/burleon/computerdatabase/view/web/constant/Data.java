
package com.excilys.burleon.computerdatabase.view.web.constant;

/**
 * The constants used in JSP pages and Servlets.
 *
 * @author Junior Burleon
 *
 */
public final class Data {

    /* LIST */
    public static final String LIST_COMPUTER = "listComputer";

    /* COMPUTER */
    public static final String COMPUTER = "computer";
    public static final String COMPUTER_ID = "computer_id";
    public static final String COMPUTER_NAME = "computer_name";
    public static final String COMPUTER_INTRODUCE_DATE = "computer_introduce_date";
    public static final String COMPUTER_DISCONTINUE_DATE = "computer_discontinue_date";
    public static final String COMPUTER_COMPANY_ID = "computer_company_id";
    public static final String COMPUTER_COMPANY_NAME = "computer_company_name";

    /* ALL */
    public static final String MESSAGE_SUCCESS = "successMessage";
    public static final String MESSAGE_ERROR = "errorMessage";
    public static final String MESSAGE_DEFAULT = "defaultMessage";
    public static final String MESSAGE_INFO = "infoMessage";
    public static final String MESSAGE_WARRNING = "warrningMessage";
    public static final String SUBMIT_CREATE = "sumbitCreate";
    public static final String SUBMIT_SAVE = "submtiSave";
    public static final String SUBMIT_DELETE = "submitDelete";

    public static String getComputer() {
        return Data.COMPUTER;
    }

    public static String getComputerCompanyId() {
        return Data.COMPUTER_COMPANY_ID;
    }

    public static String getComputerCompanyName() {
        return Data.COMPUTER_COMPANY_NAME;
    }

    public static String getComputerDiscontinueDate() {
        return Data.COMPUTER_DISCONTINUE_DATE;
    }

    public static String getComputerId() {
        return Data.COMPUTER_ID;
    }

    public static String getComputerIntroduceDate() {
        return Data.COMPUTER_INTRODUCE_DATE;
    }

    public static String getComputerName() {
        return Data.COMPUTER_NAME;
    }

    public static String getListComputer() {
        return Data.LIST_COMPUTER;
    }

    public static String getMessageDefault() {
        return Data.MESSAGE_DEFAULT;
    }

    public static String getMessageError() {
        return Data.MESSAGE_ERROR;
    }

    public static String getMessageInfo() {
        return Data.MESSAGE_INFO;
    }

    public static String getMessageSuccess() {
        return Data.MESSAGE_SUCCESS;
    }

    public static String getMessageWarrning() {
        return Data.MESSAGE_WARRNING;
    }

    public static String getSubmitCreate() {
        return Data.SUBMIT_CREATE;
    }

    public static String getSubmitDelete() {
        return Data.SUBMIT_DELETE;
    }

    public static String getSubmitSave() {
        return Data.SUBMIT_SAVE;
    }

}
