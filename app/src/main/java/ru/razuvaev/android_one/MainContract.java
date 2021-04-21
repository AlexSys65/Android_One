package ru.razuvaev.android_one;

public interface MainContract {
    interface ViewText {
        void ShowText(String result, String preResult);
    }

    interface Presenter {
        void numberBtnClick(String resultText, String s);

        void pointBtnClick(String resultText);

        void operationClick(String resultText, String s);

        void onResultClick(String resultText);

        void cancelClick();

        void backSpaceClick(String resultText);
    }

    interface Model {
        static String calculateExpression(String s) {
            return null;
        }
    }
}
