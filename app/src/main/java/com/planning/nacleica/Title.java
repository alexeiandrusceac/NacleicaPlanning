package com.planning.nacleica;

public enum Title {

    CEO("Director general"), DESIGNER("Designer"), MANAGER("Manager"), WORKER("Angajat");
    /*, DESIGNER("Designer")*/
    private String title;
    private int titleIndex;
    private Title(String title){this.title = title;}

    @Override
    public String toString(){return title;}


}
