package seedu.duke.parser;

import static seedu.duke.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.duke.commands.Command;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.GoalCommand;
import seedu.duke.commands.HelpCommand;
import seedu.duke.commands.IncorrectCommand;
import seedu.duke.commands.logcommands.LogCommand;
import seedu.duke.commands.logcommands.DeleteLogCommand;
import seedu.duke.commands.logcommands.ViewLogCommand;
import seedu.duke.commands.meal.MealCommand;
import seedu.duke.data.exception.IllegalValueException;

/**
 * Parses user input.
 */
public class Parser {

    public static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");
    // one or more keywords separated by whitespace

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

            // case AddCommand.COMMAND_WORD:
            // return prepareAdd(arguments);
            //
            // case DeleteCommand.COMMAND_WORD:
            // return prepareDelete(arguments);
            //
            // case ClearCommand.COMMAND_WORD:
            // return new ClearCommand();
            //
            // case FindCommand.COMMAND_WORD:
            // return prepareFind(arguments);
            //
            // case ListCommand.COMMAND_WORD:
            // return new ListCommand();
            //
            // case ViewCommand.COMMAND_WORD:
            // return prepareView(arguments);
            //
            // case ViewAllCommand.COMMAND_WORD:
            // return prepareViewAll(arguments);
            //

            case LogCommand.COMMAND_WORD:
                return new LogCommand(Arrays.asList(arguments.trim().split(" ")));

            case DeleteLogCommand.COMMAND_WORD:
                return new DeleteLogCommand(Arrays.asList(arguments.trim().split(" ")));

            case ViewLogCommand.COMMAND_WORD:
                return new ViewLogCommand(Arrays.asList(arguments.trim().split(" ")));

            case MealCommand.COMMAND_WORD:
                return new MealCommand(Arrays.asList(arguments.trim().split(" ")));

            case GoalCommand.COMMAND_WORD:
                return new GoalCommand(userInput);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD: // Fallthrough

            default:
                return new HelpCommand();
        }
    }

    // /**
    // * Parses arguments in the context of the add person command.
    // *
    // * @param args full command args string
    // * @return the prepared command
    // */
    // private Command prepareAdd(String args) {
    // final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
    // // Validate arg string format
    // if (!matcher.matches()) {
    // return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
    // AddCommand.MESSAGE_USAGE));
    // }
    // try {
    // return new AddCommand(
    // matcher.group("name"),
    //
    // matcher.group("phone"),
    // isPrivatePrefixPresent(matcher.group("isPhonePrivate")),
    //
    // matcher.group("email"),
    // isPrivatePrefixPresent(matcher.group("isEmailPrivate")),
    //
    // matcher.group("address"),
    // isPrivatePrefixPresent(matcher.group("isAddressPrivate")),
    //
    // getTagsFromArgs(matcher.group("tagArguments"))
    // );
    // } catch (IllegalValueException ive) {
    // return new IncorrectCommand(ive.getMessage());
    // }
    // }

    /**
     * Returns true if the private prefix is present for a contact detail in the add
     * command's arguments string.
     */
    private static boolean isPrivatePrefixPresent(String matchedPrefix) {
        return matchedPrefix.equals("p");
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses the given arguments string as a single index number.
     *
     * @param args arguments string to parse as index number
     * @return the parsed index number
     * @throws ParseException        if no region of the args string could be found
     *                               for the index
     * @throws NumberFormatException the args string region is not a valid number
     */
    private int parseArgsAsDisplayedIndex(String args) throws ParseException, NumberFormatException {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find index number to parse");
        }
        return Integer.parseInt(matcher.group("targetIndex"));
    }

    /**
     * Signals that the user input could not be parsed.
     */
    public static class ParseException extends Exception {
        ParseException(String message) {
            super(message);
        }
    }

}
