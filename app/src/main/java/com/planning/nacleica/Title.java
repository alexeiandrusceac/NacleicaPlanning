package com.planning.nacleica;

public enum Title {

    CEO("Director general",1),DESIGNER("Designer",2), MANAGER("Manager",3), WORKER("Angajat",4);
    /*, DESIGNER("Designer")*/
    private String title;

    private int titleIndex;

    private Title(String title,int titleIndex) {
        this.title = title;
        this.titleIndex = titleIndex;
    }

    public int getTitleIndex() {
        return titleIndex;
    }
  /*  public String getTitle()
    {
        return title;
    }*/


    @Override
    public String toString() {
        return title;
    }


}
