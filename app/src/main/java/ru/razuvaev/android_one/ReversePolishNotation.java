package ru.razuvaev.android_one;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReversePolishNotation {

    //public static String transformationString(List<Object> arithmeticExpression) {
    public static String transformationString(String stringArithmeticExpression) {

        if (stringArithmeticExpression.isEmpty()) {
            return "";
        }
        HashMap<String, Integer> priority;
        List<Object> outputStack = new ArrayList<>();
        List<String> actStack = new ArrayList<>();
        final String actions = "%^*/+-()";
        final String briefActions = "%^*/+-";
        int j;
        priority = new HashMap<>();
        priority.put("%", 5);
        priority.put("^", 4);
        priority.put("*", 3);
        priority.put("/", 3);
        priority.put("+", 2);
        priority.put("-", 2);
        priority.put("(", 1);
        priority.put(")", 1);

        List<Object> arithmeticExpression = stringСonversion(stringArithmeticExpression, actions);

        if (arithmeticExpression == null) {
            return "";
        }
        if (arithmeticExpression.size() < 3) {
            return "";
        }
        for (Object o : arithmeticExpression) {
            if (briefActions.contains(arithmeticExpression.get(0).toString())) {
                outputStack.add("0");
                continue;
            }
            if (actions.contains(o.toString())) {
                j = -1;
                if (actStack.isEmpty() | o.toString().equals("(")) {
                    actStack.add(o.toString());
                    continue;
                }
                for (int i = actStack.size() - 1; i >= 0; i--) {
                    if (o.toString().equals(")")) {
                        while (!actStack.get(i).equals("(")) {
                            outputStack.add(actStack.get(i));
                            i--;
                            j++;
                        }
                        actStack.remove(i);
                        break;
                    }
                    if (priority.get(actStack.get(i)) < priority.get(o.toString())) {
                        break;
                    }
                    outputStack.add(actStack.get(i));
                    j++;
                }
                while (j >= 0) {
                    actStack.remove(actStack.size() - 1);
                    j--;
                }
                if (!o.toString().equals(")")) {
                    actStack.add(o.toString());
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

    private static List<Object> stringСonversion(String stringArithmeticExpression, String actions) {
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

