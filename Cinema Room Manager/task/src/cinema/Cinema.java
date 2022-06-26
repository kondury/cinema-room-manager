package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    private static final String ROWS_PROMPT_STR = "Enter the number of rows:";
    private static final String SEATS_PROMPT_STR = "Enter the number of seats in each row:";
    private static final String CHOOSE_ROW_STR = "Enter a row number:";
    private static final String CHOOSE_SEAT_STR = "Enter a seat number in that row:";
    private static final String WRONG_INPUT_STR = "Wrong input!";
    private static final String TICKET_ALREADY_PURCHASED = "That ticket has already been purchased!";
    private static final String PRICE_TMPL_STR = "Ticket price: $%d\n";
    private static final String MENU_STR = "1. Show the seats\n" +
                                           "2. Buy a ticket\n" +
                                           "3. Statistics\n" +
                                           "0. Exit";
    private static final String NUMBER_OF_PURCHASED_TICKETS_TMPL_STR = "Number of purchased tickets: %d\n";
    private static final String PERCENTAGE_TMPL_STR = "Percentage: %.2f%%\n";
    private static final String CURRENT_INCOME_TMPL_STR = "Current income: $%d\n";
    private static final String TOTAL_INCOME_TMPL_STR = "Total income: $%d\n";
    private static final int INPUT_ACTION_STATE = 100;
    private static final int SHOW_CINEMA_ROOM_STATE = 1;
    private static final int BUY_TICKET_STATE = 2;
    private static final int SHOW_STATISTICS_STATE = 3;
    private static final int EXIT_STATE = 0;

    private static char[][] auditorium;
    private static int totalRows;
    private static int seatsInRow;
    private static int purchasedTickets = 0;
    private static int currentIncome = 0;

    public static void main(String[] args) {
        prepareCinema();
        int state = INPUT_ACTION_STATE;
        while (state != EXIT_STATE) {
            switch (state) {
                case INPUT_ACTION_STATE:
                    state = getNextAction();
                    break;
                case SHOW_CINEMA_ROOM_STATE:
                    printAuditorium();
                    state = INPUT_ACTION_STATE;
                    break;
                case BUY_TICKET_STATE:
                    buyTicket();
                    state = INPUT_ACTION_STATE;
                    break;
                case SHOW_STATISTICS_STATE:
                    printStatistics();
                    state = INPUT_ACTION_STATE;
                    break;
            }
        }
    }

    private static void printStatistics() {
        System.out.printf(NUMBER_OF_PURCHASED_TICKETS_TMPL_STR, purchasedTickets);
        System.out.printf(PERCENTAGE_TMPL_STR, (double) 100 * purchasedTickets / getTotalSeats());
        System.out.printf(CURRENT_INCOME_TMPL_STR, currentIncome);
        System.out.printf(TOTAL_INCOME_TMPL_STR, getTotalIncome());
        System.out.println();
    }

    private static int getNextAction() {
        int action = readWithPrompt(MENU_STR);
        System.out.println();
        return action;
    }

    private static void prepareCinema() {
        int totalRows = readWithPrompt(ROWS_PROMPT_STR);
        int seatsInRow = readWithPrompt(SEATS_PROMPT_STR);
        System.out.println();
        initAuditorium(totalRows, seatsInRow);
    }

    private static void initAuditorium(int _totalRows, int _seatsInRow) {
        totalRows = _totalRows;
        seatsInRow = _seatsInRow;
        auditorium = new char[totalRows][seatsInRow];
        for (char[] row : auditorium) {
            Arrays.fill(row, 'S');
        }
    }

    private static void buyTicket() {
        while (true) {
            int chosenRow = readWithPrompt(CHOOSE_ROW_STR);
            int chosenSeat = readWithPrompt(CHOOSE_SEAT_STR);
            System.out.println();
            if (chosenRow < 0 || chosenRow > totalRows || chosenSeat < 0 || chosenSeat > seatsInRow) {
                System.out.println(WRONG_INPUT_STR);
                System.out.println();
            } else if (!isEmptySeat(chosenRow, chosenSeat)) {
                System.out.println(TICKET_ALREADY_PURCHASED);
                System.out.println();
            } else {
                bookSeat(chosenRow, chosenSeat);
                printTicketPrice(chosenRow);
                purchasedTickets++;
                currentIncome += getTicketPrice(chosenRow);
                break;
            }
        }
        System.out.println();
    }

    private static boolean isEmptySeat(int row, int seat) {
        return auditorium[row - 1][seat - 1] == 'S';
    }

    private static void bookSeat(int row, int seat) {
        auditorium[row - 1][seat - 1] = 'B';
    }

    private static int getTicketPrice(int row) {
        return (getTotalSeats() <= 60 || row <= totalRows / 2) ? 10 : 8;
    }

    private static void printTicketPrice(int row) {
        System.out.printf(PRICE_TMPL_STR, getTicketPrice(row));
    }

    private static void printAuditorium() {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 0; i < seatsInRow; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();

        for (int j = 0; j < totalRows; j++) {
            System.out.print(j + 1);
            for (int i = 0; i < seatsInRow; i++) {
                System.out.print(" " + auditorium[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int readWithPrompt(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static int getTotalSeats() {
        return totalRows * seatsInRow;
    }

    private static int getTotalIncome() {
        return (getTotalSeats() <= 60) ? getTotalSeats() * 10 : seatsInRow * (totalRows / 2 * 18 + totalRows % 2 * 8);
    }
}