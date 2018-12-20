package com.planning.nacleica.authactions;

import androidx.annotation.NonNull;

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

    public int getWorkerID() {
        return this.workerID;
    }

    public String getWorkerName() {
        return this.Name;
    }

    public String getWorkerPrename() {
        return this.Prename;
    }

    public String getWorkerPassword() {
        return this.Password;
    }

    public int getWorkerTitle() {
        return this.Title;
    }

    public byte[] getWorkerImage() {
        return this.Image;
    }

    public String getWorkerBirthday() {
        return this.Birthday;
    }

    public String getFullName() {
        return this.Name + " " + this.Prename;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getFullName();
    }
}
