package ru.razuvaev.android_one;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReversePolishNotation {

    public static String transformationString(List<Object> arithmeticExpression) {
        HashMap<String, Integer> priority;

        if (arithmeticExpression.isEmpty()) {
            return null;
        }
        List<Object> outputStack = new ArrayList<>();
        List<String> actStack = new ArrayList<>();
        final String actions = "%^*/+-()";
        final String briefActions = "%^*/+-";
        int j;
        priority = new HashMap<>();
        priority.put("%",5);
        priority.put("^",4);
        priority.put("*",3);
        priority.put("/",3);
        priority.put("+",2);
        priority.put("-",2);
        priority.put("(",1);
        priority.put(")",1);

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
                    if (priority.get(actStack.get(i)) < priority.get(o.toString())) {break;}
                    outputStack.add(actStack.get(i));
                    j++;
                }
                while (j >= 0){
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

    public static String calc(List<Object> expRPN) {
        String actions = "%^*/+-";
        String resultString = "";
        List<Object> linkedListRPN = new LinkedList<>(expRPN);
        int i = 0;
        //final byte outputSize = 13;

        //linkedListRPN.addAll(expRPN);

        try {
            while (linkedListRPN.size() > 1) {
                if (actions.contains(linkedListRPN.get(i).toString())) {
                    switch (linkedListRPN.get(i).toString()) {
                        case ("%") :{
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i-2) / 100 * (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("^") :{
                            linkedListRPN.set(i - 2, Math.pow((Double) linkedListRPN.get(i-2), (Double) linkedListRPN.get(i - 1)));
                            break;
                        }
                        case ("*") :{
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i-2) * (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("/") :{
                            if ((Double) linkedListRPN.get(i - 1) == 0) {
                                return "Деление на 0";
                            }
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i-2) / (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("+") :{
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i-2) + (Double) linkedListRPN.get(i - 1));
                            break;
                        }
                        case ("-") :{
                            linkedListRPN.set(i - 2, (Double) linkedListRPN.get(i-2) - (Double) linkedListRPN.get(i - 1));
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

        resultString = linkedListRPN.toString().substring(1,linkedListRPN.toString().indexOf("]"));
        if ((Double) linkedListRPN.get(0)%1 == 0) {
            resultString = resultString.substring(0, resultString.indexOf("."));
        }

        return resultString;
    }
}

