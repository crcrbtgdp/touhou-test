package io.github.crcrbtgdp.touhoutest.barrage.orbit;

import java.io.IOException;

public class Parser {

    ReadFile rf = new ReadFile();
    String barragesX;
    String barragesY;

    public Parser() {
        try {
            barragesX = rf.read("testx.txt");
            barragesY = rf.read("testy.txt");
            //System.out.println(barragesX);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double[] calculate(double t, double r) {

        char c;
        int index;
        double num;
        double[] nums = new double[2];

        for (int i = 0; i < 2; i++) {
            c = 0;
            index = 0;
            while (c != '>') {//(i==0?
                index++;
                c = i==0?barragesX.charAt(index):barragesY.charAt(index);
            }
            index++;
            num = 0;
            String instruction;
            Calculate calculate = new Calculate();

            do {
                instruction = i==0?barragesX.substring(index, index + 2):barragesY.substring(index, index + 2);
                index += 2;
                String num2 = "";

                do {
                    num2 += i == 0 ? barragesX.substring(index, index + 1) : barragesY.substring(index, index + 1);
                    index++;
                    c = i == 0 ? barragesX.charAt(index) : barragesY.charAt(index);
                } while (index + 1 < (i == 0 ? barragesX.length() : barragesY.length()) && ((c >= '0' && c <= ':') || c == 't' || c == 'r' || c == '.'));
                if (index+1==(i==0?barragesX.length():barragesY.length())) {
                    num2 += i == 0 ? barragesX.substring(index, index + 1) : barragesY.substring(index, index + 1);
                }
                num = calculate.calculate(instruction, num, num2.equals("t") ? Double.toString(t) : (num2.equals("r") ? Double.toString(r) : num2));
                //System.out.println(num);
            } while (index+1 < (i==0?barragesX.length():barragesY.length()));
            nums[i] = num;
        }
        return nums;
    }
}
