package main;

import objects.Facility;
import objects.PortalCrane;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class MyRunnable implements Runnable{

    private PortalCrane portalCrane;
    private ArrayList<Facility> facilities;
    private JFrame jFrame;

    public MyRunnable(PortalCrane portalCrane, ArrayList<Facility> facilities, JFrame jFrame) {
        this.portalCrane = portalCrane;
        this.facilities = facilities;
        this.jFrame = jFrame;
    }

    @Override
    public void run() {
        while (true) {
            portalCrane.doRandomMove(facilities, jFrame);
        }
    }
}
