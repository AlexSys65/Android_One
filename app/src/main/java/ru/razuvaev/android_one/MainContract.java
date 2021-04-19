package ru.razuvaev.android_one;

public interface MainContract {
    interface View {
        void numberBtnClick(View v);
        void pointBtnClick();
        void operationClick();
        void onResultClick();
        void cancelClick();
        void backSpaceClick();
    }

    interface  Presenter {

    }

    interface Model {

    }
}
