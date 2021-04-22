package ru.razuvaev.android_one.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ru.razuvaev.android_one.MainContract;

public class ReversePolishNotation {

    private static final HashMap<String, Integer> priority = new HashMap<String, Integer>() {
        {
            put("%", 5);
            put("^", 4);
            put("*", 3);
            put("/", 3);
            put("+", 2);
            put("-", 2);
            put("(", 1);
            put(")", 1);
        }
    };
    private static final String actions = "%^*/+-()";
    private static final String briefActions = "%^*/+-";

    public static String calculateExpression(String stringArithmeticExpression) {

        if (stringArithmeticExpression.isEmpty()) {
            return "";
        }
        List<Object> arithmeticExpression = stringConversion(stringArithmeticExpression);
        if (arithmeticExpression.isEmpty()) {
            return "";
        }
        if (arithmeticExpression.size() < 3) {
            return "";
        }
        int j;
        List<Object> outputStack = new ArrayList<>();
        List<String> actStack = new ArrayList<>();
        for (Object o : arithmeticExpression) {
            if (briefActions.contains(arithmeticExpression.get(0).toString())) {
                outputStack.add("0");
                continue;
            }
            String strOperation = o.toString();

            if (actions.contains(strOperation)) {
                j = -1;
                if (actStack.isEmpty() | strOperation.equals("(")) {
                    actStack.add(strOperation);
                    continue;
                }
                for (int i = actStack.size() - 1; i >= 0; i--) {
                    String action = actStack.get(i);
                    if (strOperation.equals(")")) {
                        while (!actStack.get(i).equals("(")) {
                            outputStack.add(action);
                            i--;
                            j++;
                        }
                        actStack.remove(i);
                        break;
                    }
                    if (priority.get(action) < priority.get(strOperation)) {
                        break;
                    }
                    outputStack.add(action);
                    j++;
                }
                while (j >= 0) {
                    actStack.remove(actStack.size() - 1);
                    j--;
                }
                if (!strOperation.equals(")")) {
                    actStack.add(strOperation);
                }
            } else {
                outputStack.add(o);
            }
        }
        if (!actStack.isEmpty()) {
            for (int i = actStack.size() - 1; i >= 0; i--) {
                outputStack.add(actStack.get(i));
            }
        }

        return calc(outputStack);
    }

    private static List<Object> stringConversion(String stringArithmeticExpression) {
        List<Object> arithmeticExpression = new ArrayList<>();
        int indexBegin = 0;
        Double number;

        for (int i = 0; i < stringArithmeticExpression.length(); i++) {
            if (actions.contains(Character.toString(stringArithmeticExpression.charAt(i)))) {
                try {
                    number = Double.parseDouble(stringArithmeticExpression.substring(indexBegin, i));
                    arithmeticExpression.add(number);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                indexBegin = i + 1;
                arithmeticExpression.add(stringArithmeticExpression.substring(i, indexBegin));
            }
        }
        if (indexBegin < stringArithmeticExpression.length()) {
            if (!actions.contains(stringArithmeticExpression.substring(indexBegin))) {
                try {
                    number = Double.parseDouble(stringArithmeticExpression.substring(indexBegin));
                    arithmeticExpression.add(number);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                arithmeticExpression.add(stringArithmeticExpression.substring(indexBegin));
            }
        }
        int indexLastElement = arithmeticExpression.size() - 1;
        if (arithmeticExpression.get(indexLastElement) instanceof String) {
            arithmeticExpression.remove(indexLastElement);
        }
        return arithmeticExpression;
    }

    public static String calc(List<Object> expRPN) {
        String actions = "%^*/+-";
        String resultString = "";
        List<Object> linkedListRPN = new LinkedList<>(expRPN);
        int i = 0;

        try {
            while (linkedListRPN.size() > 1) {
                if (actions.contains(linkedListRPN.get(i).toString())) {
                    switch (linkedListRPN.get(i).toString()) {
                        case ("%"): {
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i - 2) / 100 * (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("^"): {
                            linkedListRPN.set(i - 2, Math.pow((Double) linkedListRPN.get(i - 2), (Double) linkedListRPN.get(i - 1)));
                            break;
                        }
                        case ("*"): {
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i - 2) * (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("/"): {
                            if ((Double) linkedListRPN.get(i - 1) == 0) {
                                return "Деление на 0";
                            }
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i - 2) / (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("+"): {
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i - 2) + (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("-"): {
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i - 2) - (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                    }
                    linkedListRPN.remove(i);
                    linkedListRPN.remove(i - 1);
                    i = i - 2;
                }
                i++;
            }
        } catch (Exception e) {
            Log.e("TAGNum", "calc: ", e);
        }

        resultString = linkedListRPN.toString().substring(1, linkedListRPN.toString().indexOf("]"));
        if ((Double) linkedListRPN.get(0) % 1 == 0) {
            resultString = resultString.substring(0, resultString.indexOf("."));
        }

        return resultString;
    }
}

