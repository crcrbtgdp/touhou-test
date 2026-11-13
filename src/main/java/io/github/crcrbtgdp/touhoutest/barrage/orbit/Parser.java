package io.github.crcrbtgdp.touhoutest.barrage.orbit;

import java.io.IOException;

public class Parser {

    private final String[] barrages = new String[2];

    public Parser() {
        try {
            ReadFile readFile = new ReadFile();
            barrages[0] = readFile.read("x.txt");
            barrages[1] = readFile.read("y.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double[] calculate(double t, double r) {

        double[] nums = new double[2];

        for (int i = 0; i < 2; i++) {
            String barrage = barrages[i];
            int index = barrage.indexOf('>') + 1;
            double num = 0;
            Calculate calculate = new Calculate();

            do {
                String instruction = barrage.substring(index, index + 2);
                index += 2;
                StringBuilder operand = new StringBuilder();

                while (index < barrage.length()) {
                    char c = barrage.charAt(index);
                    if (!((c >= '0' && c <= ':') || c == '-' || c == 't' || c == 'r' || c == '.')) {
                        break;
                    }
                    operand.append(c);
                    index++;
                }

                if (index + 1 == barrage.length()) {
                    operand.append(barrage.charAt(index));
                }
                String value = operand.toString();
                num = calculate.calculate(instruction, num,
                        value.equals("t") ? Double.toString(t) : value.equals("r") ? Double.toString(r) : value);
            } while (index + 1 < barrage.length());
            nums[i] = num;
        }
        return nums;
    }
}
