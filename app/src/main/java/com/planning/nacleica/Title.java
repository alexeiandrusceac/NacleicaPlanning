package com.planning.nacleica;

public enum Title {
    CEO("Director general"), /*ADMIN("Administrator"),*/ MANAGER("Manager"), WORKER("Angajat");/*, DESIGNER("Designer")*/
    private String title;
    private Title(String title){this.title = title;}
    @Override
    public String toString(){return title;}
}
