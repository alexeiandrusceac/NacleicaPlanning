package com.planning.nacleica.AuthActions;

public class Worker {

    public int workerID;
    public String Name;
    public String Prename;
    public String Password;
    public int Title;
    public byte[] Image;
    public String Birthday;

    public Worker() {
    }

    public String getWorkerName(int workerID)
    {
        return Name + " "+ Prename;
    }

}
