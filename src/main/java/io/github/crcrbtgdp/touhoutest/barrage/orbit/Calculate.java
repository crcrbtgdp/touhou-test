package io.github.crcrbtgdp.touhoutest.barrage.orbit;

import java.util.HashMap;

class Calculate {

    HashMap<Integer, Double> memory = new HashMap<>();

    double calculate(String instruction, double num1, String str) {
        double num2;
        if (str.charAt(0) == ':') {
            num2 = memory.get(Integer.valueOf(str.substring(1)));
        } else {
            num2 = Double.parseDouble(str);
        }
        //System.out.println(instruction);
        if (instruction.equals("MW")) {
            memory.put((int)num2, num1);
        }
        return switch (instruction) {
            case "AD" -> num1+num2;
            case "SB" -> num1-num2;
            case "MT" -> num1*num2;
            case "DV" -> num1/num2;
            case "MD" -> num1%num2;
            case "SN" -> Math.sin(num2);
            case "CS" -> Math.cos(num2);
            case "TN" -> Math.tan(num2);
            case "LG" -> Math.log10(num1)/Math.log10(num2);
            case "AS" -> Math.asin(num2);
            case "AC" -> Math.acos(num2);
            case "AT" -> Math.atan(num2);
            case "AB" -> Math.abs(num2);
            case "FL" -> Math.floor(num2);
            case "CL" -> Math.ceil(num2);
            case "SG" -> Math.signum(num2);
            case "RT" -> Math.pow(num1, 1/num2);
            case "PW" -> Math.pow(num1, num2);
            case "SH" -> Math.sinh(num2);
            case "CH" -> Math.cosh(num2);
            case "TH" -> Math.tanh(num2);
            case "CC" -> 1/Math.sin(num2);
            case "SC" -> 1/Math.cos(num2);
            case "CT" -> 1/Math.tan(num2);
            case "MW" -> num1;
            default -> 0;
        };
    }
}
