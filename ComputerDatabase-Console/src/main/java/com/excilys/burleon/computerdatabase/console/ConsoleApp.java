package com.excilys.burleon.computerdatabase.console;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excilys.burleon.computerdatabase.console.exception.RestClientException;
import com.excilys.burleon.computerdatabase.console.model.Company;
import com.excilys.burleon.computerdatabase.console.model.Computer;

public class ConsoleApp {

    private static final Scanner SCAN = new Scanner(System.in);
    private static int choiceMainMenu;
    private static final int NB_ELEMENT = 10;
    private static int computerPageNumber = 1;
    private static int companyPageNumber = 1;

    /**
     * To clear the console.
     */
    public static void clearConsole() {

    }

    /**
     * The main function.
     *
     * @param args
     *            the args
     * @throws IOException
     *             The IOException
     */
    public static void main(final String[] args) throws IOException {
        final ConsoleApp ConsoleApp = new ConsoleApp();
        ConsoleApp.start(args);
    }

    private void displayListCompany(final List<Company> companyList) {
        companyList.forEach(company -> System.out.println(" " + company.id + "\t" + company.name));

    }

    private void displayListComputer(final List<Computer> computerList) {
        computerList.forEach(computer -> System.out.println(" " + computer.id + "\t" + computer.name));
    }

    /**
     * To display the menu and get the user choice.
     *
     * @param menuToDisplay
     *            The menu shown to the user
     * @return The menu choice done by the user
     */
    public int displayMenyAndGetUserChoice(final StringBuilder menuToDisplay) {
        ConsoleApp.clearConsole();
        System.out.println(menuToDisplay);
        boolean isContinue = true;
        int choice = 0;
        do {
            final String choiceString = ConsoleApp.SCAN.nextLine();
            final Pattern pattern = Pattern.compile("(\\d+)");
            final Matcher matcher = pattern.matcher(choiceString);
            if (matcher.find()) {
                if (!matcher.group(1).equalsIgnoreCase("")) {
                    choice = Integer.valueOf(matcher.group(1));
                    isContinue = false;
                }
            }
            ConsoleApp.clearConsole();
        } while (isContinue);
        return choice;
    }

    /**
     * Get a date with the format : yyyy-MM-dd.
     *
     * @return The localdatetime provide by the user
     */
    public LocalDateTime inputDate() {
        boolean isContinue = true;
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datee = null;
        LocalDateTime date = null;
        while (isContinue) {
            final String line = ConsoleApp.SCAN.nextLine();
            if (!line.trim().isEmpty()) {
                try {
                    datee = LocalDate.parse(line);
                    isContinue = false;
                    date = LocalDateTime.of(datee, LocalTime.now());
                } catch (final DateTimeParseException e) {
                    System.out.println("Sorry, that's not valid. Please try again.");
                }
            } else {
                isContinue = false;
            }
        }
        return date;
    }

    /**
     * Get a long.
     *
     * @return a long
     */
    private long inputLong() {
        boolean isContinue = true;
        long input = 0;
        do {
            final String inputLong = ConsoleApp.SCAN.nextLine();
            final Pattern pattern = Pattern.compile("(\\d+)");
            final Matcher matcher = pattern.matcher(inputLong);
            if (!inputLong.equals("")) {
                if (matcher.find()) {
                    if (!matcher.group(1).equalsIgnoreCase("")) {
                        input = Long.valueOf(matcher.group(1));
                        isContinue = false;
                    }
                }
            } else {
                isContinue = false;
            }
        } while (isContinue);
        return input;
    }

    /**
     * To display List of Computer or Company and there menu.
     */
    public void showListAndMenu() {
        boolean isContinue = true;
        while (isContinue) {
            StringBuilder sb = new StringBuilder();
            sb.append("\t1. Next Page\n");
            sb.append("\t2. Previous page\n");
            sb.append("\t3. Show page x\n");
            sb.append("\t0. Pevious menu\n");
            if (ConsoleApp.choiceMainMenu == 1) {
                this.displayListComputer(
                        RestClient.getComputers(ConsoleApp.NB_ELEMENT, ConsoleApp.computerPageNumber));
            } else if (ConsoleApp.choiceMainMenu == 2) {
                this.displayListCompany(
                        RestClient.getCompanies(ConsoleApp.NB_ELEMENT, ConsoleApp.companyPageNumber));
            }

            final int choice = this.displayMenyAndGetUserChoice(sb);

            switch (choice) {
                case 1:
                    if (ConsoleApp.choiceMainMenu == 1) {
                        ConsoleApp.computerPageNumber++;
                    } else if (ConsoleApp.choiceMainMenu == 2) {
                        ConsoleApp.companyPageNumber++;
                    }
                    break;
                case 2:
                    if (ConsoleApp.choiceMainMenu == 1) {
                        ConsoleApp.computerPageNumber--;
                    } else if (ConsoleApp.choiceMainMenu == 2) {
                        ConsoleApp.companyPageNumber--;
                    }
                    break;
                case 3:
                    sb = new StringBuilder();
                    sb.append("\tNumber of page : ");
                    final int pageNumber = this.displayMenyAndGetUserChoice(sb);
                    if (ConsoleApp.choiceMainMenu == 1) {
                        ConsoleApp.computerPageNumber = pageNumber;
                    } else if (ConsoleApp.choiceMainMenu == 2) {
                        ConsoleApp.companyPageNumber = pageNumber;
                    }
                    break;
                case 0:
                    isContinue = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void start(final String[] args) {

        boolean isContinue = true;
        boolean isContinueSubMenu = false;
        while (isContinue) {
            StringBuilder sb = new StringBuilder();
            sb.append("\t1. List computers\n");
            sb.append("\t2. List companies\n");
            sb.append("\t3. Show computer details\n");
            sb.append("\t4. Create a computer\n");
            sb.append("\t5. Update a computer\n");
            sb.append("\t6. Delete a computer\n");
            sb.append("\t7. Delete a company (And computer associate)\n");
            sb.append("\t0. Exit\n");
            ConsoleApp.choiceMainMenu = this.displayMenyAndGetUserChoice(sb);

            Optional<Computer> computerOpt;
            switch (ConsoleApp.choiceMainMenu) {
                case 1:
                    this.showListAndMenu();
                    break;
                case 2:
                    this.showListAndMenu();
                    break;
                case 3:
                    try {
                        sb = new StringBuilder();
                        sb.append("Computer ID :\n");
                        final Optional<Computer> computerOpt1 = RestClient.getEntity(Computer.class,
                                this.displayMenyAndGetUserChoice(sb));
                        sb = new StringBuilder();
                        sb.append("Detail of : " + computerOpt1.get().getId() + "\n");
                        sb.append("Name : " + computerOpt1.get().getName() + "\n");
                        sb.append("Introduce date : " + computerOpt1.get().getIntroduced() + "\n");
                        sb.append("Discontinue date : " + computerOpt1.get().getDiscontinued() + "\n");
                        final Company company = computerOpt1.get().getCompany();
                        if (company != null) {
                            sb.append("Linked with the company : " + company.getName() + "\n\n");
                        }
                        System.out.println(sb);
                    } catch (final RestClientException e1) {
                        System.out.println(
                                "!!!  " + e1.getMessage().substring(e1.getMessage().indexOf(':') + 1) + "  !!!");
                    }
                    break;
                case 4:
                    do {
                        isContinueSubMenu = false;
                        final Computer computer = new Computer();
                        System.out.print("Computer creation\n");
                        System.out.print("Name :");
                        computer.setName(ConsoleApp.SCAN.nextLine());
                        System.out.print("Company Id :");
                        final long companyId = this.inputLong();
                        if (companyId != 0) {
                            final Company company = new Company();
                            company.setId(companyId + "");
                            computer.setCompany(company);
                        }
                        System.out.print("Introduce date (yyyy-MM-dd) :");
                        LocalDateTime date = this.inputDate();
                        if (date != null) {
                            computer.setIntroduced(date.toLocalDate().toString());
                        }
                        System.out.print("Discontinue date (yyyy-MM-dd) :");
                        date = this.inputDate();
                        if (date != null) {
                            computer.setDiscontinued(date.toLocalDate().toString());
                        }
                        try {
                            RestClient.createComputer(computer);
                        } catch (final RestClientException e) {
                            System.out.println(
                                    "!!!  " + e.getMessage().substring(e.getMessage().indexOf(':') + 1) + "  !!!");
                            isContinueSubMenu = true;
                        }
                    } while (isContinueSubMenu);
                    System.out.println("The computer has been create");
                    break;
                case 5:
                    System.out.print("Computer update ID :");
                    try {
                        computerOpt = RestClient.getEntity(Computer.class, this.inputLong());
                        do {
                            isContinueSubMenu = false;
                            System.out.print("Name :");
                            computerOpt.get().setName(ConsoleApp.SCAN.nextLine());
                            System.out.print("Company Id :");
                            final long companyId = this.inputLong();
                            if (companyId != 0) {
                                final Company company = new Company();
                                company.setId(companyId + "");
                                computerOpt.get().setCompany(company);
                            }
                            System.out.print("Introduce date (yyyy-MM-dd) :");
                            LocalDateTime date = this.inputDate();
                            if (date != null) {
                                computerOpt.get().setIntroduced(date.toLocalDate().toString());
                            }
                            System.out.print("Discontinue date (yyyy-MM-dd) :");
                            date = this.inputDate();
                            if (date != null) {
                                computerOpt.get().setDiscontinued(date.toLocalDate().toString());
                            }

                            try {
                                RestClient.updateComputer(computerOpt.get());
                            } catch (final RestClientException e) {
                                System.out.println("!!!  "
                                        + e.getMessage().substring(e.getMessage().indexOf(':') + 1) + "  !!!");
                                isContinueSubMenu = true;
                            }
                        } while (isContinueSubMenu);
                        System.out.println("The computer has been saved");
                    } catch (final RestClientException e1) {
                        System.out.println(
                                "!!!  " + e1.getMessage().substring(e1.getMessage().indexOf(':') + 1) + "  !!!");
                    }
                    break;
                case 6:
                    try {
                        sb = new StringBuilder();
                        sb.append("Remove computer ID :\n");
                        computerOpt = RestClient.getEntity(Computer.class, this.displayMenyAndGetUserChoice(sb));
                        RestClient.removeEntity(Computer.class, Long.valueOf(computerOpt.get().getId()));
                        sb = new StringBuilder();
                        sb.append("The computer has been deleted");
                        System.out.println(sb);
                    } catch (final RestClientException e1) {
                        System.out.println(
                                "!!!  " + e1.getMessage().substring(e1.getMessage().indexOf(':') + 1) + "  !!!");
                    }
                    break;
                case 7:
                    try {
                        sb = new StringBuilder();
                        sb.append("Remove company ID :\n");
                        final Optional<Company> companyOpt = RestClient.getEntity(Company.class,
                                this.displayMenyAndGetUserChoice(sb));
                        RestClient.removeEntity(Company.class, Long.valueOf(companyOpt.get().getId()));
                        sb = new StringBuilder();
                        sb.append("The company has been deleted");
                        System.out.println(sb);
                    } catch (final RestClientException e1) {
                        System.out.println(
                                "!!!  " + e1.getMessage().substring(e1.getMessage().indexOf(':') + 1) + "  !!!");
                    }
                    break;
                case 0:
                    isContinue = false;
                    System.out.println("------- APP IS CLOSED -------");
                    break;
                default:
                    break;
            }
        }
    }
}
