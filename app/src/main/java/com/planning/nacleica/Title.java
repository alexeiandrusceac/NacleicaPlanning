package com.planning.nacleica;

public enum Title {
    CEO("Director general"), /*ADMIN("Administrator"),*/ MANAGER("Manager"), WORKER("Angajat");
    /*, DESIGNER("Designer")*/
    private String title;
    private int titleIndex;
    private Title(String title){this.title = title;}
    private Title(int titleIndex) {this.titleIndex = titleIndex;}
    @Override
    public String toString(){return title;}
    @Override
    public int toInt(){return titleIndex;}

}
