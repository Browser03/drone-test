package br.com.drone.dronetest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Drone {

    public String changePosition(String str) {

        if (str == null || str.trim().isEmpty())
            return defaultError();

        List<String> chars = Arrays.asList(str.split(""));
        List<String> commands = new ArrayList<String>();

        String strCommand = "";

        for (String c : chars) {
            if (!isNumeric(c)) {
                if (isNumeric(strCommand))
                    return defaultError();
                commands.add(strCommand);
                strCommand = "";
            }
            strCommand += c;
        }

        if (strCommand != "") {
            if (isNumeric(strCommand))
                return defaultError();
            commands.add(strCommand);
        }

        List<String> newCommands = new ArrayList<String>();

        for (int i = 0; i < commands.size(); i++) {

            var command = commands.get(i);

            if (getCommand(command).equals("X")) {

                if (getTimes(command) > 1) {
                    return defaultError();
                }

                if (newCommands.size() > 1)
                    newCommands.remove(newCommands.size() - 1);
            } else {
                newCommands.add(command);
            }
        }
        commands = newCommands;

        int x = 0;
        int y = 0;

        Map<String, Integer> map = new Hashtable<String, Integer>();

        map.put("N", 0);
        map.put("S", 0);
        map.put("L", 0);
        map.put("O", 0);

        for (String command : commands) {

            switch (getCommand(command)) {
                case "N":
                case "S":
                case "L":
                case "O":
                    var times = map.get(getCommand(command)) + getTimes(command);
                    if (times < 1 || times > 2147483647)
                        return defaultError();
                    map.put(getCommand(command), map.get(getCommand(command)) + getTimes(command));
                    break;
                case "":
                    break;
                default:
                    return defaultError();
            }

        }

        y = map.get("N") - map.get("S");
        x = map.get("L") - map.get("O");

        return "(" + x + ", " + y + ")";

    }

    private boolean isNumeric(String c) {
        try {
            Integer.parseInt(c);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private int getTimes(String c) {

        var arrCommand = Arrays.asList(c.split("", 2));

        if (arrCommand.size() > 1) {
            var strTimes = arrCommand.get(1);
            try {
                return Integer.parseInt(strTimes);
            } catch (Exception e) {
                return 1;
            }
        }

        return 1;

    }

    private String getCommand(String c) {
        return c.split("")[0];
    }

    private String defaultError() {
        return "(999, 999)";
    }

}